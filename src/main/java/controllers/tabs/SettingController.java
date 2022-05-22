package controllers.tabs;

import com.jfoenix.controls.JFXTabPane;
import command.GetAllSavingsTypeCommand;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.SavingType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SettingController {
    @FXML
    public TableColumn<SavingType,String> typeSavingCol;
    @FXML
    public TableColumn<SavingType,Integer> tenorCol;
    @FXML
    public TableColumn<SavingType,Double> interestRateCol;
    @FXML
    public TableColumn<SavingType,Integer> minSendingDateCol;
    @FXML
    public TableColumn<SavingType,String> ruleCol;
    private GetAllSavingsTypeCommand getAllSavingsTypeCommand;
    @FXML
    private Tab tabTypeSaveMoney;
    @FXML
    private TableView <SavingType>tbSavingTypes;
    //
    @FXML
    private Tab tabOtherInfo;
    @FXML
    private Tab tabEmployee;
    @FXML
    private JFXTabPane tabSettingPanel;
    private  int  _currentTab = 0;


    @FXML
    void initialize(){
        typeSavingCol.setCellValueFactory(new PropertyValueFactory<>("nameSavingType"));
        tenorCol.setCellValueFactory(new PropertyValueFactory<>("tenor"));
        interestRateCol.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
        minSendingDateCol.setCellValueFactory(new PropertyValueFactory<>("minSendingDate"));
        ruleCol.setCellValueFactory(new PropertyValueFactory<>("rule"));
        getAllSavingsTypeCommand = new GetAllSavingsTypeCommand();
        getAllSavingsTypeCommand.setOnSucceed(
                new Callback() {
                    @Override
                    public Object call(Object param) {
                        if(getAllSavingsTypeCommand.getResult()!=null){
                            showSavingTypeResult( (ArrayList<SavingType>) getAllSavingsTypeCommand.getResult());
                        }
                        return  null;
                    }
                }
        );
        getAllSavingsTypeCommand.execute();
    }

    private  void showSavingTypeResult(ArrayList<SavingType> savingTypes){
        for(var type:savingTypes){
            tbSavingTypes.getItems().add(type);
        }
    }


}
