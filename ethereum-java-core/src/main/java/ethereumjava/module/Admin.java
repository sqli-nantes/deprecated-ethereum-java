package ethereumjava.module;

import java.util.List;

import rx.Observable;
import ethereumjava.exception.EthereumJavaException;
import ethereumjava.module.objects.NodeInfo;
import ethereumjava.module.objects.Peer;

/**
 * Admin module like the one exposed by Geth node.
 * Gives access to methods to manage, control and monitor a node.
 * See specifications at :
 * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#admin
 *
 * It's an interface because calls are made using Java reflection.
 *
 * Created by gunicolas on 17/08/16.
 */
public interface Admin {

    /**
     * Pass a <em>nodeURL</em> to connect a to a peer on the network.
     * The <em>nodeURL</em> needs to be in enode URL format (enode://id@ip:port).
     * node URL can be found by using admin.nodeInfo.enode
     * Geth will maintain the connection until it shuts down and attempt to reconnect if the connection drops intermittently.
     *
     * @param peer a node URL in enode URL format (enode://id@ip:port)
     * @return true if peer parameter is well formed and a communication request is sent.
     * @throws EthereumJavaException if geth returns an error or communication shut down.
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#adminaddpeer
     */
    boolean addPeer(String peer) throws EthereumJavaException;

    /**
     * Get information on the currently connected node synchronously.
     *
     * @see NodeInfo
     * @return NodeInfo object containing infos on the node
     * @throws EthereumJavaException if geth returns an error or communication shut down
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#adminnodeinfo
     */
    NodeInfo nodeInfo() throws EthereumJavaException;

    /**
     *  Get information on the currently connected node asynchronously.
     *  Returned Observable can be subscribed to wait NodeInfo on a different thread. Prevents blocking the main thread.
     *
     * @see NodeInfo
     * @see Observable
     * @return an Observable of NodeInfo object containing info on the node.
     * @throws EthereumJavaException if geth returns an error or communication shut down
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#adminnodeinfo
     */
    Observable<NodeInfo> getNodeInfo() throws EthereumJavaException;

    /**
     * Get a list of the currently connected peers synchronously
     *
     * @see Peer
     * @return a list of Peer object, currently connected to the node.
     * @throws EthereumJavaException if geth returns an error or communication shut down
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#adminpeers
     */
    List<Peer> peers() throws EthereumJavaException;

    /**
     * Get a list of the currently connected peers asynchronously
     * Returned Observable can be subscribed to wait connected peers info on a different thread. Prevents blocking the main thread.
     *
     * @see Peer
     * @see Observable
     * @return an Observable of list of Peer objects, currently connected to the node.
     * @throws EthereumJavaException if geth returns an error or communication shut down
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#adminpeers
     */
    Observable<List<Peer>> getPeers() throws EthereumJavaException;

}
