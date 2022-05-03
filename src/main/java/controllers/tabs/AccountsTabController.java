package controllers.tabs;

import command.SavingAccountCommand;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.util.Callback;
import models.SavingAccount;

import java.util.ArrayList;

public class AccountsTabController {
    @FXML
//    private AnchorPane root;
    SavingAccountCommand command;
    @FXML
    private Button btnAdd;

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
    private ProgressBar progressBar;

    void setOnFetching(boolean isLoading) {
        progressBar.setVisible(isLoading);
    }

    @FXML
    void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nationalIdColumn.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("savingAccountType"));
        surplusColumn.setCellValueFactory(new PropertyValueFactory<>("surplus"));
        tbvSavingAccount.getColumns().addAll(nameColumn,idColumn, nationalIdColumn, typeColumn, surplusColumn);
        command = new SavingAccountCommand();
        setOnFetching(true);
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
