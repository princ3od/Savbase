package utils;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import controllers.dialogs.BaseDialogController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import navigation.Navigation;

import java.io.IOException;

//How to use
//AppDialog<String> dialog = new AppDialog(root);
//String result = dialog.showAndWait(ScenePaths.DialogPaths.VIEW_ACCOUNT, "Thông tin nhân viên");
public class AppDialog<T> {

    JFXAlert dialog;
    T result;
    Object param;

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    String dialogPath;
    String headingText;

    boolean overlayClose = true;

    public AppDialog(String dialogPath, String headingText) {
        this.dialogPath = dialogPath;
        this.headingText = headingText;
    }

    public AppDialog(String dialogPath, String headingText, Object param) {
        this.dialogPath = dialogPath;
        this.headingText = headingText;
        this.param = param;
    }

    public AppDialog(String dialogPath, String headingText, boolean overlayClose, Object param) {
        this.dialogPath = dialogPath;
        this.headingText = headingText;
        this.overlayClose = overlayClose;
        this.param = param;
    }

    void initializeDialog() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(dialogPath));
        JFXDialogLayout layout = new JFXDialogLayout();
        try {
            layout.getChildren().add(fxmlLoader.load());
            BaseDialogController controller = fxmlLoader.getController();
            if (controller != null) {
                controller.setDialog(this);
                controller.setParam(this.param);
            } else throw new Exception("Please attach a controller to dialog.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog = new JFXAlert(Navigation.getInstance().getMainStage());
        dialog.setOverlayClose(true);
        dialog.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        dialog.setContent(layout);
        dialog.initModality(Modality.NONE);
        JFXButton closeButton = new JFXButton();
        closeButton.setStyle("-fx-background-radius: 8;");
        closeButton.setRipplerFill(Color.valueOf("#e0e0e0"));
        closeButton.setGraphic(new ImageView(new Image("/images/close.png")));
        closeButton.setMaxSize(24, 24);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        BorderPane header = new BorderPane();
        if (headingText != null) {
            Label lbHeader = new Label(headingText);
            lbHeader.setStyle("-fx-font-size: 24");
            header.setCenter(lbHeader);
        }
        header.setPadding(new Insets(0, 0, -32, 0));
        header.setRight(closeButton);
        layout.setHeading(header);
    }

    public void show() throws Exception {
        initializeDialog();
        dialog.show();
    }

    public void show(boolean overlayClose) throws Exception {
        initializeDialog();
        dialog.setOverlayClose(overlayClose);
        dialog.show();
    }

    public T showAndWait() throws Exception {
        initializeDialog();
        dialog.showAndWait();
        return result;
    }

    public T showAndWait(boolean overlayClose) throws Exception {
        initializeDialog();
        dialog.setOverlayClose(overlayClose);
        dialog.showAndWait();
        return result;
    }

    public void close() {
        if (dialog != null) {
            dialog.close();
        }
    }
}
