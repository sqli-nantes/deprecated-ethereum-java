package web3j.solidity.coder;

import java.util.HashMap;
import java.util.Map;

import web3j.solidity.coder.decoder.SDecoder;
import web3j.solidity.coder.decoder.SIntDecoder;
import web3j.solidity.coder.encoder.SBytesEncoder;
import web3j.solidity.coder.encoder.SEncoder;
import web3j.solidity.coder.encoder.SIntEncoder;
import web3j.solidity.coder.encoder.SUIntEncoder;
import web3j.solidity.types.SBytes;
import web3j.solidity.types.SInt;
import web3j.solidity.types.SUInt;

/**
 * Mapping between Solidity types and coders (Encoders/Decoders).
 * Created by gunicolas on 08/09/16.
 */
public abstract class SCoderMapper {

    private static final Map<Class,Class<? extends SEncoder>> encoderMapping = new HashMap<Class,Class<? extends SEncoder>>(){{
        put(SInt.class,SIntEncoder.class);
        put(SUInt.class,SUIntEncoder.class); //TODO SUIntEncoder == SIntEncoder --> Refactor to use the same encoder
        put(SBytes.class, SBytesEncoder.class);
    }};


    private static final Map<Class,Class<? extends SDecoder>> decoderMapping = new HashMap<Class,Class<? extends SDecoder>>(){{
        put(SInt.class,SIntDecoder.class);
    }};


    /**
     * Returns solidity encoder for the given class or null if there is no encoder specified.
     * Look for an encoder of super class if none is found while there is no superclass.
     * @param clazz the class looking for an encoder
     * @return an encoder for the given class
     */
    public static Class<? extends SEncoder> getEncoderForClass(Class clazz){
        if( clazz == null ) return null;
        Class<? extends SEncoder> ret = encoderMapping.get(clazz);
        if (ret != null) return ret;
        return getEncoderForClass(clazz.getSuperclass());
    }

    /**
     * Returns solidity decoder for the given class or null if there is no decoder specified.
     * Look for a decoder of super class if none is found while there is no superclass.
     * @param clazz the class looking for an decoder
     * @return a decoder for the given class
     */
    public static Class<? extends SDecoder> getDecoderForClass(Class clazz){
        if( clazz == null ) return null;
        Class<? extends SDecoder> ret = decoderMapping.get(clazz);
        if (ret != null) return ret;
        return getDecoderForClass(clazz.getSuperclass());
    }
}
