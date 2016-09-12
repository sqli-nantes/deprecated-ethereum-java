package web3j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;

import rx.Observable;
import web3j.exception.Web3JException;
import web3j.module.Admin;
import web3j.module.Eth;
import web3j.module.Personal;
import web3j.module.annotation.ConvertParam;
import web3j.module.converter.ParameterConverter;
import web3j.net.Request;
import web3j.net.provider.Provider;
import web3j.solidity.Contract;

public class Web3J {

    public Admin admin;
    public Personal personal;
    public Eth eth;
    public Contract contract;

    private Web3J(Admin admin, Personal personal, Eth eth,Contract contract) {
        this.admin = admin;
        this.personal = personal;
        this.eth = eth;
        this.contract = contract;
    }



    public static class Builder {

        private InvocationHandler handler;
        private Provider provider;

        public Builder(){}

        public Builder provider(Provider provider) {
            this.provider = provider;
            handler = new InvocationHandler(this.provider);
            return this;
        }

        public Web3J build() throws Web3JException {
            if( handler == null ) throw new Web3JException("Missing provider");
            Admin admin = (Admin) Proxy.newProxyInstance(Admin.class.getClassLoader(), new Class[]{Admin.class},handler);
            Personal personal = (Personal) Proxy.newProxyInstance(Personal.class.getClassLoader(),new Class[]{Personal.class},handler);
            Eth eth = (Eth) Proxy.newProxyInstance(Eth.class.getClassLoader(),new Class[]{Eth.class},handler);
            Contract contract = new Contract(eth);
            return new Web3J(admin,personal,eth,contract);
        }

    }

    static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        Provider provider;

        public InvocationHandler(Provider _provider) {
            this.provider = _provider;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            args = convertArgumentsIfNecessary(method,args);
            String formattedArgs = Utils.formatArgsToString(args);
            String moduleName = method.getDeclaringClass().getSimpleName().toLowerCase();
            String methodName = "_"+Utils.extractMethodName(method);
            Type returnType = Utils.extractReturnType(method);
            Request request = new Request(moduleName+methodName,formattedArgs,returnType);

            Observable<?> result = provider.sendRequest(request);
            return convertToSyncIfNecessary(result, method);

        }

        private Object[] convertArgumentsIfNecessary(Method method, Object[] args) {
            int i=0;
            for(Annotation[] annotations : method.getParameterAnnotations()){
                if( annotations.length > 0 ){
                    Annotation annotation = annotations[0];
                    if( annotation.annotationType().isAssignableFrom(ConvertParam.class) ){
                        ConvertParam convertParamAnnotation = (ConvertParam) annotation;
                        try {
                            ParameterConverter parameterConverter = convertParamAnnotation.with().newInstance();
                            args[i] = parameterConverter.convertFrom(args[i]);
                        } catch (InstantiationException | IllegalAccessException e) {
                            throw new Web3JException(e);
                        }
                    }
                }
                i++;
            }
            return args;
        }

        private <T> Object convertToSyncIfNecessary(Observable<T> requestResult, Method method) throws Web3JException{
            Object result = null;
            if(method.getReturnType().isAssignableFrom(Observable.class)) {
                result = requestResult;
            } else if( requestResult != null ){
                try {
                    result = requestResult.toBlocking().single();
                }catch(NoSuchElementException e ){
                    throw new Web3JException(e);
                }
            }
            return result;
        }

    }


}
