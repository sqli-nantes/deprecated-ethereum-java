package ethereumjava.solidity;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.List;

import ethereumjava.module.Eth;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.TransactionRequest;
import ethereumjava.sha3.Sha3;
import ethereumjava.solidity.coder.SCoder;
import ethereumjava.solidity.types.SType;

/**
 * Created by gunicolas on 4/08/16.
 */

/**+
 *
 * @param <T> return type
 */
public class SolidityFunction<T extends SType> {

    Method method;
    Eth eth;
    String address;
    Object[] args;
    String fullName;

    public SolidityFunction(Method method, Object[] args,Eth eth, String address) {
        this.method = method;
        this.eth = eth;
        this.address = address;
        this.args = args;
        this.fullName = SolidityUtils.transformToFullName(method);
    }

    private String signature(){
        return Sha3.hash(this.fullName).substring(0,8);
    }

    private String toPayload(){
        String encodedParameters = "";
        if( args != null ) encodedParameters = SCoder.encodeParams(args);
        return "0x"+this.signature()+ encodedParameters;
    }

    private List<T> decodeResponse(String dataHex){
        //TODO use decoder;
        return null;
    }


    private TransactionRequest formatRequest(String from, BigInteger gas){
        //TODO can estimate gas before
        String payload = toPayload();
        TransactionRequest request = new TransactionRequest(from,address);
        request.setGas(gas);
        request.setDataHex(payload);
        return request;
    }

    public Hash sendTransaction(String from, BigInteger gas){
        return eth.sendTransaction(formatRequest(from,gas));
    }

    public List<T> call(String from, BigInteger gas){
        String encodedResponse = eth.call(formatRequest(from,gas));
        return decodeResponse(encodedResponse);
    }

}
