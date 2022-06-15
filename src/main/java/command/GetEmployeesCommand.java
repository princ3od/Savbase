package command;

import constants.Constants;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import models.Account;
import service.EmployeeService;

public class GetEmployeesCommand extends BaseCommand  {
    public GetEmployeesCommand() {
        task = new Task<ObservableList<Account>>() {
            @Override
            protected ObservableList<Account> call() throws Exception {
                Thread.sleep(Constants.DEFAULT_COMMAND_DELAY_TIME);
                ObservableList<Account> accounts = EmployeeService.getAll();
                return accounts;
            }
        };
    }
}
