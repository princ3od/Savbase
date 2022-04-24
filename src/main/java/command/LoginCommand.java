package command;

import javafx.concurrent.Task;
import models.Account;
import service.LoginService;

public class LoginCommand extends BaseCommand {
    public LoginCommand(String username, String password) {
        task = new Task<Account>() {
            @Override
            protected Account call() throws Exception {
                Account account = LoginService.authenticate(username, password);
                return account;
            }
        };
    }
}
