package controllers.dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import command.AddNewSavingTypeCommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import models.SavingType;
import utils.SnackBarUtils;
import utils.Utils;

import java.util.Date;

public class AddSavingTypeController  extends  BaseDialogController{
    private AddNewSavingTypeCommand addNewSavingTypeCommand;
    @FXML
    private JFXTextField txtSavingName;

    @FXML
    private JFXTextField txtTenor;

    @FXML
    private JFXTextField txtMinSending;

    @FXML
    private JFXComboBox<String> cbRule;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXTextField txtInterestRate;

    private TableView<SavingType> savingTypeTableView;

    @Override
    public void onSetParam() {
        savingTypeTableView = (TableView<SavingType>)getParam();
    }
    @FXML
    void initialize() {

        ObservableList<String> listLeagues = FXCollections.observableArrayList(
                "<=", "=");
        cbRule.getItems().clear();
        cbRule.setItems(listLeagues);
        cbRule.setVisibleRowCount(2);
        cbRule.setValue("<=");
    }

    public void onAddNewSavingType(MouseEvent mouseEvent) {
        try{
            String savingName = txtSavingName.getText();
            Integer tenor = Integer.parseInt(txtTenor.getText());
            Double interestRate = Double.parseDouble(txtInterestRate.getText());
            Integer minSendingDate = Integer.parseInt(txtMinSending.getText());
            String rule = cbRule.getValue();
            Date activeDate =  new Date(System.currentTimeMillis());
            addNewSavingTypeCommand = new AddNewSavingTypeCommand(savingName,tenor,interestRate, Utils.untilDateToSqlDate(activeDate),minSendingDate,rule);

            addNewSavingTypeCommand.setOnSucceed(
                    new Callback() {
                        @Override
                        public Object call(Object param) {
                            SnackBarUtils.getInstance().show(Utils.getRoot(), "Thêm thành công");
                            Boolean result = true;
                            setResult(result);
                            onClose();
                           return  null;
                        }
                    }
            );
            addNewSavingTypeCommand.execute();
        }
        catch (Exception e){
            System.out.println("============================");
            System.out.println(e.getMessage());
        }

    }
}
