package ethereumjava;

import ethereumjava.module.objects.Block;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.module.objects.Transaction;
import org.junit.Test;
import rx.Observable;

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
        Block<Transaction> block = ethereumJava.eth.block(Hash.valueOf("0xbbd9f1c1e525508619aefe433326f8da0eaeb10cdaa9afd8ba34ee94defff6ea"), Transaction.class);
        assertTrue(block.transactions.get(0) instanceof Transaction);
    }

    @Test
    public void blockTxHashTest() throws Exception{
        Block<Hash> block = ethereumJava.eth.block(Hash.valueOf("0xbbd9f1c1e525508619aefe433326f8da0eaeb10cdaa9afd8ba34ee94defff6ea"), Hash.class);
        assertTrue(block.transactions.get(0) instanceof Hash);
    }

    @Test
    public void getBlockTxObjectTest() throws Exception{
        Observable<Block<Transaction>> block = ethereumJava.eth.getBlock(Hash.valueOf("0xbbd9f1c1e525508619aefe433326f8da0eaeb10cdaa9afd8ba34ee94defff6ea"), Transaction.class);
        assertTrue(block.toBlocking().first().transactions.get(0) instanceof Transaction);
    }

    @Test
    public void getBlockTxHashTest() throws Exception{
        Observable<Block<Hash>> block = ethereumJava.eth.getBlock(Hash.valueOf("0xbbd9f1c1e525508619aefe433326f8da0eaeb10cdaa9afd8ba34ee94defff6ea"), Hash.class);
        assertTrue(block.toBlocking().first().transactions.get(0) instanceof Hash);
    }

}
