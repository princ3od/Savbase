package command;

import javafx.concurrent.Task;
import service.TransactionService;


import java.sql.Date;

public class AddTransactionCommand extends BaseCommand {
    public AddTransactionCommand(int type, int accountId, int employeeId, Date date, double amount) {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                TransactionService.create(type, accountId, employeeId, date, amount);
                return null;
            }
        };
    }
}
