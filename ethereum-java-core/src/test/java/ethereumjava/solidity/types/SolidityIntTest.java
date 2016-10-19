package ethereumjava.solidity.types;


import org.junit.runner.RunWith;


import junitparams.JUnitParamsRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 16/08/16.
 */
@RunWith(JUnitParamsRunner.class)
public class SolidityIntTest {

    private Object[] parametersForIsType() {
        return new Object[]{
                new Object[]{"int",true},
                new Object[]{"int[]",true},
                new Object[]{"int[4]",true},
                new Object[]{"int[][]",true},
                new Object[]{"int[3][]",true},
                new Object[]{"int[][6][]",true},
                new Object[]{"int32",true},
                new Object[]{"int64[]",true},
                new Object[]{"int8[4]",true},
                new Object[]{"int256[][]",true},
                new Object[]{"int[3][]",true},
                new Object[]{"int64[][6][]",true}
        };
    }

}
