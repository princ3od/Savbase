package command;

import javafx.concurrent.Task;
import models.SavingType;
import service.SavingTypeService;

import java.util.ArrayList;

public class GetAllSavingsTypeCommand extends BaseCommand {

    public  GetAllSavingsTypeCommand(){
        task = new Task<ArrayList<SavingType>>() {
            @Override
            protected ArrayList<SavingType> call() throws Exception {
                ArrayList<SavingType> savingTypes = SavingTypeService.getAllSavingType();
                return savingTypes;
            }
        };
    }
}
