package web3j.solidity;

import java.lang.reflect.Method;

import web3j.module.Eth;
import web3j.module.objects.Hash;
import web3j.module.objects.TransactionRequest;
import web3j.sha3.Sha3;
import web3j.solidity.coder.SCoder;

/**
 * Created by gunicolas on 4/08/16.
 */

/**+
 *
 * @param <T> return type
 */
public class SolidityFunction<T> {

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
        return "0x"+this.signature()+ SCoder.encodeParams(args);
    }

    public Hash sendTransaction(TransactionRequest request){
        String payload = toPayload();
        request.setDataHex(payload);
        request.setToHex(address);
        return eth.sendTransaction(request);
    }

}
