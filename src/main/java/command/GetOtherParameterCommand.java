package command;

import constants.Constants;
import javafx.concurrent.Task;
import models.OtherParameter;
import service.OtherParameterService;

public class GetOtherParameterCommand extends BaseCommand  {
    public GetOtherParameterCommand() {
        task = new Task<OtherParameter>() {
            @Override
            protected OtherParameter call() throws Exception {
                Thread.sleep(Constants.DEFAULT_COMMAND_DELAY_TIME);
                OtherParameter otherParameter = OtherParameterService.get();
                return otherParameter;
            }
        };
    }
}
