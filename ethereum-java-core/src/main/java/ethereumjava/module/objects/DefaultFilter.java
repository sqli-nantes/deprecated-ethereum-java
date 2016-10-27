package ethereumjava.module.objects;

import ethereumjava.module.Eth;
import ethereumjava.solidity.coder.decoder.SDecoder;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T> Logs data type
 */
public class DefaultFilter<T> extends AbstractFilter<Log[]> {

    Class<? extends SDecoder<T>> returnDecoder;

    FilterOptions options;

    public DefaultFilter(FilterOptions options, Eth eth, Class<? extends SDecoder<T>> returnDecoder) {

        super(eth);

        this.returnDecoder = returnDecoder;
        this.options = options;

    }

    @Override
    Observable<String> createFilter() {
        return eth.newFilter(options);
    }

    @Override
    void onNewData(Log[] logs) {
        if( logs == null ){
            return;
        }

        for(Log log : logs){

            try {
                T data = null;
                if( returnDecoder != null ) //TODO return log if no return parameter
                    data =  returnDecoder.newInstance().decode(log.data);

                for(Subscriber<? super T> subscriber : filterCallbacks ) {
                    subscriber.onNext(data);
                }

            } catch (Exception e) {
                for(Subscriber subscriber : filterCallbacks ) {
                    subscriber.onError(e);
                }
            }
        }
    }
}
