package command;

import constants.Constants;
import javafx.concurrent.Task;
import utils.LocalSettings;

public class StartupCommand extends BaseCommand {

    public StartupCommand() {
        task = new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(Constants.DEFAULT_COMMAND_DELAY_TIME);
                LocalSettings.getConnectionString();
                return null;
            }
        };
    }
}
