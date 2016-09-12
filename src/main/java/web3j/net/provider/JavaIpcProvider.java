package web3j.net.provider;

import com.etsy.net.JUDS;
import com.etsy.net.UnixDomainSocketClient;

import java.io.IOException;

import web3j.exception.Web3JException;

/**
 * Created by gunicolas on 31/08/16.
 */
public class JavaIpcProvider extends IpcAbstractProvider {

    UnixDomainSocketClient socket;

    public JavaIpcProvider(String _ipcFilePath) throws Web3JException {
        super(_ipcFilePath);
    }

    @Override
    protected void setStreams() throws IOException {
        socket = new UnixDomainSocketClient(this.ipcFilePath, JUDS.SOCK_STREAM);
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
    }
}
