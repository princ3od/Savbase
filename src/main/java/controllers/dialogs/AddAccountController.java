package controllers.dialogs;

import com.jfoenix.controls.*;
import command.AddSavingAccountCommand;
import command.GetSavingTypeCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import utils.SnackBarUtils;

import java.sql.Date;

public class AddAccountController extends BaseDialogController {

    @FXML
    private StackPane root;

    @FXML
    private JFXRadioButton radCu;

    @FXML
    private JFXRadioButton radMoi;

    @FXML
    private JFXTextField txtCMND;

    @FXML
    private JFXTextField txtHoTen;

    @FXML
    private JFXDatePicker dpNgaySinh;

    @FXML
    private ComboBox<String> cboGioiTinh;

    @FXML
    private JFXTextField txtDiaChi;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSDT;

    @FXML
    private JFXTextField txtSoTien;

    @FXML
    private JFXDatePicker dpNgayMo;

    @FXML
    private ComboBox<String> cboLoaiSo;

    @FXML
    private JFXButton btnTaoSo;

    @FXML
    private JFXButton btnDong;

    private ToggleGroup group;

    public void initialize() {
        System.out.println("cc intialize");
    }


    @Override
    public void onSetParam() {
        group = new ToggleGroup();
        ObservableList<String> sexs = FXCollections.observableArrayList("Nam", "Nữ");
        cboGioiTinh.setItems(sexs);
        cboGioiTinh.getSelectionModel().select(0);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                // Có lựa chọn
                if (group.getSelectedToggle() != null) {
                    JFXRadioButton button = (JFXRadioButton) group.getSelectedToggle();
                   txtHoTen.setText("");
                   txtDiaChi.setText("");
                   txtEmail.setText("");
                   txtSDT.setText("");
                   dpNgaySinh.setValue(null);
                   txtCMND.setText("");
                   cboGioiTinh.getSelectionModel().clearSelection();
                    if (button.getText().equals("Cho khách hàng cũ")) {
                        txtHoTen.setDisable(true);
                        txtDiaChi.setDisable(true);
                        txtEmail.setDisable(true);
                        txtSDT.setDisable(true);
                        dpNgaySinh.setDisable(true);
                        cboGioiTinh.setDisable(true);
                    }
                    else {
                        txtHoTen.setDisable(false);
                        txtDiaChi.setDisable(false);
                        txtEmail.setDisable(false);
                        txtSDT.setDisable(false);
                        dpNgaySinh.setDisable(false);
                        cboGioiTinh.setDisable(false);
                    }
                }
            }
        });

        radCu.setToggleGroup(group);
        radMoi.setToggleGroup(group);
        radMoi.setSelected(true);

        GetSavingTypeCommand getSavingTypeCommand = new GetSavingTypeCommand();
        getSavingTypeCommand.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                cboLoaiSo.setItems((ObservableList<String>) getSavingTypeCommand.getResult());
                return null;
            }
        });
        getSavingTypeCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                cboLoaiSo.setItems(FXCollections.observableArrayList());
                return null;
            }
        });
        getSavingTypeCommand.execute();

    }

    public void onAction() {
        if (((JFXRadioButton) group.getSelectedToggle()).getText().equals("Cho khách hàng cũ")) {
            if (txtCMND.getText().isEmpty() || txtSoTien.getText().isEmpty() || dpNgayMo.getValue() == null || cboLoaiSo.getSelectionModel().getSelectedItem() == null) {
                SnackBarUtils.getInstance().show(root, "Vui lòng nhập đầy đủ thông tin");
                return;
            }

        }
        else {
            if (txtCMND.getText().isEmpty() || txtSoTien.getText().isEmpty() || dpNgayMo.getValue() == null || cboLoaiSo.getSelectionModel().getSelectedItem() == null
            || txtDiaChi.getText().isEmpty() || txtHoTen.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSDT.getText().isEmpty()
            ||cboGioiTinh.getSelectionModel().getSelectedItem() == null || dpNgaySinh.getValue() == null) {
                SnackBarUtils.getInstance().show(root, "Vui lòng nhập đầy đủ thông tin");
                return;
            }
        }
        try {
            double __ = Double.parseDouble(txtSoTien.getText());
        }
        catch (Exception e) {
            SnackBarUtils.getInstance().show(root, "Vui lòng nhập đúng định dạng tiền");
            return;
        }
        Date ngaySinh;
        if (((JFXRadioButton) group.getSelectedToggle()).getText().equals("Cho khách hàng cũ")) ngaySinh = null;
        else  ngaySinh =  new Date(dpNgaySinh.getValue().getYear(), dpNgaySinh.getValue().getMonth().getValue(), dpNgaySinh.getValue().getDayOfMonth());
        AddSavingAccountCommand command = new AddSavingAccountCommand(((JFXRadioButton) group.getSelectedToggle()).getText(),
                txtCMND.getText(), cboGioiTinh.getSelectionModel().getSelectedItem(), ngaySinh,
                txtHoTen.getText(), txtDiaChi.getText(), txtEmail.getText(), txtSDT.getText(), cboLoaiSo.getSelectionModel().getSelectedItem(),
                new Date(dpNgayMo.getValue().getYear(), dpNgayMo.getValue().getMonth().getValue(), dpNgayMo.getValue().getDayOfMonth()),
                Double.parseDouble(txtSoTien.getText()));
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                SnackBarUtils.getInstance().show(root, "Thêm thành công");
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                if (command.getException().getMessage().equals("The statement did not return a result set.")) {
                    SnackBarUtils.getInstance().show(root, "Thêm thành công");
                }
                else SnackBarUtils.getInstance().show(root, "Lỗi: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();
    }
}
