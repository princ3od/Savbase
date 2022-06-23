package controllers;

import com.jfoenix.controls.*;
import constants.Constants;
import constants.Strings;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.util.Callback;
import javafx.util.Duration;
import navigation.Navigable;
import navigation.Navigation;
import navigation.ScenePaths;
import stores.AppStore;
import utils.AppDialog;
import utils.SnackBarUtils;
import utils.Utils;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeController extends Navigable {
    @FXML
    private StackPane root;

    @FXML
    private BorderPane content;

    @FXML
    private Pane pnHeader;

    @FXML
    private Text lbUsername;

    @FXML
    private JFXButton btnExpand;

    @FXML
    private JFXButton btnAccounts;

    @FXML
    private JFXButton btnTransactions;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnSetting;

    boolean isExpanded = false;

    @Override
    public void push(String tabName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(tabName));
        try {
            System.out.println("[NAVIGATION] Change to tab " + tabName);
            content.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Rectangle clipRect;

    @FXML
    void initialize() {
        clipRect = new Rectangle(pnHeader.getWidth(), pnHeader.getHeight());
        clipRect.heightProperty().bind(pnHeader.heightProperty());
        clipRect.widthProperty().bind(pnHeader.widthProperty());
        pnHeader.setClip(clipRect);
        pnHeader.setMaxHeight(100);
        SnackBarUtils.getInstance().show(root, "Chào mừng " + AppStore.getCurrentAccount().getStaffName() + " quay trở lại!");
        lbUsername.textProperty().setValue(AppStore.getCurrentAccount().getStaffName());
        btnReport.setVisible(AppStore.getCurrentAccount().isAdmin());
        btnSetting.setVisible(AppStore.getCurrentAccount().isAdmin());
        push(ScenePaths.TabPagePaths.ACCOUNTS);
        btnAccounts.getStyleClass().setAll("tab-selected");
        Utils.setRoot(root);
    }

    public void onExpand(MouseEvent mouseEvent) {
        changeSize(pnHeader, pnHeader.getMaxWidth(), isExpanded ? Constants.COLLAPSED_HOME_HEADER_HEIGHT : Constants.EXPANDED_HOME_HEADER_HEIGHT);
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(Constants.DEFAULT_ANIMATION_TIME), btnExpand);
        rotateTransition.setToAngle(isExpanded ? Constants.NORMAL_ANGLE : Constants.UP_SIDE_DOWN_ANGLE);
        rotateTransition.play();
        isExpanded = !isExpanded;
    }

    public void onLogout(ActionEvent actionEvent) {
        Navigation.getInstance().pushAndRemoveAll(ScenePaths.LOGIN);
        AppStore.setCurrentAccount(null);
    }

    public void onChangeTab(ActionEvent actionEvent) {
        JFXButton source = (JFXButton) actionEvent.getSource();
        if (source == btnAccounts) {
            push(ScenePaths.TabPagePaths.ACCOUNTS);
        } else if (source == btnTransactions) {
            push(ScenePaths.TabPagePaths.TRANSACTIONS);
        } else if (source == btnSetting) {
            push(ScenePaths.TabPagePaths.SETTING);
        } else if (source == btnReport) {
            push(ScenePaths.TabPagePaths.REPORT);
        }
        setSelectedTabButton(source);
    }

    void setSelectedTabButton(JFXButton btn) {
        btnAccounts.getStyleClass().setAll("tab");
        btnTransactions.getStyleClass().setAll("tab");
        btnSetting.getStyleClass().setAll("tab");
        btnReport.getStyleClass().setAll("tab");
        btn.getStyleClass().setAll("tab-selected");
    }

    void changeSize(final Pane pane, double width, double height) {
        Duration cycleDuration = Duration.millis(Constants.DEFAULT_ANIMATION_TIME);
        Timeline timeline = new Timeline(
                new KeyFrame(cycleDuration,
                        new KeyValue(pane.maxWidthProperty(), width, Interpolator.EASE_BOTH)),
                new KeyFrame(cycleDuration,
                        new KeyValue(pane.maxHeightProperty(), height, Interpolator.EASE_BOTH))
        );
        timeline.play();
    }


    public void onViewInfo(ActionEvent actionEvent) throws Exception {
        AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.VIEW_EMPLOYEE_INFO, Strings.VIEW_EMPLOYEE_INFO_DIALOG_TITLE, AppStore.getCurrentAccount());
        dialog.show();
    }
}
