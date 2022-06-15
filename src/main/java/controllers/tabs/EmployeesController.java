package controllers.tabs;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import command.GetEmployeesCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.Account;
import navigation.ScenePaths;
import utils.AppDialog;
import utils.SnackBarUtils;
import utils.Utils;


public class EmployeesController {

    @FXML
    private StackPane root;

    @FXML
    private TableView<Account> tbEmployee;

    @FXML
    private TableColumn<Account, String> nameColumn = new TableColumn<>("Họ và tên");

    @FXML
    private TableColumn<Account, String> positionColumn = new TableColumn<>("Chức vụ");

    @FXML
    private TableColumn<Account, String> nationalIdColumn = new TableColumn<>("CMND/CCCD");

    @FXML
    private TableColumn<Account, String> genderColumn = new TableColumn<>("Giới tính");

    @FXML
    private TableColumn<Account, String> birthDateColumn = new TableColumn<>("Ngày sinh");


    @FXML
    private JFXButton btnAddEmployee;

    @FXML
    private JFXButton btnDelEmployee;

    @FXML
    private JFXButton btnEditEmployee;

    @FXML
    private MFXProgressBar progressBar;

    void setOnFetching(boolean isLoading) {
        progressBar.setVisible(isLoading);
        tbEmployee.setVisible(!isLoading);
    }

    @FXML
    void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        nameColumn.setPrefWidth(200);
        positionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> param) {
                return new SimpleStringProperty(Utils.decodePosition.get(param.getValue().getPosition()));
            }
        });
        positionColumn.setPrefWidth(200);
        nationalIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nationalIdColumn.setPrefWidth(200);
        genderColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> param) {
                return new SimpleStringProperty(Utils.decodeGender.get(param.getValue().getSex()));
            }
        });
        genderColumn.setPrefWidth(180);
        birthDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Account, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Account, String> param) {
                return new SimpleStringProperty(Utils.dateFormatter.format(param.getValue().getBirthdate()));
            }
        });
        birthDateColumn.setPrefWidth(200);
        tbEmployee.getColumns().clear();
        tbEmployee.getColumns().addAll(nameColumn, positionColumn, nationalIdColumn, genderColumn, birthDateColumn);
        tbEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
            @Override
            public void changed(ObservableValue<? extends Account> observableValue, Account account, Account newValue) {
                if (newValue != null) {
                    AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.VIEW_EMPLOYEE_INFO, "Thông tin nhân viên", newValue);
                    try {
                        dialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        loadData();
    }

    void loadData() {
        setOnFetching(true);
        tbEmployee.getItems().clear();
        GetEmployeesCommand command = new GetEmployeesCommand();
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object param) {
                setOnFetching(false);
                tbEmployee.setItems((ObservableList<Account>) command.getResult());
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
    void onDel(ActionEvent event) {

    }

    @FXML
    void onOpenAdd(ActionEvent event) {
        AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.ADD_EMPLOYEE, "Thêm nhân viên", null);
        try {
            String result = dialog.showAndWait(false);
            if (result.equals("success")) {
                loadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onOpenEdit(ActionEvent event) {
        if (tbEmployee.getSelectionModel().getSelectedItem() == null) return;
        AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.EDIT_EMPLOYEE, "Sửa thông tin nhân viên", tbEmployee.getSelectionModel().getSelectedItem());
        try {
            String result = dialog.showAndWait(false);
            if (result.equals("success")) {
                loadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
