package ethereumjava.exception;

/**
 * Created by gunicolas on 17/08/16.
 */
public class EthereumJavaException extends RuntimeException {

    public EthereumJavaException(Exception e) {
        super(e.getMessage(), e.getCause());
    }

    public EthereumJavaException(String message) {
        super(message);
    }
}
