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

        filterId = createFilter().toBlocking().first();
    }

    abstract Observable<String> createFilter();


    public Observable<T> watch(){
        return Observable.interval(POLLING_TIMEOUT,TimeUnit.MILLISECONDS)
                .flatMap(getFilterChanges())
                .flatMap(fromListToElement());
    }

    private Func1<List<T>, Observable<T>> fromListToElement() {
        return new Func1<List<T>, Observable<T>>() {
            @Override
            public Observable<T> call(List<T> ts) {
                return Observable.from(ts);
            }
        };
    }

    private Func1<Long, Observable<List<T>>> getFilterChanges() {
        return new Func1<Long, Observable<List<T>>>() {
            @Override
            public  Observable<List<T>> call(Long l) {
                return eth.getFilterChanges(filterId);
            }
        };
    }

    public Observable stopWatching(){
        this.pollingThread.interrupt();
        this.filterCallbacks.clear();
        return eth.uninstallFilter(filterId);
    }
}
