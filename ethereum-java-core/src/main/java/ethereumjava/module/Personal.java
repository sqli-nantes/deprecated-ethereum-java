package ethereumjava.module;

import java.util.List;

import rx.Observable;

/**
 * Created by gunicolas on 22/08/16.
 */
public interface Personal {

    List<String> listAccounts();

    boolean unlockAccount(String address,String password,int duration);

    String newAccount(String password);


}
