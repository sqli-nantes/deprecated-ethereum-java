package ethereumjava.solidity;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import ethereumjava.exception.EthereumJavaException;
import ethereumjava.module.Eth;

/**
 * Created by gunicolas on 30/08/16.
 */
public class Contract<T extends ContractType> {

    Class<T> abi;
    String address;
    Eth eth;

    public Contract(Eth eth){
        this.eth = eth;
    }

    public Contract<T> withAbi(Class<T> abi){
        this.abi = abi;
        return this;
    }

    public T at(String address){
        this.address = address;
        return (T) Proxy.newProxyInstance(abi.getClassLoader(),new Class[]{abi},new InvocationHandler());
    }

    class InvocationHandler implements java.lang.reflect.InvocationHandler{

        @Override
        public SolidityElement invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if( method.getReturnType().isAssignableFrom(SolidityFunction.class) ){
                return new SolidityFunction(address,method,eth,args);
            } else if( method.getReturnType().isAssignableFrom(SolidityEvent.class) ){
                return new SolidityEvent(address,method,eth);
            }
            throw new EthereumJavaException("Contract element return type is invalid");
        }
    }

}
