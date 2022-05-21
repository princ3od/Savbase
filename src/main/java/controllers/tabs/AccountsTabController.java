package controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import command.SavingAccountCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.util.Callback;
import models.SavingAccount;
import navigation.ScenePaths;
import utils.AppDialog;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AccountsTabController {
    @FXML
//    private AnchorPane root;
    SavingAccountCommand command;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private TableView<SavingAccount> tbvSavingAccount = new TableView<SavingAccount>();

    @FXML
    private TableColumn<SavingAccount, Integer> idColumn = new TableColumn<>("Số tài khoản");

    @FXML
    private TableColumn<SavingAccount, String> nameColumn = new TableColumn<>("Họ và tên");

    @FXML
    private TableColumn<SavingAccount, String> nationalIdColumn = new TableColumn<>("CMND/CCCD");

    @FXML
    private TableColumn<SavingAccount, Double> surplusColumn = new TableColumn<>("Số dư");

    @FXML
    private TableColumn<SavingAccount, String> typeColumn = new TableColumn<>("Loại tài khoản");;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXProgressBar progressBar;

    void setOnFetching(boolean isLoading) {
        progressBar.setVisible(isLoading);
    }

    @FXML
    void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(200);
        nationalIdColumn.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        nationalIdColumn.setPrefWidth(150);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("savingAccountType"));
        typeColumn.setPrefWidth(150);
        surplusColumn.setCellValueFactory(new PropertyValueFactory<>("surplus"));
        surplusColumn.setPrefWidth(200);
        tbvSavingAccount.getColumns().clear();
        tbvSavingAccount.getColumns().addAll(nameColumn,idColumn, nationalIdColumn, typeColumn, surplusColumn);

        tbvSavingAccount.getColumns().forEach(new Consumer<TableColumn<SavingAccount, ?>>() {
            @Override
            public void accept(TableColumn<SavingAccount, ?> savingAccountTableColumn) {
                savingAccountTableColumn.setResizable(false);
                savingAccountTableColumn.setSortable(false);
            }
        });
        tbvSavingAccount.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SavingAccount>() {
            @Override
            public void changed(ObservableValue<? extends SavingAccount> observable, SavingAccount oldValue, SavingAccount newValue) {
                if (newValue != null) {
                    AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.VIEW_ACCOUNT, "Thông tin sổ tiết kiệm", newValue);
                    try {
                        dialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        command = new SavingAccountCommand();
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                setOnFetching(false);
                System.out.print(command.getResult());
                tbvSavingAccount.setItems((ObservableList<SavingAccount>) command.getResult());
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                setOnFetching(false);
//                SnackBarUtils.getInstance().show(root, "Lỗi: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();

    }

    @FXML
    void onOpenAdd(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.showAndWait();
    }

    @FXML
    void onSearchChange(InputMethodEvent event) {

    }


}
