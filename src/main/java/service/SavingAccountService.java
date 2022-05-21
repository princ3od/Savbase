package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.SavingAccount;
import models.builder.SavingAccountBuilder;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;


public class SavingAccountService {
    public static ObservableList<SavingAccount> getAll() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_GetSavingAccounts()}", null);
        ArrayList<SavingAccount> savingAccounts = new ArrayList<>();

        while (data.next()) {
            SavingAccountBuilder savingAccountBuilder = new SavingAccountBuilder().setName(data.getString("TenKH"))
                    .setId(data.getInt("MaSTK")).setNationalId(data.getString("CCCD/CMND"))
                    .setSurplus(data.getDouble("SoDu")).setSavingAccountType(data.getString("TenLoaiSTK"))
                    .setEmail(data.getString("Email")).setSex(data.getBoolean("GioiTinh")  ? "Nam" : "Nữ")
                    .setPhoneNumber(data.getString("DienThoai")).setAddress(data.getString("DiaChi"))
                    .setOpenDate(data.getDate("NgayMoSo"));
            savingAccounts.add(savingAccountBuilder.getResult());
        }
        ObservableList<SavingAccount> result = FXCollections.observableArrayList(savingAccounts);
        return result;

    }
}
