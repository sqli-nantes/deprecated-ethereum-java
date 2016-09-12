package web3j;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import web3j.solidity.types.SInt;

/**
 * Created by gunicolas on 23/08/16.
 */
public class UtilsTest {


    @Test
    public void test() throws Exception{
        A a = (A) Proxy.newProxyInstance(A.class.getClassLoader(),new Class[]{A.class},new invocation());
        a.foo(new SInt.SInt8[]{});

    }

    public class invocation implements InvocationHandler {

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {


            return null;
        }
    }

    public interface A{

        void foo(SInt.SInt8[] c);
    }



}
