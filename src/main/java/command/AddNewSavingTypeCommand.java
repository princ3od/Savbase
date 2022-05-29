package command;

import com.sun.javafx.tk.Toolkit;
import javafx.concurrent.Task;
import models.SavingType;
import service.SavingAccountService;
import service.SavingTypeService;

import java.sql.Date;

public class AddNewSavingTypeCommand  extends  BaseCommand{
    public  AddNewSavingTypeCommand(String name, Integer tenor, Double interateRate, Date activeDate, Integer minSendingDate, String rule){
        task = new Task<>() {
            @Override
            protected Object call() throws Exception {
                SavingTypeService.createNewSavingType(name, tenor, interateRate, activeDate, minSendingDate, rule);
                return null;
            }
        };
    }
}
