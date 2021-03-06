package controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import command.DelSavingTypeCommand;
import command.GetAllSavingsTypeCommand;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import models.SavingType;
import javafx.util.Callback;
import navigation.ScenePaths;
import utils.AppDialog;
import utils.SnackBarUtils;

import java.util.ArrayList;

public class SavingTypeController<T> {

    @FXML
    private StackPane root;

    @FXML
    private TableView<SavingType> tbSavingType = new TableView<SavingType>();

    @FXML
    private TableColumn<SavingType, String> savingType = new TableColumn<>("Loại sổ");
    @FXML
    private TableColumn<SavingType, Integer> tennor = new TableColumn<>("Kỳ hạn");
    @FXML
    private TableColumn<SavingType, Double> interestRate = new TableColumn<>("Lãi xuất");
    @FXML
    private TableColumn<SavingType, Integer> minSendingDate = new TableColumn<>("Số ngày gửi tối thiểu");
    @FXML
    private TableColumn<SavingType, String> rule = new TableColumn<>("Quy định rút tiền");

    @FXML
    private JFXButton btnAddSavingType;

    @FXML
    private JFXProgressBar savingTypeProgressBar;
    @FXML
    private JFXButton btnDelSavingType;

    private GetAllSavingsTypeCommand getAllSavingsTypeCommand;


    @FXML
    void initialize() {
        savingType.setCellValueFactory(new PropertyValueFactory<>("nameSavingType"));
        savingType.setPrefWidth(200);
        tennor.setCellValueFactory(new PropertyValueFactory<>("tenor"));
        tennor.setPrefWidth(200);
        interestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
        interestRate.setPrefWidth(200);
        minSendingDate.setCellValueFactory(new PropertyValueFactory<>("minSendingDate"));
        minSendingDate.setPrefWidth(180);
        rule.setCellValueFactory(new PropertyValueFactory<>("rule"));
        rule.setPrefWidth(200);
        tbSavingType.getColumns().clear();
        tbSavingType.getColumns().addAll(savingType, tennor, interestRate, minSendingDate, rule);
        loadData();
    }

    private void showSavingTypeResult(ArrayList<SavingType> savingTypes) {
        for (var type : savingTypes) {
            tbSavingType.getItems().add(type);
        }
    }


    public void onAddSavingType(MouseEvent mouseEvent) throws Exception {
        AppDialog<String> dialog = new AppDialog(ScenePaths.DialogPaths.ADD_NEW_SAVING_TYPE, null, tbSavingType);
        T rawResult = (T) dialog.showAndWait();
        boolean result = (Boolean) rawResult;
        if (result) {
            loadData();
        }
    }

    private void loadData() {
        tbSavingType.getItems().clear();
        getAllSavingsTypeCommand = new GetAllSavingsTypeCommand();
        getAllSavingsTypeCommand.setOnSucceed(
                new Callback() {
                    @Override
                    public Object call(Object param) {
                        if (getAllSavingsTypeCommand.getResult() != null) {
                            showSavingTypeResult((ArrayList<SavingType>) getAllSavingsTypeCommand.getResult());
                        }
                        return null;
                    }
                }
        );
        getAllSavingsTypeCommand.execute();
    }


    @FXML
    void onDelSavingType(MouseEvent event) {
        SavingType selectedSavingType = tbSavingType.getSelectionModel().getSelectedItem();
        System.out.println(selectedSavingType);
        DelSavingTypeCommand delSavingTypeCommand = new DelSavingTypeCommand(selectedSavingType);
        delSavingTypeCommand.setOnSucceed(
                new Callback() {
                    @Override
                    public Object call(Object param) {
                        onDelSavingSuccess();
                        return null;
                    }
                }
        );
        delSavingTypeCommand.setOnFail(
                new Callback() {
                    @Override
                    public Object call(Object param) {
                        onDelSavingFail((Exception) delSavingTypeCommand.getException());
                        return null;
                    }
                }
        );
        delSavingTypeCommand.execute();
    }

    void onDelSavingSuccess() {
        SnackBarUtils.getInstance().show(root, "Xoá thành công");
    }

    void onDelSavingFail(Exception ex) {
        SnackBarUtils.getInstance().show(root, "Xoá không thành công \n" + ex.getMessage());
    }
}
