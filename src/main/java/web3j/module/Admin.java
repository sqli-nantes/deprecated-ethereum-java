package web3j.module;

import java.util.List;

import rx.Observable;
import web3j.exception.Web3JException;
import web3j.module.objects.NodeInfo;
import web3j.module.objects.Peer;

/**
 * Created by gunicolas on 17/08/16.
 */
public interface Admin {

    boolean addPeer(String peer) throws Web3JException;

    NodeInfo nodeInfo() throws Web3JException;
    Observable<NodeInfo> getNodeInfo() throws Web3JException;

    List<Peer> peers() throws Web3JException;
    Observable<List<Peer>> getPeers() throws Web3JException;

}
