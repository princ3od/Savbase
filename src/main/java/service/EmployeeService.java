package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Account;
import models.SavingAccount;
import models.builder.AccountBuilder;
import models.builder.SavingAccountBuilder;

import javax.sql.rowset.CachedRowSet;
import java.sql.Date;
import java.util.ArrayList;

public class EmployeeService {
    public static ObservableList<Account> getAll() throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_LoadAllStaffs()}", null);
        ArrayList<Account> accounts = new ArrayList<>();

        while (data.next()) {
            AccountBuilder accountBuilder = new AccountBuilder().setStaffID(data.getInt("MaNV"))
                    .setPosition(data.getInt("MaCV")).setStaffName(data.getString("TenNV"))
                    .setID(data.getString("CCCD/CMND")).setPhoneNum(data.getString("DienThoai"))
                    .setEmail(data.getString("Email")).setSex(data.getString("GioiTinh"))
                    .setAddress(data.getString("DiaChi")).setBirthdate(data.getDate("NgaySinh"));
            accounts.add(accountBuilder.getResult());
        }
        ObservableList<Account> result = FXCollections.observableArrayList(accounts);
        return result;

    }

    public static void create(int position, String name, String ID, boolean sex, Date birthDate, String phone,
                              String address, String password, String email) throws Exception {
            ExecuteQuery.executeReader("{CALL Savbase_AddNewStaff(?, ?, ?, ?, ?, ?, ?, ?, ?)}"
                    , new Object[]{position, name, ID, sex, birthDate, phone, address, password, email});
    }
}
