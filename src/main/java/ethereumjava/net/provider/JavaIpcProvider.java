package ethereumjava.net.provider;



import java.io.IOException;

import ethereumjava.exception.EthereumJavaException;

/**
 * Created by gunicolas on 31/08/16.
 */
public class JavaIpcProvider extends IpcAbstractProvider {


    public JavaIpcProvider(String _ipcFilePath) throws EthereumJavaException {
        super(_ipcFilePath);
    }

    @Override
    protected void setStreams() throws IOException {
    }
}
