package controllers;

import com.jfoenix.controls.*;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.skins.JFXProgressBarSkin;
import command.LoginCommand;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.util.Callback;

public class LoginController {

    LoginCommand loginCommand;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField txtAccount;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXCheckBox ckbRemember;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private MFXProgressSpinner progressBar;

    @FXML
    void initialize() {
    }

    void setOnLogin(boolean isLogining) {
        progressBar.setVisible(isLogining);
        btnLogin.setVisible(!isLogining);
        txtAccount.setDisable(isLogining);
        txtPassword.setDisable(isLogining);
    }

    public void onLogin() {

        loginCommand = new LoginCommand(txtAccount.getText(), txtPassword.getText());
        setOnLogin(true);
        loginCommand.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                setOnLogin(false);
                Alert alert;
                if (loginCommand.getResult() == null) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Tài khoản hoặc mật khẩu không đúng");
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Đăng nhập thành công");
                }
                alert.showAndWait();
                return null;
            }
        });
        loginCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                setOnLogin(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Đã có lỗi xảy ra!");
                alert.setContentText(loginCommand.getException().getMessage());
                alert.showAndWait();
                return null;
            }
        });
        loginCommand.execute();
    }


}