package ethereumjava.module.objects;

import java.math.BigDecimal;
import java.math.BigInteger;

import ethereumjava.solidity.SolidityUtils;

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
        this(fromHex, toHex,null,null);
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

    public void setGas(BigInteger gas){
        this.gasHex = SolidityUtils.toHex(new BigDecimal(gas));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if( fromHex != null ) sb.append("\"from\":\"").append(fromHex).append('\"');
        if( toHex != null ) sb.append(",\"to\":\"").append(toHex).append('\"');
        if( gasHex != null ) sb.append(",\"gas\":\"").append(gasHex).append('\"');
        if( valueHex != null ) sb.append(",\"value\":\"").append(valueHex).append('\"');
        if( dataHex != null ) sb.append(",\"data\":\"").append(dataHex).append('\"');
        sb.append('}');

        return sb.toString();

    }
}
