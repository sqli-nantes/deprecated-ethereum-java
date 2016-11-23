package ethereumjava.config;

import ethereumjava.EthereumJava;
import ethereumjava.net.provider.RpcProvider;
import org.junit.After;
import org.junit.Before;

/**
 * Created by gunicolas on 12/10/16.
 */
public class RPCTest {

    protected EthereumJava ethereumJava;

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://"+Config.RPC_PROVIDER_ADDR+":"+Config.RPC_PROVIDER_PORT))
                .build();
    }

    @After
    public void after() throws Exception{
        ethereumJava.close();
    }
}
