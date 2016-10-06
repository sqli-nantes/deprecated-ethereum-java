package web3j.solidity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigInteger;

import web3j.Web3J;
import web3j.module.objects.TransactionRequest;
import web3j.net.Request;
import web3j.net.provider.Provider;
import web3j.solidity.types.SInt;

/**
 * Created by gunicolas on 30/08/16.
 */
public class ContractTest {

    @Mock
    Provider mockProvider;

    @Captor
    ArgumentCaptor<Request> captor;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    Web3J web3J;

    @Before
    public void setup(){
        web3J = new Web3J.Builder()
                .provider(mockProvider)
                .build();
    }

    @Test
    public void contractCreationTest() throws Exception {
       MyContract contract = (MyContract) web3J.contract.withAbi(MyContract.class).at("0x54684643138463"); //TODO remove cast

        contract.foo(SInt.fromInteger(3)).sendTransaction("0x54686332268164868648",new BigInteger("90000"));

    }
}
