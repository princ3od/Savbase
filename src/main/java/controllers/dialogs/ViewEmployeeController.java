package controllers.dialogs;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.sql.*;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import models.Account;
import models.SavingAccount;
import utils.Utils;

public class ViewEmployeeController extends BaseDialogController {
    @FXML
    private JFXTextField txtStaffId;

    @FXML
    private JFXTextField txtPosition;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPhoneNum;

    @FXML
    private JFXTextField txtGender;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtStaffName;

    @FXML
    private JFXTextField txtBornDate;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXButton btnClose;

    @Override
    public void onSetParam() {
        Account employeeAccount = (Account) getParam();
        txtStaffId.setText(employeeAccount.getID());
        txtPosition.setText(Utils.decodePosition.get(employeeAccount.getPosition()));
        txtStaffName.setText(employeeAccount.getStaffName());
        txtID.setText(employeeAccount.getID());
        txtGender.setText(Utils.decodeGender.get(employeeAccount.getSex()));
        txtPhoneNum.setText(employeeAccount.getPhoneNum());
        txtEmail.setText(employeeAccount.getEmail());
        txtAddress.setText(employeeAccount.getAddress());
        String readableDate = Utils.dateFormatter.format(employeeAccount.getBirthdate());
        txtBornDate.setText(readableDate);
    }

    public void onClose(MouseEvent mouseEvent) {
        super.onClose();
    }
}




