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
    protected Config config;

    @Before
    public void setUp() throws Exception{
        config = Config.newInstance();

        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://"+config.rpcProviderAddr +":"+config.rpcProviderPort))
                .build();
    }

    @After
    public void after() throws Exception{
        ethereumJava.close();
    }
}
