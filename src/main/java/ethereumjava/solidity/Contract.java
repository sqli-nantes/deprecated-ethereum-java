package ethereumjava.solidity;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        public SolidityFunction invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return new SolidityFunction(method,args,eth,address);
        }
    }

}
