package controllers.tabs;

import com.jfoenix.controls.*;
import command.SavingAccountCommand;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.SavingAccount;
import navigation.ScenePaths;
import utils.AppDialog;
import utils.SnackBarUtils;

public class TransactionsTabController {
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField txtHoTen;

    @FXML
    private JFXTextField txtSoTienGiaoDich;

    @FXML
    private JFXDatePicker dpNgayGiaoDich;

    @FXML
    private ComboBox<SavingAccount> cboSTK;

    @FXML
    private JFXButton btnTienClick;

    @FXML
    private CheckBox ckIn;

    @FXML
            private CheckBox ckRutTien;

    @FXML
            private RadioButton rdbGui;
    @FXML
            private RadioButton rdbRut;
    SavingAccountCommand command;
    private ToggleGroup group;

    public void initialize() {
        rdbGui.setSelected(true);
        rdbRut.setSelected(false);
        ckRutTien.setVisible(false);

        command = new SavingAccountCommand();
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                System.out.print(command.getResult());
                cboSTK.setItems((ObservableList<SavingAccount>) command.getResult());
                return null;
            }

        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
//                SnackBarUtils.getInstance().show(root, "Lỗi: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();
    }
    public void onClickRadioGuiTien(MouseEvent mouseEvent) throws Exception {
        rdbGui.setSelected(true);
        rdbRut.setSelected(false);
        ckRutTien.setVisible(false);
        btnTienClick.setText("Gửi tiền");
    }
    public void onClickRadioRutTien(MouseEvent mouseEvent) throws Exception {
        rdbGui.setSelected(false);
        rdbRut.setSelected(true);
        ckRutTien.setVisible(true);
        btnTienClick.setText("Rút tiền");
    }


}
