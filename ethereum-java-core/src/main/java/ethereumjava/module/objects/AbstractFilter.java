package ethereumjava.module.objects;

import ethereumjava.module.Eth;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 20/10/16.
 */
public abstract class AbstractFilter<T> {

    static final int POLLING_TIMEOUT = 1000/2;

    Eth eth;

    String filterId;

    Thread pollingThread;

    List<Subscriber> filterCallbacks;

    public AbstractFilter(Eth eth) {
        this.eth = eth;
        this.filterCallbacks = new ArrayList<>();

        Observable<String> callback = createFilter();

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
            }
        });
    }

    abstract Observable<String> createFilter();

    //TODO externalise POLL and let it POLL multiple methods at the same time
    void poll(){
        pollingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Observable<T> changesObservable = (Observable<T>) eth.getFilterChanges(filterId);

                    changesObservable.subscribe(new Subscriber<T>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            for(Subscriber subscriber : filterCallbacks )
                                subscriber.onError(e);
                        }

                        @Override
                        public void onNext(T t) {
                            onNewData(t);
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
    abstract void onNewData(T data);

    public Observable<T> watch(){

        return Observable.create(new Observable.OnSubscribe<T>(){
            @Override
            public void call(Subscriber<? super T> subscriber) {
                filterCallbacks.add(subscriber);

                if( filterId != null ){
                    poll();
                }
            }
        });
    }

    public Observable stopWatching(){
        this.pollingThread.interrupt();
        this.filterCallbacks.clear();
        return eth.uninstallFilter(filterId);
    }
}
