package ethereumjava.module;

import ethereumjava.config.Config;
import ethereumjava.config.RPCTest;
import ethereumjava.config.RxJavaHelper;
import ethereumjava.exception.EthereumJavaException;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.module.objects.Peer;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.List;

import static ethereumjava.config.RxJavaHelper.*;
import static org.junit.Assert.*;

/**
 * Created by gunicolas on 22/11/16.
 */
public class RPCAdminModuleTest extends RPCTest {

    @Test
    public void nodeInfoTest() throws Exception{
        testNodeInfo(ethereumJava.admin.nodeInfo());
    }

    @Test
    public void getNodeInfoTest() throws Exception{
        TestSubscriber subscriber = new TestSubscriber();
        Observable<NodeInfo> nodeInfoObservable = ethereumJava.admin.getNodeInfo();
        nodeInfoObservable.subscribe(subscriber);

        waitTerminalEvent(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);

        testNodeInfo((NodeInfo) subscriber.getOnNextEvents().get(0));
    }

    private void testNodeInfo(NodeInfo nodeInfo){
        assertNotNull(nodeInfo);
        assertTrue(nodeInfo.ip.compareTo(Config.ETH_ADDR)==0);
        assertTrue(nodeInfo.listenAddr.compareTo("["+Config.ETH_ADDR+"]:"+Config.ETH_PORT)==0);
        assertTrue(nodeInfo.ports.discovery == Config.ETH_PORT);
        assertTrue(nodeInfo.ports.listener == Config.ETH_PORT);
        assertTrue(nodeInfo.protocols.eth.network == Config.NETWORK_ID);
        assertTrue(nodeInfo.protocols.eth.difficulty.compareTo(Config.DIFFICULTY)==0);
    }

    @Test
    public void addPeerSucessTest() throws Exception{
        //TODO integration test with peer
        String enode = "enode://7a72845d280720c7d89873aa7abb817a541c83d5930cc4c5cea3c38fbcd0c9f5ef71b45a3e6249d98cdfe8d05cd9d523bff99f78ce8f25c46c43ec189c431a85@[::]:30303";
        boolean added = ethereumJava.admin.addPeer(enode);
        assertTrue(added);
    }

    @Test
    public void addPeerFailTest() throws Exception{
        String enode = "enode://id@ip:port";
        try {
            ethereumJava.admin.addPeer(enode);
        }catch(EthereumJavaException e){
            assertNotNull(e);
        }
    }

    @Test
    public void peersTest() throws Exception{
        List<Peer> peers = ethereumJava.admin.peers();

        testPeers(peers);
    }

    @Test
    public void getPeersTest() throws Exception{
        TestSubscriber testSubscriber = new TestSubscriber();

        Observable<List<Peer>> peersObservable = ethereumJava.admin.getPeers();
        peersObservable.subscribe(testSubscriber);

        waitTerminalEvent(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        testPeers((List<Peer>) testSubscriber.getOnNextEvents().get(0));
    }

    private void testPeers(List<Peer> peers){
        assertTrue(peers.size()==0);
    }



}
