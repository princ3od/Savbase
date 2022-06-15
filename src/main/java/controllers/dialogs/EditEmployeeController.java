package controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import command.AddEmployeeCommand;
import command.EditEmployeeCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.Account;
import utils.SnackBarUtils;
import utils.Utils;

import java.sql.Date;

public class EditEmployeeController extends BaseDialogController {

    @FXML
    private StackPane root;

    @FXML
    private JFXButton btnSave;

    @FXML
    private ComboBox<String> cboPosition;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    void onEdit(ActionEvent event) {
        try {
            if (txtName.getText().isEmpty() || txtPhone.getText().isEmpty()
                    || txtAddress.getText().isEmpty() || txtEmail.getText().isEmpty()
                    || cboPosition.getSelectionModel().getSelectedItem() == null) {
                SnackBarUtils.getInstance().show(root, "Vui lòng nhập đầy đủ thông tin");
                return;
            }
            Account account = (Account) getParam();
            int staffID = account.getStaffID();
            int position = cboPosition.getValue().equals("Quản lý") ? 1 : cboPosition.getValue().equals("Nhân viên") ? 2 : 3;
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();

            EditEmployeeCommand command = new EditEmployeeCommand(staffID, position, name, phone, address, email);
            command.setOnSucceed(new Callback() {
                @Override
                public Object call(Object o) {
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "Sửa thành công");
                    setResult("success");
                    onClose();
                    return null;
                }
            });
            command.setOnFail(new Callback() {
                @Override
                public Object call(Object o) {
                    if (command.getException().getMessage().equals("The statement did not return a result set.")) {
                        SnackBarUtils.getInstance().show(Utils.getRoot(), "Sửa thành công");
                        setResult("success");
                        onClose();
                    }
                    else {
                        SnackBarUtils.getInstance().show(root, "Lỗi: " + command.getException().getMessage());
                        setResult("fail");
                        onClose();
                    }
                    return null;
                }
            });
            command.execute();
        }
        catch (Exception e){
            System.out.println("============================");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onSetParam() {
        ObservableList<String> positions = FXCollections.observableArrayList("Quản lý", "Nhân viên", "Admin");
        cboPosition.setItems(positions);

        Account account = (Account) getParam();
        txtAddress.setText(account.getAddress());
        txtEmail.setText(account.getEmail());
        txtName.setText(account.getStaffName());
        txtPhone.setText(account.getPhoneNum());
        cboPosition.getSelectionModel().select(account.getPosition() - 1);


    }
}
