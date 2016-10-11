package ethereumjava.net.provider;

import ethereumjava.exception.EthereumJavaException;
import ethereumjava.net.Request;
import ethereumjava.net.Response;
import rx.Observable;
import rx.Subscriber;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by gunicolas on 31/08/16.
 */
public abstract class IpcAbstractProvider extends AbstractProvider {

    public final String ipcFilePath;

    Thread listeningThread;
    boolean listen;

    public IpcAbstractProvider(String _ipcFilePath) throws EthereumJavaException {
        super();
        this.ipcFilePath = _ipcFilePath;
    }

    @Override
    public void init() throws EthereumJavaException {
        startListening();
    }

    @Override
    protected Observable send(byte[] stringRequest, final Request request){
        try {
            out.write(stringRequest);
        } catch (IOException e) {
            throw new EthereumJavaException(e);
        }

        requestQueue.put(request.getId(),request);

        Observable ret = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                request.addSubscriber(subscriber);
            }
        });

        return ret;
    }

    private void startListening() throws EthereumJavaException {
        listeningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listen = true;
                    while (listen) {
                        try {
                            String line;
                            while (in.ready() && (line = in.readLine()) != null) {
                                Response response = gson.fromJson(line, Response.class);

                                if (response.request != null) {

                                    List<Subscriber> subscribers = response.request.getSubscribers();
                                    if (response.isError()) {
                                        for (Subscriber subscriber : subscribers) {
                                            subscriber.onError(new EthereumJavaException(response.error.message));
                                        }
                                    } else {
                                        for (Subscriber subscriber : subscribers) {
                                            subscriber.onNext(response.result);
                                            subscriber.onCompleted();
                                        }
                                    }
                                    requestQueue.remove(response.id);

                                } // else just ignored
                            }
                        }catch(Exception e ){
                            throw new EthereumJavaException(e);
                        }
                        Thread.sleep(500);
                    }
                }
                catch(Exception e){
                    throw new EthereumJavaException(e);
                }
            }
        });
        listeningThread.start();
    }

    @Override
    public void stop() throws EthereumJavaException {
        if( this.listeningThread != null ){
            this.listen = false;
        }
        try {
            this.inputStream.close();
            this.outputStream.close();
        }catch(IOException e){
            throw new EthereumJavaException(e);
        }
    }
}
