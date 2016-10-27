package ethereumjava;

import ethereumjava.net.provider.RpcProvider;
import org.junit.After;
import org.junit.Before;

/**
 * Created by gunicolas on 12/10/16.
 */
public class EthereumRPCJavaTest {

    EthereumJava ethereumJava;

    final String ACCOUNT = "0x79db03078440f2750d61bbb9b706d02769f6562a";
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
