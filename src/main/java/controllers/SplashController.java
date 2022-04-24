package controllers;

import com.jfoenix.controls.JFXButton;
import command.StartupCommand;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import navigation.ScenePaths;
import javafx.scene.control.Alert;
import javafx.util.Callback;
import navigation.Navigation;
import utils.LocalSettings;

import java.io.IOException;
import java.util.Properties;

public class SplashController {

    @FXML
    MFXProgressSpinner spinner;

    @FXML
    Label lbLoading;

    @FXML
    JFXButton btnRetry;

    @FXML
    public void initialize() {
        btnRetry.setVisible(false);
        StartupCommand startupCommand = new StartupCommand();
        startupCommand.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                Navigation.getInstance().push(ScenePaths.LOGIN, false);
                return null;
            }
        });
        startupCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                btnRetry.setVisible(true);
                lbLoading.setVisible(false);
                spinner.setVisible(false);
                Properties properties = new Properties();
                properties.setProperty("connection_string", "jdbc:sqlserver://localhost\\SQLEXPRESS:52018;instance=sqlexpress;databaseName=Savbase;user=sa;password=123456");
                try {
                    LocalSettings.saveTofile(properties, LocalSettings.CONNECTION_STRING_FILE, "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Đã có lỗi xảy ra!");
                alert.setContentText(startupCommand.getException().getMessage());
                alert.showAndWait();
                return null;
            }
        });
        startupCommand.execute();
    }

    public void onRetry() throws IOException {
        Navigation.getInstance().restart();
    }
}