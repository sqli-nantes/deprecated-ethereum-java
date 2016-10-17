package ethereumjava.module.objects;

import ethereumjava.module.Eth;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 13/10/16.
 */
public class Filter {

    static final int POLLING_TIMEOUT = 1000/2;

    List<Subscriber> filterCallbacks;
    List<Subscriber> logsCallbacks;
    String filterId;

    Eth eth;

    Thread pollingThread;

    public Filter(FilterOptions options,Eth eth) {

        this.filterCallbacks = new ArrayList<>();
        this.logsCallbacks = new ArrayList<>();
        this.eth = eth;

        final Observable<String> callback =  eth.newFilter(options);
        callback.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                for(Subscriber subscriber : filterCallbacks){
                    subscriber.onError(e);
                }
            }

            @Override
            public void onNext(String s) {
                filterId = s;

                for(Subscriber logCallback : logsCallbacks){
                    get(logCallback);
                }

                logsCallbacks.clear();

                for(Subscriber filterCallback : filterCallbacks){
                    getLogsAtStart(filterCallback);
                }

                if( filterCallbacks.size() > 0){
                    poll();
                }
            }
        });
    }

    private void get(final Subscriber callback){
        if( filterId == null ){
            logsCallbacks.add(callback);
        } else {
            Observable<Log[]> onLogsReceived = eth.getFilterLogs(filterId);
            onLogsReceived.subscribe(new Subscriber<Log[]>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    callback.onError(e);
                }

                @Override
                public void onNext(Log[] logs) {
                    callback.onNext(logs);
                }
            });
        }
    }

    private void getLogsAtStart(final Subscriber callback){
        get(new Subscriber<Log[]>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);
            }

            @Override
            public void onNext(Log[] logs) {
                for(Log log : logs){
                    callback.onNext(log);
                }
            }
        });
    }


    //TODO externalise POLL and let it POLL multiple methods at the same time
    private void poll(){

        pollingThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){

                    Observable<Log[]> changesObservable = eth.getFilterChanges(filterId);

                    changesObservable.subscribe(new Subscriber<Log[]>() {
                        @Override
                        public void onCompleted() {
                            System.out.println("completed");
                        }

                        @Override
                        public void onError(Throwable e) {
                            for(Subscriber subscriber : filterCallbacks )
                                subscriber.onError(e);
                        }

                        @Override
                        public void onNext(Log[] logs) {
                            for(Subscriber subscriber : filterCallbacks )
                                subscriber.onNext(logs);
                        }
                    });

                    try {
                        Thread.sleep(POLLING_TIMEOUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        pollingThread.start();

    }


    public Observable watch(){

        Observable observable = Observable.create(new Observable.OnSubscribe<Object>(){
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                filterCallbacks.add(subscriber);

                if( filterId != null ){
                    getLogsAtStart(subscriber);
                    poll();
                }
            }
        });

        return observable;
    }

    public Observable stopWatching(){
        this.pollingThread.interrupt();
        this.filterCallbacks.clear();
        return eth.uninstallFilter(filterId);
    }
}
