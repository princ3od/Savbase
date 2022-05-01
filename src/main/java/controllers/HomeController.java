package controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.util.Duration;
import navigation.Navigation;
import stores.AppStore;
import utils.SnackBarUtils;

public class HomeController {
    @FXML
    private StackPane root;

    @FXML
    void initialize() {
        SnackBarUtils.getInstance().show(root, "Chào mừng " + AppStore.getCurrentAccount().getStaffName() + " quay trở lại!");
    }


    public void onShowSnackbar(ActionEvent actionEvent) {
        JFXDialogLayout layout = new JFXDialogLayout();
//        layout.setHeading(new Label("Header"));
        layout.setBody(new Label("Lorem ipsum"));

        JFXDialog dialog = new JFXDialog(root, layout,
                JFXDialog.DialogTransition.CENTER);
        dialog.show();
        JFXAlert alert = new JFXAlert(Navigation.getInstance().getMainStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        layout = new JFXDialogLayout();
//        layout.setHeading(new Label("Modal Dialog using JFXAlert"));
        layout.setBody(new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
                + " sed do eiusmod tempor incididunt ut labore et dolore magna"
                + " aliqua. Utenim ad minim veniam, quis nostrud exercitation"
                + " ullamco laboris nisi ut aliquip ex ea commodo consequat."));
        JFXButton closeButton = new JFXButton("ACCEPT");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);
//        alert.show();
    }
}
