package command;

import constants.Constants;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import models.Account;
import models.SavingAccount;
import service.LoginService;
import service.SavingAccountService;

import java.util.ArrayList;

public class SavingAccountCommand extends BaseCommand  {
    public SavingAccountCommand() {
        task = new Task<ObservableList<SavingAccount>>() {
            @Override
            protected ObservableList<SavingAccount> call() throws Exception {
                Thread.sleep(Constants.DEFAULT_COMMAND_DELAY_TIME);
                ObservableList<SavingAccount> savingAccounts = SavingAccountService.getAll();
                return savingAccounts;
            }
        };
    }
}
