package ethereumjava.net.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import ethereumjava.exception.EthereumJavaException;
import ethereumjava.gson.BigIntegerTypeAdapter;
import ethereumjava.gson.HashTypeAdapter;
import ethereumjava.gson.ResponseTypeAdapter;
import ethereumjava.module.objects.Hash;
import ethereumjava.net.Request;
import ethereumjava.net.Response;

/**
 * Created by gunicolas on 17/08/16.
 */
public abstract  class AbstractProvider implements Provider{

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    protected Map<Integer, Request> requestQueue;

    private int requestNumber = 0;

    DataOutputStream out;
    BufferedReader in;

    OutputStream outputStream;
    InputStream inputStream;

    Gson gson;

    Thread listeningThread;
    boolean listen;

    public AbstractProvider() throws EthereumJavaException {
        requestQueue = new HashMap<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(BigInteger.class,new BigIntegerTypeAdapter());
        gsonBuilder.registerTypeHierarchyAdapter(Response.class,new ResponseTypeAdapter(requestQueue));
        gsonBuilder.registerTypeHierarchyAdapter(Hash.class,new HashTypeAdapter());
        gson = gsonBuilder.create();
    }

    @Override
    public void init() throws EthereumJavaException {
        try {
            setStreams();
            out = new DataOutputStream(outputStream);
            in = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
        } catch (IOException e) {
            throw new EthereumJavaException(e);
        }
    }

    @Override
    public Observable sendRequest(final Request request) throws EthereumJavaException {

        request.setId(getARequestNumber());

        String stringRequest =  "{" +
                "\"jsonrpc\":\"2.0\"," +
                "\"method\":\""+ request.getMethodCall() +"\"," +
                "\"params\":"+ request.getArguments() +"," +
                "\"id\":"+request.getId()+"" +
                "}";

        byte[] req = stringRequest.getBytes(CHARSET);
        try {
            out.write(req);
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

    @Override
    public void startListening() throws EthereumJavaException {
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

    protected abstract void setStreams() throws IOException;

    protected synchronized int getARequestNumber() {
        return requestNumber++;
    }

}
