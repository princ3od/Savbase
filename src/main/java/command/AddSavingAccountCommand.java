package command;

import javafx.concurrent.Task;
import service.SavingAccountService;

import java.sql.Date;

public class AddSavingAccountCommand extends BaseCommand {
    public AddSavingAccountCommand(String createType, String CMND, String sex, Date birthDate, String name, String address
            , String email, String phone, String savingType, Date openDate, double initMoney) {
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                SavingAccountService.create(createType, CMND, sex, birthDate, name, address, email, phone, savingType, openDate, initMoney);
                return null;
            }
        };
    }
}
