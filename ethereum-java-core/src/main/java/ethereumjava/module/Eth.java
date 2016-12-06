package ethereumjava.module;

import ethereumjava.module.annotation.ConvertParam;
import ethereumjava.module.annotation.EthereumMethod;
import ethereumjava.module.annotation.ExcludeFromRequest;
import ethereumjava.module.annotation.GenericTypeIndex;
import ethereumjava.module.converter.GetBlockClassConverter;
import ethereumjava.module.objects.*;
import rx.Observable;

import java.math.BigInteger;
import java.util.List;

/**
 * Eth module like the one exposed by Geth node.
 * Gives access to methods to communicate, write and read on the blockchain
 * See specifications at :
 * @link https://github.com/ethereum/wiki/wiki/JavaScript-API#web3eth
 *
 * It's an interface because calls are made using Java reflection.
 *
 * Created by gunicolas on 23/08/16.
 */
public interface  Eth {

    /**
     * Get the balance of the given account at a given block synchronously
     *
     * @see ethereumjava.module.objects.Block.BlockParameter
     * @see BigInteger
     * @param account the account address to get the balance of
     * @param parameter the reference block used to get the balance
     * @return a BigInteger instance of the given account balance at the given block reference <b>in WEI</b>.
     * @link https://github.com/ethereum/wiki/wiki/JavaScript-API#web3ethgetbalance
     */
    @EthereumMethod(name="getBalance")
    BigInteger balance(String account, Block.BlockParameter parameter);

    /**
     * Get the balance of the given account at a given block asynchronously.
     * Returned Observable can be subscribed to wait balance on a different thread. Prevents blocking the main thread.
     *
     * @see ethereumjava.module.objects.Block.BlockParameter
     * @see BigInteger
     * @see Observable
     * @param account the account address to get the balance of
     * @param parameter the reference block used to get the balance
     * @return an Observable on a BigInteger instance of the given account balance at the given block reference <b>in WEI</b>.
     */
    Observable<BigInteger> getBalance(String account, Block.BlockParameter parameter);

    /**
     *
     * @param hash
     * @param transactionType
     * @param <T>
     * @return
     */
    @EthereumMethod(name="getBlockByHash")
    @GenericTypeIndex(1)
    <T extends TransactionFormat> Block<T> block(Hash hash, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);
    @GenericTypeIndex(1)
    <T extends TransactionFormat> Observable<Block<T>> getBlock(Hash hash, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);

    @EthereumMethod(name="getBlockByNumber")
    @GenericTypeIndex(1)
    <T extends TransactionFormat> Block<T> block(BigInteger number, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);
    @GenericTypeIndex(1)
    <T extends TransactionFormat> Observable<Block<T>> getBlock(BigInteger number, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);

    @EthereumMethod(name="getTransactionByHash")
    Transaction transaction(Hash hash);
    Observable<Transaction> getTransaction(Hash hash);

    @EthereumMethod(name="getTransactionReceipt")
    TransactionReceipt transactionReceipt(Hash hash);
    Observable<TransactionReceipt> getTransactionReceipt(Hash hash);

    Hash sendTransaction(TransactionRequest request);
    @EthereumMethod(name="sendTransaction")
    Observable<Hash> sendTransactionAsync(TransactionRequest request);

    String call(TransactionRequest request,String callOnBlock);

    Observable<String> newFilter(FilterOptions options);
    Observable<String> newBlockFilter();

    @EthereumMethod(name="getFilterLogs")
    Observable<Log[]> getFilterLogs(String filterId);

    /**
     * Polling method for a filter, which returns an array of logs which occurred since last poll.
     * @param filterId filter id got with eth.newFilter()
     * @param <T> returned log object type. Differs when it's a BlockFilter (Hash) or a DefaultFilter (Log)
     * @return an observable of List of T objects. Logs of changes since last poll
     */
    @EthereumMethod(name="getFilterChanges")
    @GenericTypeIndex(1)
    <T> Observable<List<T>> getFilterChanges(String filterId,@ExcludeFromRequest Class<T> logType);

    Observable uninstallFilter(String filterId);






}
