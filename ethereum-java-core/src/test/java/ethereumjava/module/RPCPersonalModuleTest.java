package ethereumjava.module;

import ethereumjava.config.Config;
import ethereumjava.config.RPCTest;
import ethereumjava.exception.EthereumJavaException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 22/11/16.
 */
public class RPCPersonalModuleTest extends RPCTest {

    @Test
    public void listAccountsTest() throws Exception{

        List<String> accounts = ethereumJava.personal.listAccounts();
        assertTrue(accounts.size()==1);

    }

    @Test
    public void unlockAccountSuccessTest() throws Exception{
        String account = ethereumJava.personal.listAccounts().get(0);
        boolean unlocked = ethereumJava.personal.unlockAccount(account, Config.ACCOUNT0_PASSWORD,3600);

        assertTrue(unlocked);
    }

    @Test
    public void unlockAccountFailTest() throws Exception{
        String account = ethereumJava.personal.listAccounts().get(0);
        try {
            ethereumJava.personal.unlockAccount(account, Config.ACCOUNT0_PASSWORD + "00000", 3600);
        }catch(EthereumJavaException e){
            assertTrue("could not decrypt key with given passphrase".compareTo(e.getMessage())==0);
        }
    }

    @Test
    public void newAccountTest() throws Exception{
        String account = ethereumJava.personal.newAccount(Config.ACCOUNT0_PASSWORD);
        assertTrue(account.matches("^0x([0-9a-fA-F]){40}$"));
        assertTrue(ethereumJava.personal.listAccounts().get(1).compareTo(account)==0);
        boolean unlocked = ethereumJava.personal.unlockAccount(account,Config.ACCOUNT0_PASSWORD,3600);
        assertTrue(unlocked);
    }

}
