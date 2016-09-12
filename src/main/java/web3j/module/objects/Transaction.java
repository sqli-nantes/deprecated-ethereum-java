package web3j.module.objects;

import java.math.BigInteger;

/**
 * Created by gunicolas on 26/08/16.
 */
public class Transaction extends Web3JType {

    public Hash hash;
    public BigInteger nonce;
    public Hash blockHash;
    public BigInteger blockNumber;
    public BigInteger transactionIndex;
    public String from;
    public String to;
    public BigInteger value;
    public BigInteger gasPrice;
    public BigInteger gas;
    public BigInteger number;
    public String data;


}
