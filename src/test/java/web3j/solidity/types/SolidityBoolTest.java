package web3j.solidity.types;


import org.junit.Test;
import org.junit.runner.RunWith;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 16/08/16.
 */
@RunWith(JUnitParamsRunner.class)
public class SolidityBoolTest {

    private Object[] parametersForIsType() {
        return new Object[]{
                new Object[]{"bool", true},
                new Object[]{"bool[]", true},
                new Object[]{"bool[4]", true},
                new Object[]{"bool[][]", true},
                new Object[]{"bool[3][]", true},
                new Object[]{"bool[][6][]", true}
        };
    }



    @Test
    @Parameters
    public void isType(String type,boolean expected) throws Exception{
        final boolean result = SBool.isType(type);
        assertEquals(expected,result);
    }


}
