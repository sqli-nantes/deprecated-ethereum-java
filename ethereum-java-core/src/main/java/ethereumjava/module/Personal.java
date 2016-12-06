package ethereumjava.module;

import java.util.List;

/**
 * Personal module like the one exposed by Geth node.
 * Gives access to methods to manage, control and monitor accounts on the node.
 * See specifications at :
 *
 * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#personal
 *
 * It's an interface because calls are made using Java reflection.
 *
 * Created by gunicolas on 17/08/16.
 */
public interface Personal {

    /**
     * List all accounts/wallets available on the node.
     *
     * @return a list of account address, available on the node
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#personallistaccounts
     */
    List<String> listAccounts();

    /**
     * Unlock the account with the given address, password and during the given duration.
     *
     * @param address  account address, given when account is created.
     * @param password account password, set at the account creation
     * @param duration duration of the unlocked session
     * @return true if the account has been unlocked with the given password
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#personalunlockaccount
     */
    boolean unlockAccount(String address, String password, int duration);

    /**
     * Creates a new account/wallet on the node, protected by the given password
     *
     * @param password password which will be used to unlock the account
     * @return generated address of the newly created account
     * @link https://github.com/ethereum/go-ethereum/wiki/JavaScript-Console#personalnewaccount
     */
    String newAccount(String password);

}
