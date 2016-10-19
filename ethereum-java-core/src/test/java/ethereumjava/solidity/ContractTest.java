package ethereumjava.solidity;

import ethereumjava.EthereumJava;
import ethereumjava.module.objects.Hash;
import ethereumjava.net.provider.RpcProvider;
import ethereumjava.solidity.types.SUInt;
import ethereumjava.solidity.types.SVoid;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by gunicolas on 30/08/16.
 */
public class ContractTest {


    EthereumJava ethereumJava;


    final String ACCOUNT = "0xccb8af9d259115163400362c4dfc5f4a0c1e5109";
    final String CONTRACT_ADDRESS = "0x8571f5f9df8623ad1e702f6e41bb4dd1d21b8a41";

    final String PASSWORD = "toto";

    ChoupetteContract choupetteContract;

    @Before
    public void setup() throws Exception{
        ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider("http://localhost:8547"))
                .build();

        ethereumJava.personal.unlockAccount(ACCOUNT,PASSWORD,3600);
        choupetteContract = (ChoupetteContract) ethereumJava.contract.withAbi(ChoupetteContract.class).at(CONTRACT_ADDRESS);
    }


    @After
    public void after() throws Exception{
        ethereumJava.close();
    }

    interface ChoupetteContract extends ContractType {

        @SolidityEvent.Anonymous(false)
        @SolidityEvent.Parameters({
                @SolidityEvent.Parameter(indexed = false, name = "state", type = SUInt.SUInt256.class)
        })
        SolidityEvent<SUInt.SUInt256> OnStateChanged();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction RentMe();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction StopRent();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction StartRent();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction ValidateTravel();

        @SolidityFunction.ReturnType(SVoid.class)
        SolidityFunction GoTo(SUInt.SUInt256 x, SUInt.SUInt256 y);
        @SolidityFunction.ReturnType(SUInt.SUInt256.class)
        SolidityFunction GetPrice();


    }

    @Test
    public void testContractRentMe() throws Exception{

        Hash txHash = choupetteContract.RentMe().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStopRent() throws Exception{

        Hash txHash = choupetteContract.StopRent().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractStartRent() throws Exception{

        Hash txHash = choupetteContract.StartRent().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);
    }

    @Test
    public void testContractValidateTravel() throws Exception{

        Hash txHash = choupetteContract.ValidateTravel().sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGoTo() throws Exception{

        Hash txHash = choupetteContract
                .GoTo(  SUInt.fromBigInteger256(BigInteger.valueOf(3)),
                        SUInt.fromBigInteger256(BigInteger.valueOf(2)))
                .sendTransaction(ACCOUNT, new BigInteger("90000"));

        assertTrue(txHash!=null);

    }

    @Test
    public void testContractGetPrice() throws Exception{

        SUInt.SUInt256 response = (SUInt.SUInt256) choupetteContract.GetPrice().call();

        assertEquals(new BigInteger("0"),response.get());

    }

    @Test
    public void testContractOnStateChanged() throws Exception{

        new Thread(new Runnable() {
            @Override
            public void run() {

            final SolidityEvent event = choupetteContract.OnStateChanged();
            Observable<SUInt.SUInt256> obs = event.watch();
            obs.subscribe(new Subscriber<SUInt.SUInt256>() {
                @Override
                public void onCompleted() {
                    System.out.println("end of filter");
                }

                @Override
                public void onError(Throwable e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void onNext(SUInt.SUInt256 ret) {
                    System.out.println("state : " + ret.asString());
                }
            });

            }
        }).start();

        synchronized (this){
            this.wait(1000);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Call RentMe()");
                choupetteContract.RentMe().sendTransaction(ACCOUNT, new BigInteger("90000"));
            }
        }).start();

        synchronized (this){
            this.wait();
        }



    }
}
