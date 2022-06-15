package command;

import javafx.concurrent.Task;
import service.OtherParameterService;

public class EditOtherParameterCommand extends BaseCommand {
    public EditOtherParameterCommand(double minDepositAmount, double minInitDeposit, boolean controlClosingSaving) {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                OtherParameterService.update(minDepositAmount, minInitDeposit, controlClosingSaving);
                return null;
            }
        };
    }
}
