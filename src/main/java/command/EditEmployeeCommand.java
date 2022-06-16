package command;

import javafx.concurrent.Task;
import service.EmployeeService;


import java.sql.Date;

public class EditEmployeeCommand extends BaseCommand {
    public EditEmployeeCommand(int staffID, int position, String name, String phone, String email, String address) {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                EmployeeService.update(staffID, position, name, phone, address, email);
                return null;
            }
        };
    }
}
