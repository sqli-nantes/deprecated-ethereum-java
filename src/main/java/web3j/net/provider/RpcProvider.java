package web3j.net.provider;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gunicolas on 18/08/16.
 */
public class RpcProvider extends AbstractProvider {

    HttpURLConnection connection;
    String address;

    public RpcProvider(String address) {
        super();
        this.address = address;
    }

    @Override
    protected void setStreams() throws IOException {
        URL url = new URL(address);
        this.connection = (HttpURLConnection) url.openConnection();
        this.connection.setRequestMethod("POST");
        this.connection.setDoInput(true);
        this.connection.setDoOutput(true);
        this.outputStream = this.connection.getOutputStream();
        this.inputStream = this.connection.getInputStream();
    }

}
