package ethereumjava;

import ethereumjava.net.provider.RpcProvider;
import org.junit.After;
import org.junit.Before;

/**
 * Created by gunicolas on 12/10/16.
 */
public class EthereumRPCJavaTest {

    EthereumJava ethereumJava;

    final String ACCOUNT = "0xa9d28b5d7688fab363a3304992e48966118a4b8d";
    final String PASSWORD = "toto";

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://localhost:8547"))
                .build();

        ethereumJava.personal.unlockAccount(ACCOUNT,PASSWORD,3600);
    }

    @After
    public void after() throws Exception{
        ethereumJava.close();
    }
}
