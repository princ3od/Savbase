package service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Customer;
import models.builder.CustomerBuilder;
import models.builder.SavingAccountBuilder;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;
public class CustomerService {
    public static ObservableList<Customer> get(int id) throws Exception {

        CachedRowSet data = ExecuteQuery.executeReader("{CALL Savbase_GetCustomerIf(?)}", new Object[]{id});
        ArrayList<Customer> customers = new ArrayList<>();

        while (data.next()) {
            CustomerBuilder customerBuilder = new CustomerBuilder().setId(data.getInt("MaKH"))
                    .setName(data.getString("TenKH")).setEmail(data.getString("Email"))
                    .setAddress(data.getString("Address")).setSex(data.getBoolean("GioiTinh") ? "Nam" : "Nữ");

            customers.add(customerBuilder.getResult());

        }
        ObservableList<Customer> result = FXCollections.observableArrayList(customers);

        return result;

    }
}
