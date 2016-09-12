package web3j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import web3j.module.objects.NodeInfo;
import web3j.net.provider.RpcProvider;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Web3JTest {

    Web3J web3J;
    RpcProvider provider;

    @Before
    public void setup() throws Exception{
        provider = new RpcProvider("http://localhost:8545");
        provider.init();
        provider.startListening();
        web3J = new Web3J.Builder()
                .provider(provider)
                .build();
    }

    @Test
    public void test() throws Exception{
        NodeInfo nodeInfo = web3J.admin.nodeInfo();
        System.out.println(nodeInfo.toString());
    }

    @After
    public void after() throws Exception{
        provider.stop();
    }


}
