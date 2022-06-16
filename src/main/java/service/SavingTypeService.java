package service;

import models.SavingType;
import models.builder.SavingBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.rowset.CachedRowSet;
import java.sql.Date;
import java.util.ArrayList;

public class SavingTypeService {
    public static ArrayList<SavingType> getAllSavingType() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL SAVBASE_LoadSavingType}", null);
        ArrayList<SavingType> savingTypes = new ArrayList<>();
        while (data.next()) {
            SavingBuilder savingBuilder = new SavingBuilder().setSavingId(data.getInt("MaLoaiSTK"))
                    .setNameSavingType(data.getString("TenLoaiSTK"))
                    .setTenor(data.getInt("KyHan")).setInterestRate(data.getDouble("LaiSuat"))
                    .setActiveDate(data.getDate("NgayApDung")).setRule(data.getString("QDSoTienDuocRut"));
            savingTypes.add(savingBuilder.build());
        }
        return savingTypes;
    }

    public static ObservableList<String> getAll() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_LoadSavingsType()}", null);
        ArrayList<String> savingTypes = new ArrayList<>();

        while (data.next()) {
            savingTypes.add(data.getString("TenLoaiSTK"));
        }
        ObservableList<String> result = FXCollections.observableArrayList(savingTypes);
        return result;

    }

    public static void createNewSavingType(
            String name, Integer tenor, Double interateRate, Date activeDate, Integer minSendingDate, String rule
    ) throws Exception {
        try {
            ExecuteQuery.executeReader("{CALL Savbase_AddNewSavingType(?, ?, ?, ?, ?, ?)}"
                    , new Object[]{name, tenor, interateRate, activeDate, minSendingDate, rule});
        } catch (Exception ex) {
            printException(ex);
        }
    }

    public static void delSavingType(SavingType typeObject) throws Exception {

        var result = ExecuteQuery.executeUpdate("{CALL SAVBASE_DelSavingType(?)}", new Object[]{typeObject.getId()});
    }

    private static void printException(Exception e) {
        System.out.println("<================================================>");
        System.out.println(e.getMessage());
    }
}
