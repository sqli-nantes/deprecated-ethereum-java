package ethereumjava;

import ethereumjava.module.objects.Block;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.module.objects.Transaction;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 18/08/16.
 */
public class ModulesTest extends EthereumRPCJavaTest {

    @Test
    public void testModules() throws Exception{

        NodeInfo nodeInfo = ethereumJava.admin.nodeInfo();
        System.out.println(nodeInfo.toString());

        Block<Hash> block = ethereumJava.eth.block(BigInteger.valueOf(11), Hash.class);
        System.out.println(block.toString());

        Block<Transaction> block1 = ethereumJava.eth.block(BigInteger.valueOf(11), Transaction.class);
        System.out.println(block.toString());

    }

}
