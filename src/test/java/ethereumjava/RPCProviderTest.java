package ethereumjava;

import ethereumjava.module.objects.Block;
import ethereumjava.module.objects.Hash;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.solidity.ContractType;
import ethereumjava.solidity.SolidityEvent;
import ethereumjava.solidity.SolidityFunction;
import ethereumjava.solidity.types.SUInt;
import ethereumjava.solidity.types.SVoid;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 18/08/16.
 */
public class RPCProviderTest extends EthereumJavaTest {

    @Test
    public void testModules() throws Exception{

        NodeInfo nodeInfo = ethereumJava.admin.nodeInfo();
        System.out.println(nodeInfo.toString());

        Block<Hash> block = ethereumJava.eth.block(BigInteger.ZERO, Hash.class);
        System.out.println(block.toString());
    }


    interface ChoupetteContract extends ContractType {


        @SolidityEvent.Anonymous(value = false)
        @SolidityEvent.Parameters(value = {
            @SolidityEvent.Parameter(indexed = false, name = "state", type = SUInt.SUInt256.class)
        })
        SolidityEvent OnStateChanged();

        @SolidityFunction.ReturnType(value = SVoid.class)
        SolidityFunction RentMe();

        @SolidityFunction.ReturnType(value = SVoid.class)
        SolidityFunction StopRent();

        @SolidityFunction.ReturnType(value = SVoid.class)
        SolidityFunction StartRent();

        @SolidityFunction.ReturnType(value = SVoid.class)
        SolidityFunction ValidateTravel();

        @SolidityFunction.ReturnType(value = SVoid.class)
        SolidityFunction GoTo(SUInt.SUInt256 x, SUInt.SUInt256 y);

        @SolidityFunction.ReturnType(value = SUInt.SUInt256.class)
        SolidityFunction GetPrice();
    }


    final String CONTRACT_ADDRESS = "0x4f81d84cccd66f12836625281ae249f8b0586920";

    @Test
    public void testContractRentMe() throws Exception{

        ChoupetteContract choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);
        Hash txHash = choupetteContract.RentMe().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStopRent() throws Exception{

        ChoupetteContract choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);
        Hash txHash = choupetteContract.StopRent().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStartRent() throws Exception{

        ChoupetteContract choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);
        Hash txHash = choupetteContract.StartRent().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractValidateTravel() throws Exception{

        ChoupetteContract choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);
        Hash txHash = choupetteContract.ValidateTravel().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGoTo() throws Exception{

        ChoupetteContract choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);
        Hash txHash = choupetteContract
                .GoTo(  SUInt.fromBigInteger256(BigInteger.valueOf(3)),
                        SUInt.fromBigInteger256(BigInteger.valueOf(2)))
                .sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGetPrice() throws Exception{

        ChoupetteContract choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);

        SUInt.SUInt256 response = (SUInt.SUInt256) choupetteContract.GetPrice().call();

        assertEquals(new BigInteger("1000000"),response.get());

    }

    @Test
    public void testContractOnStateChanged() throws Exception{

        ChoupetteContract choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);

        Observable obs = choupetteContract.OnStateChanged().watch();
        obs.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });







    }




}
