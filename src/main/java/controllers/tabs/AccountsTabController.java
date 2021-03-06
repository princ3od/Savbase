package controllers.tabs;

import com.jfoenix.controls.JFXButton;
import command.SavingAccountCommand;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.SavingAccount;
import navigation.ScenePaths;
import utils.AppDialog;
import utils.SnackBarUtils;
import utils.Utils;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class AccountsTabController {
    @FXML
    private StackPane root;
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
    private TableColumn<SavingAccount, String> surplusColumn = new TableColumn<>("Số dư");

    @FXML
    private TableColumn<SavingAccount, String> typeColumn = new TableColumn<>("Loại tài khoản");
    ;

    @FXML
    private TextField txtSearch;

    @FXML
    private MFXProgressBar progressBar;

    void setOnFetching(boolean isLoading) {
        progressBar.setVisible(isLoading);
        tbvSavingAccount.setVisible(!isLoading);
    }

    @FXML
    void initialize() {
        setOnFetching(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(200);
        nationalIdColumn.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
        nationalIdColumn.setPrefWidth(150);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("savingAccountType"));
        typeColumn.setPrefWidth(150);
        surplusColumn.setCellValueFactory(new PropertyValueFactory<>("surplus"));
        surplusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SavingAccount, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<SavingAccount, String> param) {
                return new SimpleStringProperty(Utils.currencyFormat.format(param.getValue().getSurplus()));
            }
        });
        surplusColumn.setPrefWidth(200);
        tbvSavingAccount.getColumns().clear();
        tbvSavingAccount.getColumns().addAll(nameColumn, idColumn, nationalIdColumn, typeColumn, surplusColumn);

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


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tbvSavingAccount.getSelectionModel().clearSelection();
                if (newValue.isEmpty()) {
                    tbvSavingAccount.setItems((ObservableList<SavingAccount>) command.getResult());
                } else {
                    tbvSavingAccount.setItems(((ObservableList<SavingAccount>) command.getResult()).filtered(new Predicate<SavingAccount>() {
                        @Override
                        public boolean test(SavingAccount savingAccount) {
                            return savingAccount.getName().contains(txtSearch.getCharacters())
                                    || savingAccount.getNationalId().contains(txtSearch.getCharacters())
                                    || (savingAccount.getId() + "").contains(txtSearch.getCharacters());
                        }
                    }));

                }
            }
        });
        loadData();
    }

    void loadData() {
        command = new SavingAccountCommand();
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                setOnFetching(false);
                tbvSavingAccount.setItems((ObservableList<SavingAccount>) command.getResult());
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object param) {
                setOnFetching(false);
                SnackBarUtils.getInstance().show(root, "Lỗi: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();
    }

    @FXML
    void onOpenAdd(ActionEvent event) {
        AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.ADD_ACCOUNT, "Tạo sổ", null);
        try {
            dialog.showAndWait(false);
            if (dialog.getResult() == "success") {
                loadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
