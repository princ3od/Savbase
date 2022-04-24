package service;

import models.Account;
import models.builder.AccountBuilder;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;

public class LoginService {
    public static Account authenticate(String username, String password) throws Exception {
        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_Login( ?, ?)}", new Object[]{username, password});
        if (!data.next()) return null;
        AccountBuilder accountBuilder = new AccountBuilder().setStaffID(data.getInt("MaNV"))
                .setPosition(data.getInt("MaCV")).setPassword(data.getString("Pass")).setStaffName(data.getString("TenNV"))
                .setID(data.getString("CCCD/CMND")).setPhoneNum(data.getString("DienThoai")).setEmail(data.getString("Email"))
                .setAddress(data.getString("DiaChi")).setSex(data.getString("GioiTinh")).setBirthdate(data.getDate("NgaySinh"));
        return accountBuilder.getResult();
    }
}
