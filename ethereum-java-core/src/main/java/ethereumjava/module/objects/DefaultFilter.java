package ethereumjava.module.objects;

import ethereumjava.module.Eth;
import ethereumjava.solidity.coder.decoder.SDecoder;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

public class DefaultFilter extends AbstractFilter<Log> {

    FilterOptions options;

    public DefaultFilter(FilterOptions options, Eth eth) {

        super(eth);

        this.options = options;

    }

    @Override
    Observable<String> createFilter() {
        return eth.newFilter(options);
    }
}
