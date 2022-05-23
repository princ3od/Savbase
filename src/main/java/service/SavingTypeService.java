package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;

public class SavingTypeService {
    public static ObservableList<String> getAll() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_LoadSavingsType()}", null);
        ArrayList<String> savingTypes = new ArrayList<>();

        while (data.next()) {
            savingTypes.add(data.getString("TenLoaiSTK"));
        }
        ObservableList<String> result = FXCollections.observableArrayList(savingTypes);
        return result;

    }
}
