package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import navigation.Navigable;
import navigation.ScenePaths;

import java.io.IOException;

public class ReportTabController extends Navigable {

    @FXML
    private JFXButton btnRevenue;

    @FXML
    private JFXButton btnOpenClose;

    @FXML
    private BorderPane pnReportTabContent;


    @FXML
    void initialize() {
        push(ScenePaths.TabPagePaths.ReportTab.SALES);
        btnRevenue.getStyleClass().setAll("horizontal-tab-selected");
    }

    @Override
    protected void push(String tabName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(tabName));
        try {
            System.out.println("[NAVIGATION] Change to tab " + tabName);
            pnReportTabContent.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onChangeTab(ActionEvent actionEvent) {
        JFXButton source = (JFXButton) actionEvent.getSource();
        if (source == btnRevenue) {
            push(ScenePaths.TabPagePaths.ReportTab.SALES);
        } else if (source == btnOpenClose) {
            push(ScenePaths.TabPagePaths.ReportTab.OPEN_CLOSE_ACCOUNTS);
        }
        setSelectedTabButton(source);
    }

    void setSelectedTabButton(JFXButton btn) {
       btnRevenue.getStyleClass().setAll("horizontal-tab");
        btnOpenClose.getStyleClass().setAll("horizontal-tab");
        btn.getStyleClass().setAll("horizontal-tab-selected");
    }
}
