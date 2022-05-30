package command;

import javafx.concurrent.Task;
import models.SavingType;
import service.SavingTypeService;

public class DelSavingTypeCommand  extends  BaseCommand{
    public  DelSavingTypeCommand(SavingType delSavingObject){
        task = new Task() {
            @Override
            protected Object call() throws Exception {
                SavingTypeService.delSavingType(delSavingObject);
                return null;
            }
        };
    }
}
