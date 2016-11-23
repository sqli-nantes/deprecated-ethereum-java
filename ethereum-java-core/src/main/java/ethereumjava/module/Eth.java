package ethereumjava.module;

import ethereumjava.module.annotation.ConvertParam;
import ethereumjava.module.annotation.EthereumMethod;
import ethereumjava.module.annotation.GenericTypeIndex;
import ethereumjava.module.converter.GetBlockClassConverter;
import ethereumjava.module.objects.*;
import rx.Observable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by gunicolas on 23/08/16.
 */
public interface  Eth {

    @EthereumMethod(name="getBalance")
    BigInteger balance(String account, Block.BlockParameter parameter);
    Observable<BigInteger> getBalance(String account, Block.BlockParameter parameter);

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
    <T> Observable<List<T>> getFilterChanges(String filterId);

    Observable uninstallFilter(String filterId);






}
