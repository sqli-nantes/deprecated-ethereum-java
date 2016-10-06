package ethereumjava.net.provider;

import com.etsy.net.JUDS;
import com.etsy.net.UnixDomainSocketClient;

import java.io.IOException;

import ethereumjava.exception.EthereumJavaException;

/**
 * Created by gunicolas on 31/08/16.
 */
public class JavaIpcProvider extends IpcAbstractProvider {

    UnixDomainSocketClient socket;

    public JavaIpcProvider(String _ipcFilePath) throws EthereumJavaException {
        super(_ipcFilePath);
    }

    @Override
    protected void setStreams() throws IOException {
        socket = new UnixDomainSocketClient(this.ipcFilePath, JUDS.SOCK_STREAM);
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
    }
}
