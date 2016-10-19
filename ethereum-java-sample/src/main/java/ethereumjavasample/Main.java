package ethereumjavasample;

import ethereumjava.EthereumJava;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.net.provider.RpcProvider;

/**
 * Created by root on 19/10/16.
 */
public class Main {

    static final String RPC_ADDRESS = "http://localhost:8547";


    public static void main(String[] args){

        EthereumJava ethereumJava = new EthereumJava.Builder()
                .provider(new RpcProvider(RPC_ADDRESS))
                .build();

        NodeInfo nodeInfo = ethereumJava.admin.nodeInfo();
        System.out.println(nodeInfo.enode);



        System.out.println("it works");
    }

}
