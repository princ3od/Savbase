package controllers.dialogs;

import com.jfoenix.controls.*;
import command.AddSavingAccountCommand;
import command.GetOtherParameterCommand;
import command.GetSavingTypeCommand;
import command.SavingAccountCommand;
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
import models.OtherParameter;
import models.SavingAccount;
import utils.SnackBarUtils;
import utils.Utils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.Predicate;

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
    private ComboBox<String> cbbOldAccounts;

    private ToggleGroup group;

    OtherParameter otherParameter;

    ObservableList<SavingAccount> accounts;

    public void initialize() {
        GetOtherParameterCommand otherParameterCommand = new GetOtherParameterCommand();
        otherParameterCommand.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                otherParameter = (OtherParameter) otherParameterCommand.getResult();
                return null;
            }
        });
        otherParameterCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "Đã có lỗi xảy ra khi lấy tham số. Lỗi: " + otherParameterCommand.getException().getMessage());
                return null;
            }
        });
        otherParameterCommand.execute();

        SavingAccountCommand savingAccountCommand = new SavingAccountCommand();
        savingAccountCommand.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                accounts = (ObservableList<SavingAccount>) savingAccountCommand.getResult();
                cbbOldAccounts.setItems(FXCollections.observableArrayList(((ObservableList<SavingAccount>) savingAccountCommand.getResult()).stream().map(x -> {
                    return x.getNationalId() + "";
                }).toList()));
                return null;
            }
        });
        savingAccountCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + savingAccountCommand.getException().getMessage());
                return null;
            }
        });
        savingAccountCommand.execute();
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
                        cbbOldAccounts.setVisible(true);
                        txtCMND.setVisible(false);
                    } else {
                        txtHoTen.setDisable(false);
                        txtDiaChi.setDisable(false);
                        txtEmail.setDisable(false);
                        txtSDT.setDisable(false);
                        dpNgaySinh.setDisable(false);
                        cboGioiTinh.setDisable(false);
                        cbbOldAccounts.setVisible(false);
                        txtCMND.setVisible(true);
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

        cbbOldAccounts.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                SavingAccount _account = accounts.stream().filter(new Predicate<SavingAccount>() {
                    @Override
                    public boolean test(SavingAccount savingAccount) {
                        return String.valueOf(savingAccount.getNationalId()).equals(cbbOldAccounts.getValue());
                    }
                }).toList().get(0);
                txtHoTen.setText(_account.getName());
                txtDiaChi.setText(_account.getAddress());
                txtEmail.setText(_account.getEmail());
                txtSDT.setText(_account.getPhoneNumber());
                dpNgaySinh.setValue(LocalDate.parse("1998-02-27"));
                cboGioiTinh.setValue(_account.getSex());
                txtCMND.setText(_account.getNationalId());
            }
        });
        dpNgayMo.setValue(LocalDate.now());
    }

    public void onAction() {
        if (((JFXRadioButton) group.getSelectedToggle()).getText().equals("Cho khách hàng cũ")) {
            if (txtCMND.getText().isEmpty() || txtSoTien.getText().isEmpty() || dpNgayMo.getValue() == null || cboLoaiSo.getSelectionModel().getSelectedItem() == null) {
                SnackBarUtils.getInstance().show(root, "Vui lòng nhập đầy đủ thông tin");
                return;
            }

        } else {
            if (txtCMND.getText().isEmpty() || txtSoTien.getText().isEmpty() || dpNgayMo.getValue() == null || cboLoaiSo.getSelectionModel().getSelectedItem() == null
                    || txtDiaChi.getText().isEmpty() || txtHoTen.getText().isEmpty() || txtEmail.getText().isEmpty() || txtSDT.getText().isEmpty()
                    || cboGioiTinh.getSelectionModel().getSelectedItem() == null || dpNgaySinh.getValue() == null) {
                SnackBarUtils.getInstance().show(root, "Vui lòng nhập đầy đủ thông tin");
                return;
            }
        }
        double amount;
        try {
            amount = Double.parseDouble(txtSoTien.getText());
            if (amount < otherParameter.getMinInitDeposit()) {
                SnackBarUtils.getInstance().show(root,
                        "Số tiền ban đầu không được nhỏ hơn " + Utils.currencyFormat.format(otherParameter.getMinInitDeposit()));
                return;
            }
        } catch (Exception e) {
            SnackBarUtils.getInstance().show(root, "Vui lòng nhập đúng định dạng tiền");
            return;
        }

        Date ngaySinh;
        if (((JFXRadioButton) group.getSelectedToggle()).getText().equals("Cho khách hàng cũ")) ngaySinh = null;
        else ngaySinh = Utils.localDateToSqlDate(dpNgaySinh.getValue());
        AddSavingAccountCommand command = new AddSavingAccountCommand(((JFXRadioButton) group.getSelectedToggle()).getText(),
                txtCMND.getText(), cboGioiTinh.getSelectionModel().getSelectedItem(), ngaySinh,
                txtHoTen.getText(), txtDiaChi.getText(), txtEmail.getText(), txtSDT.getText(), cboLoaiSo.getSelectionModel().getSelectedItem(),
                Utils.localDateToSqlDate(dpNgayMo.getValue()),
                amount);
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                SnackBarUtils.getInstance().show(root, "Thêm thành công");
                setResult("success");
                onClose();
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                if (command.getException().getMessage().equals("The statement did not return a result set.")) {
                    SnackBarUtils.getInstance().show(root, "Thêm thành công");
                    setResult("success");
                    onClose();
                } else {
                    SnackBarUtils.getInstance().show(root, "Lỗi: " + command.getException().getMessage());
                    setResult("fail");
                }
                return null;
            }
        });
        command.execute();
    }
}
