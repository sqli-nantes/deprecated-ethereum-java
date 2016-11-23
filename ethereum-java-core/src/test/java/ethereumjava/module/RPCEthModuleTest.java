package ethereumjava.module;

import ethereumjava.config.Config;
import ethereumjava.config.RPCTest;
import ethereumjava.exception.EthereumJavaException;
import ethereumjava.module.objects.Block;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.Transaction;
import ethereumjava.module.objects.TransactionFormat;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static ethereumjava.config.RxJavaHelper.waitTerminalEvent;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 22/11/16.
 */
public class RPCEthModuleTest extends RPCTest {

    @Test
    public void blockByNumberTxObjectTest() throws Exception{

        Block<Transaction> block = ethereumJava.eth.block(BigInteger.ZERO, Transaction.class);
        testBlock(block);
    }

    @Test
    public void blockByNumberTxHashTest() throws Exception{
        Block<Hash> block = ethereumJava.eth.block(BigInteger.ZERO, Hash.class);
        testBlock(block);
    }

    @Test
    public void getBlockByNumberTxObjectTest() throws Exception{
        TestSubscriber testSubscriber = new TestSubscriber();
        ethereumJava.eth.getBlock(BigInteger.ZERO, Transaction.class)
            .subscribe(testSubscriber);

        waitTerminalEvent(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);

        testBlock((Block<? extends TransactionFormat>) testSubscriber.getOnNextEvents().get(0));
    }

    @Test
    public void getBlockByNumberTxHashTest() throws Exception{
        TestSubscriber testSubscriber = new TestSubscriber();

        ethereumJava.eth.getBlock(BigInteger.ZERO, Hash.class)
            .subscribe(testSubscriber);

        waitTerminalEvent(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);

        testBlock((Block<? extends TransactionFormat>) testSubscriber.getOnNextEvents().get(0));

    }

    private void testBlock(Block<? extends TransactionFormat> block){
        assertNotNull(block);
        assertTrue(block.number.compareTo(BigInteger.ZERO)==0);
        assertTrue(block.transactions.size()==0);

    }

    @Test
    public void balanceTest() throws Exception{
        String account = ethereumJava.personal.listAccounts().get(0);

        BigInteger balance = ethereumJava.eth.balance(account, Block.BlockParameter.LATEST);
        testBalance(balance);
    }

    @Test
    public void getBalanceTest() throws Exception{

        TestSubscriber testSubscriber = new TestSubscriber();
        String account = ethereumJava.personal.listAccounts().get(0);

        ethereumJava.eth.getBalance(account, Block.BlockParameter.LATEST)
            .subscribe(testSubscriber);

        waitTerminalEvent(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
        List<BigInteger> onNexts = testSubscriber.getOnNextEvents();
        testBalance(onNexts.get(0));

    }

    private void testBalance(BigInteger balance){
        assertTrue(balance.toString().compareTo("100000000000000000000000000000")==0);
    }

}
