package ethereumjava.solidity;

import ethereumjava.config.Config;
import ethereumjava.config.RPCTest;
import ethereumjava.config.RxJavaHelper;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.Transaction;
import ethereumjava.solidity.types.SUInt;
import ethereumjava.solidity.types.SVoid;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.observers.TestSubscriber;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ethereumjava.config.RxJavaHelper.waitTerminalEvent;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gunicolas on 30/08/16.
 */
public class ContractTest extends RPCTest{

    TestContract contract;
    Config.TestAccount testAccount;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        testAccount = config.accounts.get(1);
        contract =  ethereumJava.contract.withAbi(TestContract.class).at(config.contractAddress);
    }

    interface TestContract extends ContractType {

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
        Hash txHash = contract.RentMe().sendTransaction(testAccount.id, new BigInteger("90000"));
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStopRent() throws Exception{
        Hash txHash = contract.StopRent().sendTransaction(testAccount.id, new BigInteger("90000"));
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStartRent() throws Exception{
        Hash txHash = contract.StartRent().sendTransaction(testAccount.id, new BigInteger("90000"), SolidityUtils.toWei("1","ether").toBigInteger());
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractValidateTravel() throws Exception{
        Hash txHash = contract.ValidateTravel().sendTransaction(testAccount.id, new BigInteger("90000"));
        assertTrue(txHash!=null);
    }

    @Test
    public void testContractGoTo() throws Exception{

        Hash txHash = contract
                .GoTo(  SUInt.fromBigInteger256(BigInteger.valueOf(3)),
                        SUInt.fromBigInteger256(BigInteger.valueOf(2)))
                .sendTransaction(testAccount.id, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGetPrice() throws Exception{
        SUInt.SUInt256 response = contract.GetPrice().call();
        Assert.assertEquals(new BigInteger("1000000000000000000"),response.get());

    }

    @Test
    public void onStateChangedTest() throws Exception{

        TestSubscriber testSubscriber = new TestSubscriber();
        contract.OnStateChanged().watch().first().subscribe(testSubscriber);

        Config.TestAccount testAccount = config.accounts.get(1);
        boolean unlocked = ethereumJava.personal.unlockAccount(testAccount.id,testAccount.password,3600);

        if( unlocked ) {
            contract.RentMe().sendTransaction(testAccount.id, new BigInteger("90000"));
        }

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        List<SUInt.SUInt256> states = testSubscriber.getOnNextEvents();
        int state = states.get(0).get().intValue();
        Assert.assertTrue(state==1);
    }

    @Test
    public void sendTransactionAndGetMinedTest() throws Exception{
        TestSubscriber testSubscriber = new TestSubscriber();

        Config.TestAccount testAccount = config.accounts.get(1);
        boolean unlocked = ethereumJava.personal.unlockAccount(testAccount.id,testAccount.password,3600);

        if( unlocked ) {
            Observable<Transaction> obs = contract.RentMe().sendTransactionAndGetMined(testAccount.id, new BigInteger("90000"));
            obs.subscribe(testSubscriber);

            testSubscriber.awaitTerminalEvent();

            testSubscriber.assertCompleted();
            testSubscriber.assertNoErrors();
            testSubscriber.assertValueCount(1);
            assertTrue(testSubscriber.getOnNextEvents().get(0) != null);
        }
        else{
            fail("test account 1 ("+testAccount.id+") must be unlocked ("+testAccount.password+") for the test");
        }

    }

}
