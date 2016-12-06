package ethereumjava.config;

import ethereumjava.EthereumJava;
import ethereumjava.net.provider.RpcProvider;
import org.junit.After;
import org.junit.Before;

import java.io.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 12/10/16.
 */
public class RPCTest {

    protected EthereumJava ethereumJava;
    protected Config config;
    protected Process p;

    @Before
    public void setUp() throws Exception{
        config = Config.newInstance();

        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://"+config.rpcProviderAddr +":"+config.rpcProviderPort))
                .build();



        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //p = Runtime.getRuntime().exec(System.getProperty("user.dir") + "/src/test/resources/prerequisite.sh ", null, new File(System.getProperty("user.dir") + "/src/test/resources/"));

                    ProcessBuilder builder = new ProcessBuilder("/bin/bash", System.getProperty("user.dir") + "/src/test/resources/prerequisite.sh");
                    builder.directory(new File(System.getProperty("user.dir") + "/src/test/resources/"));
                    builder.redirectOutput(new File(System.getProperty("user.dir") + "/src/test/resources/out.log"));
                    builder.redirectError(new File(System.getProperty("user.dir") + "/src/test/resources/out.err.log"));
                    p = builder.start();



//            InputStream is = p.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);

//            while (true) {
//                final String line = br.readLine();
//                if (line.contains("HTTP endpoint opened")) break;
//
//                System.out.println(line);
//                System.out.flush();
//            }


                } catch (IOException e) {
                    assertTrue("Prerequisite couldn't be executed" + e,false);
                }
            }
        }).start();

        Thread.sleep(1000);

        BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.dir") + "/src/test/resources/out.err.log")));
        String line;
        boolean keepReading = true;
        while (keepReading) {
            line = br.readLine();
            if (line == null) {
                //wait until there is more of the file for us to read
                Thread.sleep(500);
            }
            else {
                if(line.contains("HTTP endpoint opened"))
                    keepReading = false;
            }
        }

    }

    @After
    public void after() throws Exception{
        ethereumJava.close();

        String killer = System.getProperty("user.dir") + "/src/test/resources/killPrerequisite.sh";

        if(new File(killer).exists()) {
            p.destroy();
            while(new File(killer).exists());
        }
    }
}
