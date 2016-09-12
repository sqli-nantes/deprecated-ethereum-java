package web3j.net.provider;

import web3j.exception.Web3JException;

/**
 * Created by gunicolas on 31/08/16.
 */
public abstract class IpcAbstractProvider extends AbstractProvider {

    final String ipcFilePath;

    public IpcAbstractProvider(String _ipcFilePath) throws Web3JException {
        super();
        this.ipcFilePath = _ipcFilePath;
    }





}
