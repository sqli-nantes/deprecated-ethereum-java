package web3j.module.objects;

import java.math.BigDecimal;
import java.math.BigInteger;

import web3j.solidity.SolidityUtils;

/**
 * Created by gunicolas on 30/08/16.
 */
public class TransactionRequest {

    String fromHex;
    String toHex;
    String gasHex;

    String valueHex;
    String dataHex;

    public TransactionRequest(String fromHex, String toHex) {
        this.fromHex = fromHex;
        this.toHex = toHex;
    }

    public TransactionRequest(String from, String to, String value, String data) {
        this(from,to,new BigInteger("90000"),value,data);
    }

    public TransactionRequest(String from, String to, BigInteger gas, String value, String data) throws IllegalArgumentException {
        this.fromHex = SolidityUtils.toHex(from);
        this.toHex = SolidityUtils.toHex(to);
        this.gasHex = SolidityUtils.toHex(new BigDecimal(gas));
        this.valueHex = SolidityUtils.toHex(value);
        this.dataHex = SolidityUtils.toHex(data);
    }

    public void setDataHex(String dataHex) {
        this.dataHex = dataHex;
    }

    public void setToHex(String toHex) {
        this.toHex = toHex;
    }

    @Override
    public String toString() {
        return "{" +
                "\"from\":\"" + fromHex + '\"' +
                ",\"to\":\"" + toHex + '\"' +
                ",\"gas\":\"" + gasHex + '\"' +
                ",\"value\":\"" + valueHex + '\"' +
                ",\"data\":\"" + dataHex + '\"' +
                '}';
    }
}
