package ethereumjava.solidity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import ethereumjava.exception.EthereumJavaException;
import ethereumjava.module.Eth;
import ethereumjava.module.objects.*;
import ethereumjava.solidity.coder.SCoder;
import ethereumjava.solidity.coder.SCoderMapper;
import ethereumjava.solidity.coder.decoder.SDecoder;
import ethereumjava.solidity.types.SType;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by gunicolas on 4/08/16.
 */

public class SolidityFunction<T extends SType> extends SolidityElement{

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ReturnType{
        Class value();
    }

    Object[] args;
    Class<T> returnType;

    public SolidityFunction(String address,Method method,Eth eth, Object[] args) {
        super(address,method,eth);
        this.args = args;
        this.returnType = method.getAnnotation(ReturnType.class).value();
    }

    @Override
    protected Class[] getParametersTypes() {
        return method.getParameterTypes();
    }

    @Override
    protected String signature(){
        return super.signature().substring(0,8);
    }

    private String encode(){
        String encodedParameters = "";
        if( args != null ) encodedParameters = SCoder.encodeParams(args);
        return "0x"+this.signature()+ encodedParameters;
    }

    private T decodeResponse(String dataHex){

        //TODO move to SCoder.decodeParam()

        Class<? extends SDecoder> decoderClass = SCoderMapper.getDecoderForClass(returnType);
        if( decoderClass == null ) throw new EthereumJavaException("no decoder found for type : "+returnType.getSimpleName());

        try {
            return (T) decoderClass.newInstance().decode(dataHex); //TODO remove cast
        } catch (Exception e) {
            throw new EthereumJavaException(e);
        }
    }


    private TransactionRequest formatRequest(String from, BigInteger gas){
        //TODO can estimate gas before
        String payload = encode();
        TransactionRequest request = new TransactionRequest(from,address);
        request.setGas(gas);
        request.setDataHex(payload);
        return request;
    }

    public Hash sendTransaction(String from, BigInteger gas){
        return eth.sendTransaction(formatRequest(from,gas));
    }


    public Observable<Transaction> sendTransactionAndGetMined(String from, BigInteger gas){

        final List<Subscriber<Transaction>> subscribers = new ArrayList<>();

        final Hash txHash = sendTransaction(from, gas);
        if( txHash == null ) return null;

        BlockFilter blockFilter = new BlockFilter(eth);
        Observable<List<String>> obs = blockFilter.watch();

        obs.subscribe(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {
                for (Subscriber sub : subscribers) {
                    sub.onCompleted();
                }
            }

            @Override
            public void onError(Throwable e) {
                for (Subscriber sub : subscribers) {
                    sub.onError(e);
                }
            }

            @Override
            public void onNext(List<String> hashs) {

                if(hashs != null && hashs.size() > 0 ) {
                    for(String hash : hashs) {
                        Block<Transaction> block = null;
                        try {
                            block = eth.block(Hash.valueOf(hash), Transaction.class);
                        } catch (EthereumJavaException e) {
                            onError(e);
                            return;
                        }

                        if (block == null) return;

                        for (Transaction transaction : block.transactions) {
                            if (transaction.hash.equals(txHash)) {
                                for (Subscriber sub : subscribers) {
                                    sub.onNext(transaction);
                                    sub.onCompleted();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        });

        return Observable.create(new Observable.OnSubscribe<Transaction>() {
            @Override
            public void call(Subscriber< ? super Transaction> subscriber) {
                subscribers.add((Subscriber<Transaction>) subscriber);
            }
        });
    }

    public T call(){

        String payload = encode();
        TransactionRequest request = new TransactionRequest(address);
        request.setDataHex(payload);

        String encodedResponse = eth.call(request,"latest");

        return decodeResponse(encodedResponse);
    }

}
