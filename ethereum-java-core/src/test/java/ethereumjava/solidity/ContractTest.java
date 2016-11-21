package ethereumjava.solidity;

import ethereumjava.EthereumJava;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.Transaction;
import ethereumjava.net.provider.RpcProvider;
import ethereumjava.solidity.types.SUInt;
import ethereumjava.solidity.types.SVoid;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.observers.Subscribers;
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

    String coinbase;
    final String CONTRACT_ADDRESS = "0xf673d7d1d6f8b8104d01843cd7fa7a8a272115d9";

    final String PASSWORD = "toto";

    ChoupetteContract choupetteContract;

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://localhost:8547"))
                .build();

        coinbase = ethereumJava.personal.listAccounts().get(0);
        ethereumJava.personal.unlockAccount(coinbase,PASSWORD,3600);
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
        SolidityFunction<SVoid> RentMe();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction<SVoid> StopRent();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction<SVoid> StartRent();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction<SVoid> ValidateTravel();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction<SVoid> GoTo(SUInt.SUInt256 x, SUInt.SUInt256 y);
        @SolidityFunction.ReturnType(SUInt.SUInt256.class)
        SolidityFunction<SUInt.SUInt256> GetPrice();


    }

    @Test
    public void testContractRentMe() throws Exception{

        Hash txHash = choupetteContract.RentMe().sendTransaction(coinbase, new BigInteger("90000"));
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStopRent() throws Exception{

        Hash txHash = choupetteContract.StopRent().sendTransaction(coinbase, new BigInteger("90000"));
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStartRent() throws Exception{

        Hash txHash = choupetteContract.StartRent().sendTransaction(coinbase, new BigInteger("90000"), SolidityUtils.toWei("1","ether").toBigInteger());
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractValidateTravel() throws Exception{

        Hash txHash = choupetteContract.ValidateTravel().sendTransaction(coinbase, new BigInteger("90000"));
        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGoTo() throws Exception{

        Hash txHash = choupetteContract
                .GoTo(  SUInt.fromBigInteger256(BigInteger.valueOf(3)),
                        SUInt.fromBigInteger256(BigInteger.valueOf(2)))
                .sendTransaction(coinbase, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGetPrice() throws Exception{

        SUInt.SUInt256 response = (SUInt.SUInt256) choupetteContract.GetPrice().call();
        assertEquals(new BigInteger("1000000"),response.get());

    }

    @Test
    public void testContractOnStateChanged() throws Exception{

        Observable<Transaction> obs = choupetteContract.RentMe().sendTransactionAndGetMined(coinbase, new BigInteger("90000"));
        obs .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.immediate())
            .subscribe(new Action1<Transaction>() {
                @Override
                public void call(Transaction transaction) {
                    Assert.assertNotNull(transaction);
                }
            });
    }

}
