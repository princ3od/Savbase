package command;

import javafx.concurrent.Task;
import service.EmployeeService;


import java.sql.Date;

public class AddEmployeeCommand extends BaseCommand {
    public AddEmployeeCommand(int position, String name, String ID, boolean sex, Date birthDate, String phone,
                              String address, String password, String email) {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                EmployeeService.create(position, name, ID, sex, birthDate, phone, address, password, email);
                return null;
            }
        };
    }
}
