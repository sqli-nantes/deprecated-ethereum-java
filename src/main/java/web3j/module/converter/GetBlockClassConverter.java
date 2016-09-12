package web3j.module.converter;

import web3j.module.objects.Hash;

/**
 * Created by gunicolas on 25/08/16.
 */
public class GetBlockClassConverter extends ParameterConverter<Class,Boolean>{

    @Override
    public Boolean convertFrom(Class parameter) {
        return !parameter.isAssignableFrom(Hash.class);
    }
}
