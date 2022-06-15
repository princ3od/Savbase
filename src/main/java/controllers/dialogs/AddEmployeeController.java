package controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import command.AddEmployeeCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import utils.SnackBarUtils;
import utils.Utils;

import java.sql.Date;


public class AddEmployeeController extends  BaseDialogController {

    @FXML
    private StackPane root;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private ComboBox<String> cboPosition;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private ComboBox<String> cboSex;

    @FXML
    private JFXDatePicker dpBirthdate;

    @FXML
    void onAdd(ActionEvent event) {
        try {
            if (txtName.getText().isEmpty() || txtID.getText().isEmpty() || txtPhone.getText().isEmpty()
                    || txtAddress.getText().isEmpty() || txtPass.getText().isEmpty() || txtEmail.getText().isEmpty()
                    || cboSex.getSelectionModel().getSelectedItem() == null || cboPosition.getSelectionModel().getSelectedItem() == null
                    || dpBirthdate.getValue() == null) {
                SnackBarUtils.getInstance().show(root, "Vui lòng nhập đầy đủ thông tin");
                return;
            }
            int position = cboPosition.getValue().equals("Quản lý") ? 1 : cboPosition.getValue().equals("Nhân viên") ? 2 : 3;
            String name = txtName.getText();
            String ID = txtID.getText();
            boolean sex = cboSex.getValue().equals("Nam") ? true : false;
            Date birthdate = new Date(dpBirthdate.getValue().getYear() - 1900, dpBirthdate.getValue().getMonth().getValue(), dpBirthdate.getValue().getDayOfMonth());

            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String password = txtPass.getText();
            String email = txtEmail.getText();

            AddEmployeeCommand command = new AddEmployeeCommand(position, name, ID, sex,  birthdate, phone, address, password, email);
            command.setOnSucceed(new Callback() {
                @Override
                public Object call(Object o) {
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "Thêm thành công");
                    setResult("success");
                    onClose();
                    return null;
                }
            });
            command.setOnFail(new Callback() {
                @Override
                public Object call(Object o) {
                    if (command.getException().getMessage().equals("The statement did not return a result set.")) {
                        SnackBarUtils.getInstance().show(Utils.getRoot(), "Thêm thành công");
                        setResult("success");
                        onClose();
                    }
                    else SnackBarUtils.getInstance().show(root, "Lỗi: " + command.getException().getMessage());
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
        ObservableList<String> sexs = FXCollections.observableArrayList("Nam", "Nữ");
        cboSex.setItems(sexs);
        cboSex.getSelectionModel().select(0);

        ObservableList<String> positions = FXCollections.observableArrayList("Quản lý", "Nhân viên", "Admin");
        cboPosition.setItems(positions);
        cboPosition.getSelectionModel().select(0);


    }
}
