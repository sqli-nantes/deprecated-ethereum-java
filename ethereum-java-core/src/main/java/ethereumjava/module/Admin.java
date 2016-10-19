package ethereumjava.module;

import java.util.List;

import rx.Observable;
import ethereumjava.exception.EthereumJavaException;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.module.objects.Peer;

/**
 * Created by gunicolas on 17/08/16.
 */
public interface Admin {

    boolean addPeer(String peer) throws EthereumJavaException;

    NodeInfo nodeInfo() throws EthereumJavaException;
    Observable<NodeInfo> getNodeInfo() throws EthereumJavaException;

    List<Peer> peers() throws EthereumJavaException;
    Observable<List<Peer>> getPeers() throws EthereumJavaException;

}
