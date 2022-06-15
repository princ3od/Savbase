package controllers.tabs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import command.EditOtherParameterCommand;
import command.GetOtherParameterCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.OtherParameter;
import utils.SnackBarUtils;
import utils.Utils;

public class OtherParameterController {

    private OtherParameter otherParameter;
    @FXML
    private StackPane root;

    @FXML
    private JFXTextField txtMinDeposit;

    @FXML
    private JFXTextField txtMinInit;

    @FXML
    private JFXComboBox<String> cboControl;

    @FXML
    void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("Có", "Không");
        cboControl.setItems(options);
        loadData();
    }

    void loadData() {
        GetOtherParameterCommand command = new GetOtherParameterCommand();
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object o) {
                otherParameter = (OtherParameter)command.getResult();
                txtMinInit.setText(otherParameter.getMinInitDeposit() + "");
                txtMinDeposit.setText(otherParameter.getMinDepositAmount() + "");
                cboControl.getSelectionModel().select(otherParameter.isControlClosingSaving() ? 0 : 1);
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object o) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();
    }

    @FXML
    void onSaveAmount(ActionEvent event) {
        if (txtMinDeposit.getText().isEmpty()) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "Không được để trống ô này!");
            return;
        }
        double minDeposit;
        try {
            minDeposit = Double.parseDouble(txtMinDeposit.getText());
        }
        catch (Exception ex) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "Vui lòng nhập đúng định dạng!");
            return;
        }
        callEdit(minDeposit, otherParameter.getMinInitDeposit(), otherParameter.isControlClosingSaving());
    }

    @FXML
    void onSaveControl(ActionEvent event) {
        callEdit(otherParameter.getMinDepositAmount(), otherParameter.getMinInitDeposit(), cboControl.getValue().equals("Có"));
    }

    @FXML
    void onSaveInit(ActionEvent event) {
        if (txtMinInit.getText().isEmpty()) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "Không được để trống ô này!");
            return;
        }
        double minInit;
        try {
            minInit = Double.parseDouble(txtMinInit.getText());
        }
        catch (Exception ex) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "Vui lòng nhập đúng định dạng!");
            return;
        }
        callEdit(otherParameter.getMinDepositAmount(), minInit, otherParameter.isControlClosingSaving());

    }

    public void callEdit(double minDeposit, double minInit, boolean control) {
        EditOtherParameterCommand command = new EditOtherParameterCommand(minDeposit, minInit, control);
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object o) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "Sửa thành công");
                otherParameter.setMinInitDeposit(minDeposit);
                otherParameter.setMinDepositAmount(minDeposit);
                otherParameter.setControlClosingSaving(control);
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object o) {
                if (command.getException().getMessage().equals("The statement did not return a result set.")) {
                    SnackBarUtils.getInstance().show(root, "Sửa thành công");
                }
                else
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();
    }

}
