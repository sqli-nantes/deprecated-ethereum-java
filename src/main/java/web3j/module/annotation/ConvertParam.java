package web3j.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import web3j.module.converter.ParameterConverter;

/**
 * Created by gunicolas on 25/08/16.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertParam {
    Class<? extends ParameterConverter> with();
}
