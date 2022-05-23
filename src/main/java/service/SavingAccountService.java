package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.SavingAccount;
import models.builder.SavingAccountBuilder;

import javax.sql.rowset.CachedRowSet;
import java.sql.Date;
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

    public static void create(String createType, String CMND, String sex, Date birthDate, String name, String address
            , String email, String phone, String savingType, Date openDate, double initMoney) throws Exception {
        if (createType.equals("Cho khách hàng cũ")) {
           ExecuteQuery.executeReader("{CALL Savbase_CreateNewAccountForOldUser(?, ?, ?, ?)}"
                    , new Object[]{CMND, savingType, openDate, initMoney});

        }
        else {
            ExecuteQuery.executeReader("{CALL Savbase_CreateNewAccountForNewCustomer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"
                    , new Object[]{name, CMND, address, phone, email, sex.equals("Nam") ? 1 : 0, birthDate, savingType, openDate, initMoney});
        }

    }
}
