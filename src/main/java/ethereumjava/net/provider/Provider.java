package ethereumjava.net.provider;

import rx.Observable;
import ethereumjava.exception.EthereumJavaException;
import ethereumjava.net.Request;

/**
 * Created by gunicolas on 23/08/16.
 */
public interface Provider {

    /**
     *
     * @param request
     * @param <T>
     * @return By contract, cannot be <code>null</code>. The @Observable to subscribe to get the response.
     */
    <T> Observable<T> sendRequest(Request request);

    void init() throws EthereumJavaException;
    void stop() throws EthereumJavaException;

}
