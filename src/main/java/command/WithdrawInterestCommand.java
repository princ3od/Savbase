package command;

import javafx.concurrent.Task;
import service.TransactionService;



public class WithdrawInterestCommand extends BaseCommand {
    public WithdrawInterestCommand(int accountId) {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                TransactionService.withdrawInterest(accountId);
                return null;
            }
        };
    }
}
