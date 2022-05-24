package controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.SavingAccount;
import navigation.ScenePaths;
import utils.AppDialog;

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

    public void initialize() {
        System.out.println("cc intialize");
    }

    public ViewAccountController() {
        System.out.println("cc constructor");
    }

    public void onAction(ActionEvent actionEvent) throws Exception {
        System.out.println("cc");
        AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.VIEW_ACCOUNT, "Thông tin nhân viên", "Nút mtk");
        String result = dialog.showAndWait(false);
    }

    @Override
    public void onSetParam() {
        SavingAccount savingAccount = (SavingAccount) getParam();
        txtCMND.setText(savingAccount.getNationalId());
        txtDiaChi.setText(savingAccount.getAddress());
        txtEmail.setText(savingAccount.getEmail());
        txtHoTen.setText(savingAccount.getName());
        txtNgayMo.setText(savingAccount.getOpenDate().toString());
        txtSoDu.setText(savingAccount.getSurplus() + "");
        lblGioiTinh.setText(savingAccount.getSex());
        lblLoaiSo.setText(savingAccount.getSavingAccountType());
        lblSDT.setText(savingAccount.getPhoneNumber());
        lblSTK.setText(savingAccount.getId() + "");

    }
}
