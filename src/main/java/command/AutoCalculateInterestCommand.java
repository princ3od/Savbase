package command;

import javafx.concurrent.Task;
import service.SystemService;

public class AutoCalculateInterestCommand extends BaseCommand {
    public AutoCalculateInterestCommand() {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                SystemService.autoCalculateInterest();
                return null;
            }
        };
    }
}
