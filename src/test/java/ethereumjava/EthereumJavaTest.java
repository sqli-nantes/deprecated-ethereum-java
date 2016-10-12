package ethereumjava;

import ethereumjava.net.provider.RpcProvider;
import org.junit.After;
import org.junit.Before;

/**
 * Created by gunicolas on 12/10/16.
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

    @After
    public void after() throws Exception{
        ethereumJava.close();
    }
}
