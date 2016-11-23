package ethereumjava;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;

import rx.Observable;
import ethereumjava.exception.EthereumJavaException;
import ethereumjava.module.Admin;
import ethereumjava.module.Eth;
import ethereumjava.module.Personal;
import ethereumjava.module.annotation.ConvertParam;
import ethereumjava.module.converter.ParameterConverter;
import ethereumjava.net.Request;
import ethereumjava.net.provider.Provider;
import ethereumjava.solidity.Contract;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class EthereumJava {

    public Admin admin;
    public Personal personal;
    public Eth eth;
    public Contract contract;

    private Provider provider;

    private EthereumJava(Provider provider,Admin admin, Personal personal, Eth eth,Contract contract) {
        this.admin = admin;
        this.personal = personal;
        this.eth = eth;
        this.contract = contract;
        this.provider = provider;
    }

    public void close(){
        provider.stop();
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
        public EthereumJava build() throws EthereumJavaException {
            if( this.provider == null || handler == null ) throw new EthereumJavaException("Missing provider");
            provider.init();
            Admin admin = (Admin) Proxy.newProxyInstance(Admin.class.getClassLoader(), new Class[]{Admin.class},handler);
            Personal personal = (Personal) Proxy.newProxyInstance(Personal.class.getClassLoader(),new Class[]{Personal.class},handler);
            Eth eth = (Eth) Proxy.newProxyInstance(Eth.class.getClassLoader(),new Class[]{Eth.class},handler);
            Contract contract = new Contract(eth);
            return new EthereumJava(provider,admin,personal,eth,contract);
        }

    }

    static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        Provider provider;

        public InvocationHandler(Provider _provider) {
            this.provider = _provider;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Type returnType = Utils.extractReturnType(method,args); //Must extract from originial parameters (next instruction convert these parameters)

            args = convertArgumentsIfNecessary(method,args);
            String formattedArgs = Utils.formatArgsToString(args);
            String moduleName = method.getDeclaringClass().getSimpleName().toLowerCase();
            String methodName = "_"+Utils.extractMethodName(method);


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
                            throw new EthereumJavaException(e);
                        }
                    }
                }
                i++;
            }
            return args;
        }

        private <T> Object convertToSyncIfNecessary(Observable<T> requestResult, Method method) throws EthereumJavaException {
            Object result = null;
            if(method.getReturnType().isAssignableFrom(Observable.class)) {
                result = requestResult;
            } else if( requestResult != null ){
                try {
                    result = requestResult.toBlocking().single(); //TODO subscribeOn (thread current) / observeOn (pool thread)
                }catch(NoSuchElementException e ){
                    throw new EthereumJavaException(e);
                }
            }
            return result;
        }



    }


}
