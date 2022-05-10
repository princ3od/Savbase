package controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import navigation.ScenePaths;
import utils.AppDialog;

public class ViewAccountController extends BaseDialogController {

    @FXML
    JFXButton btn;

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
        System.out.println("cc pass data");
        btn.setText((String) getParam());

    }
}
