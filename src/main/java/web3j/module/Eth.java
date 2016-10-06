package web3j.module;

import java.math.BigDecimal;
import java.math.BigInteger;

import rx.Observable;
import web3j.module.annotation.ConvertParam;
import web3j.module.annotation.EthereumMethod;
import web3j.module.converter.GetBlockClassConverter;
import web3j.module.objects.Block;
import web3j.module.objects.Hash;
import web3j.module.objects.Transaction;
import web3j.module.objects.TransactionReceipt;
import web3j.module.objects.TransactionRequest;
import web3j.module.objects.Web3JType;

/**
 * Created by gunicolas on 23/08/16.
 */
public interface Eth {

    @web3j.module.annotation.EthereumMethod(name="getBalance")
    BigInteger balance(String account);
    Observable<BigDecimal> getBalance(String account);

    /* transactionType is Transaction object or Hash string. */
    @web3j.module.annotation.EthereumMethod(name="getBlockByHash")
    <T extends Web3JType> Block<T> block(Hash hash, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);
    <T extends Web3JType> Observable<Block<T>> getBlock(Hash hash, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);

    @web3j.module.annotation.EthereumMethod(name="getBlockByNumber")
    <T extends Web3JType> Block<T> block(BigInteger number, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);
    <T extends Web3JType> Observable<Block<T>> getBlock(BigInteger number, @ConvertParam(with=GetBlockClassConverter.class) Class<T> transactionType);

    @web3j.module.annotation.EthereumMethod(name="getTransactionByHash")
    Transaction transaction(Hash hash);
    Observable<Transaction> getTransaction(Hash hash);

    @web3j.module.annotation.EthereumMethod(name="getTransactionReceipt")
    TransactionReceipt transactionReceipt(Hash hash);
    Observable<TransactionReceipt> getTransactionReceipt(Hash hash);

    Hash sendTransaction(TransactionRequest request);
    @EthereumMethod(name="sendTransaction")
    Observable<Hash> sendTransactionAsync(TransactionRequest request);

    String call(TransactionRequest request);
    String getCall(TransactionRequest request);






}
