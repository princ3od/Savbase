package service;

import models.SavingType;
import models.builder.SavingBuilder;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;

public class SavingTypeService {
    public static ArrayList<SavingType> getAllSavingType() throws Exception {
        CachedRowSet data =  ExecuteQuery.executeReader("{CALL SAVBASE_LoadSavingType}",null);
        ArrayList<SavingType> savingTypes = new ArrayList<>();

        while (data.next()){

            SavingBuilder savingBuilder = new SavingBuilder().setSavingId(data.getInt("MaLoaiSTK"))
                    .setNameSavingType(data.getString("TenLoaiSTK"))
                    .setTenor(data.getInt("KyHan")).setInterestRate(data.getDouble("LaiSuat"))
                    .setActiveDate(data.getDate("NgayApDung")).setRule(data.getString("QDSoTienDuocRut"));
            savingTypes.add(savingBuilder.build());

        }
        return  savingTypes;
    }
}
