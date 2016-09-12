package web3j.module.objects;


/**
 * Created by gunicolas on 25/08/16.
 */
public class Hash extends Web3JType {

    String value;

    public Hash(String value) {
        this.value = value;
    }

    public static Hash valueOf(String value) throws IllegalArgumentException{
        return new Hash(value);
    }

    @Override
    public String toString() {
        return "\""+value+"\"";
    }
}
