package ethereumjava;

import ethereumjava.module.objects.Block;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.net.provider.RpcProvider;
import ethereumjava.solidity.ContractType;
import ethereumjava.solidity.SolidityFunction;
import ethereumjava.solidity.types.SVoid;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 18/08/16.
 */
public class RPCProviderTest {

    EthereumJava ethereumJava;
    RpcProvider provider;

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://localhost:8547"))
                .build();
    }

    @Test
    public void testModules() throws Exception{

        NodeInfo nodeInfo = ethereumJava.admin.nodeInfo();
        System.out.println(nodeInfo.toString());

        Block<Hash> block = ethereumJava.eth.block(BigInteger.ZERO, Hash.class);
        System.out.println(block.toString());
    }


    interface Contract extends ContractType {
        SolidityFunction<SVoid> RentMe();
        SolidityFunction<SVoid> StopRent();
        SolidityFunction<SVoid> StartRent();
        SolidityFunction<SVoid> ValidateTravel();
    }


    final String CONTRACT_ADDRESS = "0x4f81d84cccd66f12836625281ae249f8b0586920";
    final String ACCOUNT = "0x3cd85ae0ffdf3d88c40fdce3654181665097939f";

    @Test
    public void testContractRentMe() throws Exception{

        Contract contract = (Contract) ethereumJava.contract.withAbi(Contract.class).at(CONTRACT_ADDRESS);
        Hash txHash = contract.RentMe().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStopRent() throws Exception{

        Contract contract = (Contract) ethereumJava.contract.withAbi(Contract.class).at(CONTRACT_ADDRESS);
        Hash txHash = contract.StopRent().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStartRent() throws Exception{

        Contract contract = (Contract) ethereumJava.contract.withAbi(Contract.class).at(CONTRACT_ADDRESS);
        Hash txHash = contract.StartRent().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractValidateTravel() throws Exception{

        Contract contract = (Contract) ethereumJava.contract.withAbi(Contract.class).at(CONTRACT_ADDRESS);
        Hash txHash = contract.ValidateTravel().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGoTo() throws Exception{

        Contract contract = (Contract) ethereumJava.contract.withAbi(Contract.class).at(CONTRACT_ADDRESS);
        Hash txHash = contract.().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @After
    public void after() throws Exception{
        ethereumJava.close();
    }


}
