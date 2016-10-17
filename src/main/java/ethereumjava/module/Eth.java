package ethereumjava.module;

import java.math.BigDecimal;
import java.math.BigInteger;

import ethereumjava.module.objects.*;
import ethereumjava.solidity.SolidityUtils;
import rx.Observable;
import ethereumjava.module.annotation.ConvertParam;
import ethereumjava.module.annotation.EthereumMethod;
import ethereumjava.module.converter.GetBlockClassConverter;

/**
 * Created by gunicolas on 23/08/16.
 */
public interface  Eth {

    @EthereumMethod(name="getBalance")
    BigInteger balance(String account);
    Observable<BigDecimal> getBalance(String account);

    @EthereumMethod(name="getBlockByHash")
    <T extends TransactionFormat> Block<T> block(Hash hash, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);
    <T extends TransactionFormat> Observable<Block<T>> getBlock(Hash hash, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);

    @EthereumMethod(name="getBlockByNumber")
    <T extends TransactionFormat> Block<T> block(BigInteger number, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);
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

    @EthereumMethod(name="getFilterLogs")
    Observable<Log[]> getFilterLogs(String filterId);

    @EthereumMethod(name="getFilterChanges")
    Observable<Log[]> getFilterChanges(String filterId);

    Observable uninstallFilter(String filterId);






}
