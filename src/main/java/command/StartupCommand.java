package command;

import javafx.concurrent.Task;
import utils.LocalSettings;

public class StartupCommand extends BaseCommand {

    public StartupCommand() {
        task = new Task() {
            @Override
            protected Object call() throws Exception {
                Thread.sleep(2000);
                LocalSettings.getConnectionString();
                return null;
            }
        };
    }
}
