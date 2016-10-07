package ethereumjava;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ethereumjava.module.objects.NodeInfo;
import ethereumjava.net.provider.RpcProvider;

/**
 * Created by gunicolas on 18/08/16.
 */
public class EthereumJavaTest {

    EthereumJava ethereumJava;
    RpcProvider provider;

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://localhost:8545"))
                .build();
    }

    @Test
    public void test() throws Exception{
        NodeInfo nodeInfo = ethereumJava.admin.nodeInfo();
        System.out.println(nodeInfo.toString());
    }

    @After
    public void after() throws Exception{
        ethereumJava.close();
    }


}
