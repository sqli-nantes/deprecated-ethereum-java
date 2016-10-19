package ethereumjava.module.objects;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by gunicolas on 18/08/16.
 */
public class Peer {

    List<String> caps;
    String id;
    String name;
    Network network;
    Protocols protocols;

    public class Network{
        public String localAddress;
        public String remoteAddress;

        @Override
        public String toString() {
            return "Network{" +
                    "localAddress='" + localAddress + '\'' +
                    ", remoteAddress='" + remoteAddress + '\'' +
                    '}';
        }
    }

    public class Protocols{

        public Eth eth;

        public class Eth{
            public BigInteger difficulty;
            public String head;
            public int version;

            @Override
            public String toString() {
                return "Eth{" +
                        "difficulty=" + difficulty +
                        ", head='" + head + '\'' +
                        ", version=" + version +
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

    @Override
    public String toString() {
        return "Peer{" +
                "caps=" + caps +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", network=" + network +
                ", protocols=" + protocols +
                '}';
    }
}
