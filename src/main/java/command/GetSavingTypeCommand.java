package command;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import models.SavingAccount;
import service.SavingTypeService;

public class GetSavingTypeCommand extends BaseCommand{
    public GetSavingTypeCommand() {
        task = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws Exception {
                 ObservableList<String> savingTypes = SavingTypeService.getAll();
                return savingTypes;
            }
        };
    }
}
