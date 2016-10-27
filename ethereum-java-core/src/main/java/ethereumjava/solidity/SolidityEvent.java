package ethereumjava.solidity;

import ethereumjava.module.Eth;
import ethereumjava.module.objects.DefaultFilter;
import ethereumjava.module.objects.FilterOptions;
import ethereumjava.solidity.coder.SCoderMapper;
import ethereumjava.solidity.coder.decoder.SDecoder;
import ethereumjava.solidity.types.SType;
import rx.Observable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 4/08/16.
 */
public class SolidityEvent<T> extends SolidityElement{

    DefaultFilter defaultFilter;

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Anonymous{
        boolean value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Parameters{
        Parameter[] value();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Parameter{
        boolean indexed();
        String name();
        Class<? extends SType> type();
    }

    public SolidityEvent(String address,Method method,Eth eth) {
        super(address,method,eth);
    }

    @Override
    protected Class[] getParametersTypes() {

        List<Class> ret = new ArrayList<>();

        Parameters parameters = method.getAnnotation(Parameters.class);

        if( parameters != null ){
            for( Parameter parameter : parameters.value()){
                ret.add(parameter.type());
            }
        }

        return ret.toArray(new Class[ret.size()]);
    }

    public FilterOptions encode(){

        List<String> topics = new ArrayList<>();
        topics.add("0x"+this.signature());

        return new FilterOptions(topics,this.address);
    }

    public Observable<T> watch(){

        Class[] returnParams = getParametersTypes();
        Class<? extends SDecoder> decoder = null;
        if( returnParams.length > 0 ) {
            decoder = SCoderMapper.getDecoderForClass(returnParams[0]);
        }

        FilterOptions options = encode();
        this.defaultFilter = new DefaultFilter(options,eth,decoder);
        return defaultFilter.watch();
    }

    public Observable stopWatching(){
        return this.defaultFilter.stopWatching();
    }

}
