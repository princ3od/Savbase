package controllers.tabs;

import com.jfoenix.controls.*;
import command.AddTransactionCommand;
import command.GetOtherParameterCommand;
import command.SavingAccountCommand;
import command.WithdrawInterestCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.util.Callback;
import models.OtherParameter;
import models.SavingAccount;
import navigation.ScenePaths;
import stores.AppStore;
import utils.SnackBarUtils;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;

public class TransactionsTabController {

    @FXML
    private JFXCheckBox chbPrint;

    @FXML
    private JFXCheckBox chbWithdraw;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXDatePicker dpTransaction;

    @FXML
    private JFXButton btnTabDeposit;

    @FXML
    private JFXButton btnTabWithdraw;

    @FXML
    private JFXButton btnWithdraw;

    @FXML
    private JFXButton btnDeposit;

    @FXML
    private JFXComboBox<String> cboID;

    OtherParameter otherParameter;

    ObservableList<SavingAccount> accounts;

    @FXML
    void initialize() {
        setSelectedTabButton(btnTabDeposit);
        chbWithdraw.setVisible(false);
        btnDeposit.setVisible(true);
        btnWithdraw.setVisible(false);
        SavingAccountCommand savingAccountCommand = new SavingAccountCommand();
        savingAccountCommand.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                cboID.setItems(FXCollections.observableArrayList(((ObservableList<SavingAccount>) savingAccountCommand.getResult()).stream().map(x -> {
                    return x.getId() + "";
                }).toList()));
                accounts = (ObservableList<SavingAccount>) savingAccountCommand.getResult();
                return null;
            }
        });
        savingAccountCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "L???i: " + savingAccountCommand.getException().getMessage());
                return null;
            }
        });
        savingAccountCommand.execute();
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
                SnackBarUtils.getInstance().show(Utils.getRoot(), "???? c?? l???i x???y ra khi l???y tham s???. L???i: " + otherParameterCommand.getException().getMessage());
                return null;
            }
        });
        otherParameterCommand.execute();
        cboID.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                SavingAccount _account = accounts.stream().filter(new Predicate<SavingAccount>() {
                    @Override
                    public boolean test(SavingAccount savingAccount) {
                        return String.valueOf(savingAccount.getId()).equals(cboID.getValue());
                    }
                }).toList().get(0);
                txtName.setText(_account.getName());
            }
        });
        dpTransaction.setValue(LocalDate.now());
    }

    void setSelectedTabButton(JFXButton btn) {
        btnTabDeposit.setStyle("-fx-background-color: #32A05F; -fx-text-fill: white; -fx-background-radius: 30 0 0 30;");
        btnTabWithdraw.setStyle("-fx-background-color: #32A05F; -fx-text-fill: white; -fx-background-radius: 0 30 30 0;");
        if (btn == btnTabDeposit) {
            btnTabWithdraw.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
        } else if (btn == btnTabWithdraw) {
            btnTabDeposit.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
        }
    }

    @FXML
    void onChangeTab(ActionEvent event) {
        JFXButton source = (JFXButton) event.getSource();
        if (source == btnTabDeposit) {
            chbWithdraw.setVisible(false);
            btnDeposit.setVisible(true);
            btnWithdraw.setVisible(false);
        } else if (source == btnTabWithdraw) {
            chbWithdraw.setVisible(true);
            btnDeposit.setVisible(false);
            btnWithdraw.setVisible(true);
        }
        setSelectedTabButton(source);
    }

    @FXML
    void onDeposit(ActionEvent event) {
        if (inputEmpty()) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "Vui l??ng nh???p ?????y ????? th??ng tin");
            return;
        }
        int accountId;
        double amount;
        try {
            accountId = Integer.parseInt(cboID.getValue());
        } catch (Exception e) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "S??? t??i kho???n ph???i l?? s??? nguy??n");
            return;
        }
        try {
            amount = Double.parseDouble(txtAmount.getText());
        } catch (Exception e) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "S??? ti???n ph???i l?? s??? th???c");
            return;
        }
        if (amount <= otherParameter.getMinDepositAmount()) {
            SnackBarUtils.getInstance().show(Utils.getRoot(),
                    "S??? ti???n g???i ph???i l???n h??n" + Utils.currencyFormat.format(otherParameter.getMinDepositAmount()));
            return;
        }
        AddTransactionCommand command = new AddTransactionCommand(1, accountId,
                AppStore.getCurrentAccount().getStaffID(), Utils.localDateToSqlDate(dpTransaction.getValue()), amount);
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "G???i ti???n th??nh c??ng");
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                if (command.getException().getMessage().equals("The statement did not return a result set.")) {
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "G???i ti???n th??nh c??ng");
                } else
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "G???i ti???n th???t b???i, l???i: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();
    }

    @FXML
    void onWithdraw(ActionEvent event) {
        if (inputEmpty()) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "Vui l??ng nh???p ?????y ????? th??ng tin");
            return;
        }
        int accountId;
        double amount;
        try {
            accountId = Integer.parseInt(cboID.getValue());
        } catch (Exception e) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "S??? t??i kho???n ph???i l?? s??? nguy??n");
            return;
        }
        try {
            amount = Double.parseDouble(txtAmount.getText());
        } catch (Exception e) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "S??? ti???n ph???i l?? s??? th???c");
            return;
        }
        AddTransactionCommand command = new AddTransactionCommand(2, accountId,
                AppStore.getCurrentAccount().getStaffID(), Utils.localDateToSqlDate(dpTransaction.getValue()), amount);
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "R??t ti???n th??nh c??ng");
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                if (command.getException().getMessage().equals("The statement did not return a result set.")) {
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "R??t ti???n th??nh c??ng");
                } else
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "R??t ti???n th???t b???i, l???i: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();
        if (chbWithdraw.isSelected()) {
            WithdrawInterestCommand withdrawInterestCommand = new WithdrawInterestCommand(accountId);
            withdrawInterestCommand.setOnSucceed(new Callback() {
                @Override
                public Object call(Object param) {
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "R??t ti???n l??i th??nh c??ng");
                    return null;
                }
            });
            withdrawInterestCommand.setOnFail(new Callback() {
                @Override
                public Object call(Object param) {
                    if (withdrawInterestCommand.getException().getMessage().equals("The statement did not return a result set.")) {
                        SnackBarUtils.getInstance().show(Utils.getRoot(), "R??t ti???n l??i th??nh c??ng");
                    } else
                        SnackBarUtils.getInstance().show(Utils.getRoot(), "R??t ti???n l??i th???t b???i, l???i: " + withdrawInterestCommand.getException().getMessage());
                    return null;
                }
            });
            withdrawInterestCommand.execute();
        }
    }

    boolean inputEmpty() {
        return cboID.getValue() == null || txtAmount.getText().isEmpty()
                || txtName.getText().isEmpty() || dpTransaction.getValue() == null;
    }

}
