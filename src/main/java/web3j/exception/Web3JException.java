package web3j.exception;

/**
 * Created by gunicolas on 17/08/16.
 */
public class Web3JException extends RuntimeException {

    public Web3JException(Exception e){
        super(e.getMessage(),e.getCause());
    }

    public Web3JException(String message) {
         super(message);
    }
}
