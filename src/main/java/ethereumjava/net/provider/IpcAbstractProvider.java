package ethereumjava.net.provider;

import ethereumjava.exception.EthereumJavaException;

/**
 * Created by gunicolas on 31/08/16.
 */
public abstract class IpcAbstractProvider extends AbstractProvider {

    public final String ipcFilePath;

    public IpcAbstractProvider(String _ipcFilePath) throws EthereumJavaException {
        super();
        this.ipcFilePath = _ipcFilePath;
    }





}
