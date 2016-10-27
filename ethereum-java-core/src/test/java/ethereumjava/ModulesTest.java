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
    public void nodeInfoTest() throws Exception{
        NodeInfo nodeInfo = ethereumJava.admin.nodeInfo();
        System.out.println(nodeInfo.toString());
    }

    @Test
    public void blockTxObjectTest() throws Exception{
        Block<Transaction> block = ethereumJava.eth.block(Hash.valueOf("0x2f57edeb015a8782f6220ae5a2e865259242a2c9b4352c06638b3d8bec86b179"), Transaction.class);
        assertTrue(block.transactions.get(0) instanceof Transaction);
    }

    @Test
    public void blockTxHashTest() throws Exception{
        Block<Hash> block = ethereumJava.eth.block(Hash.valueOf("0x2f57edeb015a8782f6220ae5a2e865259242a2c9b4352c06638b3d8bec86b179"), Hash.class);
        assertTrue(block.transactions.get(0) instanceof Hash);
    }

}
