package controllers.dialogs;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.SavingAccount;
import utils.Utils;

public class ViewAccountController extends BaseDialogController {

    @FXML
    private Label lblGioiTinh;

    @FXML
    private Label lblLoaiSo;

    @FXML
    private Label lblSDT;

    @FXML
    private Label lblSTK;

    @FXML
    private JFXTextField txtCMND;

    @FXML
    private JFXTextField txtDiaChi;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtHoTen;

    @FXML
    private JFXTextField txtNgayMo;

    @FXML
    private JFXTextField txtSoDu;

    @Override
    public void onSetParam() {
        SavingAccount savingAccount = (SavingAccount) getParam();
        txtCMND.setText(savingAccount.getNationalId());
        txtDiaChi.setText(savingAccount.getAddress());
        txtEmail.setText(savingAccount.getEmail());
        txtHoTen.setText(savingAccount.getName());
        txtNgayMo.setText(Utils.dateFormatter.format(savingAccount.getOpenDate()));
        txtSoDu.setText(Utils.currencyFormat.format(savingAccount.getSurplus()));
        lblGioiTinh.setText(savingAccount.getSex());
        lblLoaiSo.setText(savingAccount.getSavingAccountType());
        lblSDT.setText(savingAccount.getPhoneNumber());
        lblSTK.setText(savingAccount.getId() + "");
    }
}
