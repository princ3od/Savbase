package controllers.tabs;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import command.GetAllSavingsTypeCommand;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import models.SavingAccount;
import models.SavingType;
import javafx.util.Callback;

import java.util.ArrayList;

public class SettingController {

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

    //Logic
    private GetAllSavingsTypeCommand getAllSavingsTypeCommand;


    @FXML
    void initialize(){
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
        tbSavingType.getColumns().addAll(savingType,tennor, interestRate, minSendingDate,rule);
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
        System.out.println("savingTypes");
        for(var type:savingTypes){
            tbSavingType.getItems().add(type);
        }
    }


}
