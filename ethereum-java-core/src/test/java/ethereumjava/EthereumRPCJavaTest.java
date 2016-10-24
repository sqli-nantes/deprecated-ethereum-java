package ethereumjava;

import ethereumjava.net.provider.RpcProvider;
import org.junit.After;
import org.junit.Before;

/**
 * Created by gunicolas on 12/10/16.
 */
public class EthereumRPCJavaTest {

    EthereumJava ethereumJava;


    final String ACCOUNT = "0xc74a32dd958075a6a31db72db3fa1b7c57350d6c";
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
