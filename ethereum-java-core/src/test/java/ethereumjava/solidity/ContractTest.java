package ethereumjava.solidity;

import ethereumjava.EthereumJava;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.Transaction;
import ethereumjava.net.provider.RpcProvider;
import ethereumjava.solidity.types.SUInt;
import ethereumjava.solidity.types.SVoid;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 30/08/16.
 */
public class ContractTest {


    EthereumJava ethereumJava;


    final String ACCOUNT = "0xa9d28b5d7688fab363a3304992e48966118a4b8d";
    final String CONTRACT_ADDRESS = "0x10d92052e9ef32e8074bbd319ca3c30c4204b6de";

    final String PASSWORD = "toto";

    ChoupetteContract choupetteContract;

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://localhost:8547"))
                .build();

        ethereumJava.personal.unlockAccount(ACCOUNT,PASSWORD,3600);
        choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);
    }


    @After
    public void after() throws Exception{
        ethereumJava.close();
    }

    interface ChoupetteContract extends ContractType {

        @SolidityEvent.Anonymous(false)
        @SolidityEvent.Parameters({
                @SolidityEvent.Parameter(indexed = false, name = "state", type = SUInt.SUInt256.class)
        })
        SolidityEvent<SUInt.SUInt256> OnStateChanged();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction RentMe();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction StopRent();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction StartRent();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction ValidateTravel();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction GoTo(SUInt.SUInt256 x, SUInt.SUInt256 y);
        @SolidityFunction.ReturnType(SUInt.SUInt256.class)
        SolidityFunction GetPrice();


    }

    @Test
    public void testContractRentMe() throws Exception{

        Hash txHash = choupetteContract.RentMe().sendTransaction(ACCOUNT, new BigInteger("90000"));
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStopRent() throws Exception{

        Hash txHash = choupetteContract.StopRent().sendTransaction(ACCOUNT, new BigInteger("90000"));
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStartRent() throws Exception{

        Hash txHash = choupetteContract.StartRent().sendTransaction(ACCOUNT, new BigInteger("90000"), SolidityUtils.toWei("1","ether").toBigInteger());
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractValidateTravel() throws Exception{

        Hash txHash = choupetteContract.ValidateTravel().sendTransaction(ACCOUNT, new BigInteger("90000"));
        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGoTo() throws Exception{

        Hash txHash = choupetteContract
                .GoTo(  SUInt.fromBigInteger256(BigInteger.valueOf(3)),
                        SUInt.fromBigInteger256(BigInteger.valueOf(2)))
                .sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGetPrice() throws Exception{

        SUInt.SUInt256 response = (SUInt.SUInt256) choupetteContract.GetPrice().call();
        assertEquals(new BigInteger("1000000"),response.get());

    }


    @Test
    public void testContractOnStateChanged() throws Exception{

        Observable<Transaction> obsTransac = choupetteContract.RentMe().sendTransactionAndGetMined(ACCOUNT, new BigInteger("90000"));






        Transaction tx = obsTransac.toBlocking().first();
        assertNotNull(tx);



    }

}
