package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import navigation.Navigable;
import navigation.ScenePaths;

import java.io.IOException;

public class SettingTabController extends Navigable {
    @FXML
    private JFXButton btnSavingTypes;

    @FXML
    private JFXButton btnOtherParams;

    @FXML
    private JFXButton btnEmployees;

    @FXML
    private BorderPane pnSettingTabContent;

    @Override
    protected void push(String tabName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(tabName));
        try {
            System.out.println("[NAVIGATION] Change to tab " + tabName);
            pnSettingTabContent.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        push(ScenePaths.TabPagePaths.SettingTab.SAVING_TYPES);
        btnSavingTypes.getStyleClass().setAll("horizontal-tab-selected");
    }

    public void onChangeTab(ActionEvent actionEvent) {
        JFXButton source = (JFXButton) actionEvent.getSource();
        if (source == btnSavingTypes) {
            push(ScenePaths.TabPagePaths.SettingTab.SAVING_TYPES);
        } else if (source == btnOtherParams) {
            push(ScenePaths.TabPagePaths.SettingTab.OTHER_PARAMS);
        } else if (source == btnEmployees) {
            push(ScenePaths.TabPagePaths.SettingTab.EMPLOYEES);
        }
        setSelectedTabButton(source);
    }

    void setSelectedTabButton(JFXButton btn) {
        btnSavingTypes.getStyleClass().setAll("horizontal-tab");
        btnOtherParams.getStyleClass().setAll("horizontal-tab");
        btnEmployees.getStyleClass().setAll("horizontal-tab");
        btn.getStyleClass().setAll("horizontal-tab-selected");
    }
}
