package ethereumjava.net.provider;

import ethereumjava.exception.EthereumJavaException;
import ethereumjava.net.Request;
import ethereumjava.net.Response;
import rx.Observable;
import rx.Subscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by gunicolas on 18/08/16.
 */
public class RpcProvider extends AbstractProvider {

    HttpURLConnection connection;
    String address;

    public RpcProvider(String address) {
        super();
        this.address = address;
    }

    @Override
    public void init() {
        try {
            URL url = new URL(address);
            URLConnection urlConn = url.openConnection();

            this.connection = (HttpURLConnection) urlConn;
            this.connection.setAllowUserInteraction(false);
            this.connection.setInstanceFollowRedirects(true);
            this.connection.setRequestMethod("POST");

            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            this.connection.setChunkedStreamingMode(0);
            this.connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            this.connection.setRequestProperty("Accept", "application/json");

            this.outputStream = this.connection.getOutputStream();
        }catch(Exception e){
            throw new EthereumJavaException(e);
        }
    }

    @Override
    public void stop() throws EthereumJavaException {

    }

    @Override
    protected Observable send(byte[] stringRequest, final Request request) {
        try {
            outputStream.write(stringRequest);
            outputStream.flush();
            inputStream = this.connection.getInputStream();

            in = new BufferedReader(new InputStreamReader(inputStream, CHARSET));

            final String line;
            if( in.ready() && (line = in.readLine()) != null ){
                requestQueue.put(request.getId(),request);

                Observable ret = Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        Response response = gson.fromJson(line, Response.class);
                        if (response.request != null) {
                            if (response.isError()) {
                                subscriber.onError(new EthereumJavaException(response.error.message));
                            } else {
                                subscriber.onNext(response.result);
                                subscriber.onCompleted();
                            }
                        }
                        requestQueue.remove(response.id);
                    }
                });

                return ret;

            }

        } catch (IOException e) {
            throw new EthereumJavaException(e);
        }
        return null;
    }

}
