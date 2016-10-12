package ethereumjava;

import ethereumjava.module.objects.Block;
import ethereumjava.module.objects.Hash;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ethereumjava.module.objects.NodeInfo;
import ethereumjava.net.provider.RpcProvider;

import java.math.BigInteger;

/**
 * Created by gunicolas on 18/08/16.
 */
public class EthereumJavaTest {

    EthereumJava ethereumJava;
    RpcProvider provider;

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://localhost:8547"))
                .build();
    }

    @Test
    public void test() throws Exception{
        NodeInfo nodeInfo = ethereumJava.admin.nodeInfo();
        System.out.println(nodeInfo.toString());

        Block<Hash> block = ethereumJava.eth.block(BigInteger.ZERO, Hash.class);
        System.out.println(block.toString());
    }

    @After
    public void after() throws Exception{
        ethereumJava.close();
    }


}
