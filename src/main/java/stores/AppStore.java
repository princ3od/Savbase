package stores;

import models.Account;

public class AppStore {
    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account currentAccount) {
        AppStore.currentAccount = currentAccount;
    }

    static Account currentAccount = null;

}
