package controllers.tabs;

import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

public class SettingController {
    @FXML
    private Tab tabTypeSaveMoney;
    @FXML
    private Tab tabOtherInfo;
    @FXML
    private Tab tabEmployee;
    @FXML
    private JFXTabPane tabSettingPanel;
    private  int  _currentTab = 0;

    @FXML
    void initialize(){
        System.out.println("khoi tao");
    }
}
