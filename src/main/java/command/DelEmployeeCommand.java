package command;

import javafx.concurrent.Task;
import service.EmployeeService;


import java.sql.Date;

public class DelEmployeeCommand extends BaseCommand {
    public DelEmployeeCommand(int staffID) {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                EmployeeService.delete(staffID);
                return null;
            }
        };
    }
}
