package controllers;

import com.jfoenix.controls.*;

import java.net.URL;
import java.util.ResourceBundle;

import command.LoginCommand;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.Account;
import navigation.Navigation;
import navigation.ScenePaths;
import stores.AppStore;
import utils.SnackBarUtils;


public class LoginController {

    LoginCommand loginCommand;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane root;

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
        btnLogin.disableProperty().bind(Bindings.or(txtAccount.textProperty().isEmpty(), txtPassword.textProperty().isEmpty()));
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
                if (loginCommand.getResult() == null) {
                    SnackBarUtils.getInstance().show(root, "Tài khoản hoặc mật khẩu không hợp lệ.");
                } else {
                    AppStore.setCurrentAccount((Account) loginCommand.getResult());
                    Navigation.getInstance().pushAndRemoveAll(ScenePaths.HOME);
                }
                return null;
            }
        });
        loginCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                setOnLogin(false);
                SnackBarUtils.getInstance().show(root, "Lỗi: " + loginCommand.getException().getMessage());
                return null;
            }
        });
        loginCommand.execute();
    }

}