package ethereumjava.module.objects;

import ethereumjava.module.Eth;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by gunicolas on 20/10/16.
 */
public abstract class AbstractFilter<T> {

    static final long POLLING_TIMEOUT = 1000/2;

    Eth eth;

    String filterId;

    Thread pollingThread;

    List<Subscriber> filterCallbacks;

    public AbstractFilter(Eth eth) {
        this.eth = eth;
        this.filterCallbacks = new ArrayList<>();

        Observable<String> callback = createFilter();

//        createFilter() // Observable<String>

//                .map(getHashFromFilterId()) // Observable<Hash>

//                .map(getBlockFromHash()) // Observable<Block>
//                .map(getTransactionsFromBlock()); // Observable<Transaction>


//
//        Observable<List<Hash>> hashes = createFilter().flatMap(new Func1<String, Observable<List<Hash>>>() {
//            @Override
//            public Observable<List<Hash>> call(String s) {
//                return eth.getFilterChanges(s);
//            }
//        });
//
//        createFilter()
//                .map(new Func1<String, Observable<Object>>() {
//            @Override
//            public Observable<List<Hash>> call(String s) {
//                return eth.getFilterChanges(s);
//            }
//        }).map(new Func1<Observable<List<Hash>>, Object>() {
//
//            @Override
//            public Object call(Observable<List<Hash>> objectObservable) {
//                return null;
//            }
//        });

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

                if( pollingThread == null && filterCallbacks.size() > 0 ){
                    poll();
                }
            }
        });
    }

    abstract Observable<String> createFilter();

    //TODO externalise POLL and let it POLL multiple methods at the same time
    void poll(){
/*
        pollingObservable = (Observable) Observable.interval(POLLING_TIMEOUT, TimeUnit.MILLISECONDS)
            .flatMap(new Func1<Long, Observable<T>>(){
                @Override
                public Observable<T> call(Long aLong) {
                    return (Observable<T>) eth.getFilterChanges(filterId);
                }
            })
            .subscribeOn(Schedulers.newThread())
            .subscribe(new Subscriber<T>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(T t) {

                }
            });*/


        pollingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Observable<T> changesObservable = (Observable<T> ) eth.getFilterChanges(filterId);

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
