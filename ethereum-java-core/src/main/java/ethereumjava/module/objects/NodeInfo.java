package ethereumjava.module.objects;

import java.math.BigInteger;

/**
 * Created by gunicolas on 18/08/16.
 */
public class NodeInfo {

    public String enode;
    public String id;
    public String ip;
    public String listenAddr;
    public String name;
    public Ports ports;
    public Protocols protocols;

    public class Protocols{

        public Eth eth;

        public class Eth{
            public BigInteger difficulty;
            public String genesis;
            public String head;
            public int network;

            @Override
            public String toString() {
                return "Eth{" +
                        "difficulty=" + difficulty +
                        ", genesis='" + genesis + '\'' +
                        ", head='" + head + '\'' +
                        ", network=" + network +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Protocols{" +
                    "eth=" + eth +
                    '}';
        }
    }

    public class Ports{
        public int discovery;
        public int listener;

        @Override
        public String toString() {
            return "Ports{" +
                    "discovery=" + discovery +
                    ", listener=" + listener +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "enode='" + enode + '\'' +
                ", id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", listenAddr='" + listenAddr + '\'' +
                ", name='" + name + '\'' +
                ", ports=" + ports +
                ", protocols=" + protocols +
                '}';
    }
}
