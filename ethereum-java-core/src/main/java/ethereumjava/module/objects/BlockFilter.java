package ethereumjava.module.objects;

import ethereumjava.module.Eth;
import rx.Observable;
import rx.Subscriber;

import java.util.List;

/**
 * Created by gunicolas on 20/10/16.
 */
//String generic type is the new block hashes since last poll
public class BlockFilter extends AbstractFilter<String> {


    public BlockFilter(Eth eth) {
        super(eth);
    }

    @Override
    Observable<String> createFilter() {
        return eth.newBlockFilter();
    }

}
