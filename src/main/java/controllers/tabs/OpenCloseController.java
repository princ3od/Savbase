package controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import command.GetOpenCloseReportsCommand;
import command.GetRevenueReportsCommand;
import command.GetSavingTypeCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import models.OpenCloseReport;
import models.RevenueReport;
import models.SavingType;
import utils.SnackBarUtils;
import utils.Utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OpenCloseController {

    @FXML
    private JFXButton btnExport;

    @FXML
    private Label lblTitle;

    @FXML
    private TableView<OpenCloseReport> tbReport;

    @FXML
    private TableColumn<OpenCloseReport, String> sttColumn = new TableColumn<>("STT");

    @FXML
    private TableColumn<OpenCloseReport, String> dateColumn = new TableColumn<>("Ngày");

    @FXML
    private TableColumn<OpenCloseReport, String> openColumn = new TableColumn<>("Sổ mở");

    @FXML
    private TableColumn<OpenCloseReport, String> closeColumn = new TableColumn<>("Sổ đóng");

    @FXML
    private TableColumn<OpenCloseReport, String> differenceColumn = new TableColumn<>("Chênh lệch");

    @FXML
    private Label lblType;

    @FXML
    private AreaChart<Date, Integer> acReport;

    @FXML
    private JFXDatePicker dpFrom;

    @FXML
    private JFXDatePicker dpTo;

    @FXML
    private JFXComboBox<String> cboType;

    ObservableList<OpenCloseReport> reports;
    @FXML
    void initialize() {
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OpenCloseReport, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OpenCloseReport, String> param) {
                return new SimpleStringProperty(Utils.dateFormatter.format(param.getValue().getDate())) ;
            }
        });
        dateColumn.setPrefWidth(100);
        sttColumn.setCellFactory(col -> new TableCell<OpenCloseReport, String>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });
        sttColumn.setPrefWidth(50);
        openColumn.setCellValueFactory(new PropertyValueFactory<>("open"));
        openColumn.setPrefWidth(100);
        closeColumn.setCellValueFactory(new PropertyValueFactory<>("close"));
        closeColumn.setPrefWidth(100);

        differenceColumn.setCellValueFactory(new PropertyValueFactory<>("difference"));
        differenceColumn.setPrefWidth(100);
        tbReport.getColumns().clear();
        tbReport.getColumns().addAll(sttColumn, dateColumn, openColumn, closeColumn, differenceColumn);
        dpFrom.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 == null || dpTo.getValue() == null || cboType.getValue() == null) loadChart(null,null, cboType.getValue());
                else loadChart(Utils.localDateToSqlDate(t1), Utils.localDateToSqlDate(dpTo.getValue()), cboType.getValue());
            }
        });
        dpTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 == null || dpFrom.getValue() == null || cboType.getValue() == null) loadChart(null,null, cboType.getValue());
                else loadChart(Utils.localDateToSqlDate(dpFrom.getValue()), Utils.localDateToSqlDate(t1), cboType.getValue());
            }
        });
        cboType.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (dpFrom.getValue() == null || dpTo.getValue() == null || newValue == null) loadChart(null,null, newValue);
                else loadChart(Utils.localDateToSqlDate(dpFrom.getValue()),
                        Utils.localDateToSqlDate(dpTo.getValue()),
                        newValue);
            }
        });
        loadData();
    }

    void loadData() {
        GetOpenCloseReportsCommand command = new GetOpenCloseReportsCommand();
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object o) {
                reports = (ObservableList<OpenCloseReport>) command.getResult();
                System.out.println(reports.size());
                return null;
            }
        });
        command.setOnFail(new Callback() {
            @Override
            public Object call(Object o) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + command.getException().getMessage());
                return null;
            }
        });
        command.execute();

        GetSavingTypeCommand typeCommand = new GetSavingTypeCommand();
        typeCommand.setOnSucceed(new Callback() {
            @Override
            public Object call(Object o) {
                cboType.setItems((ObservableList<String>) typeCommand.getResult());
                cboType.getSelectionModel().select(0);
                return null;
            }
        });
        typeCommand.setOnFail(new Callback() {
            @Override
            public Object call(Object o) {
                SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + typeCommand.getException().getMessage());
                return null;
            }
        });
        typeCommand.execute();
    }

    void loadChart(Date from, Date to, String type) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        XYChart.Series dataSeries =  new XYChart.Series<Date, Double>();
        var newReports = (from != null && to != null)
                ? FXCollections.observableArrayList(reports.stream()
                .filter(new Predicate<OpenCloseReport>() {
                    @Override
                    public boolean test(OpenCloseReport report) {
                        return report.getDate().compareTo(from) >= 0 && report.getDate().compareTo(to) <= 0 && report.getSavingType().equals(cboType.getValue());
                    }
                })
                .toList())
                : FXCollections.observableArrayList(new ArrayList<OpenCloseReport>());
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < newReports.size(); i++) {
            var report = newReports.get(i);
            System.out.println("Djt me: " + report.getDate().toString() );
            var formatDateByMonth = new SimpleDateFormat("MM/yyyy").format(report.getDate());
            if (map.get(formatDateByMonth) == null) {
                map.put(formatDateByMonth, report.getDifference());
            }
            else {
                map.put(formatDateByMonth, report.getDifference() + map.get(formatDateByMonth));
            }
        }

        Set<String> set = map.keySet();
        set = set.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    return new SimpleDateFormat("MM/yyyy").parse(o1).
                            compareTo(new SimpleDateFormat("MM/yyyy").parse(o2));
                } catch (ParseException e) {
                    e.printStackTrace();
                    SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + e.getMessage());
                    return 0;
                }
            }
        }).collect(Collectors.toCollection(LinkedHashSet::new));

        acReport.getData().clear();
        acReport.getData().addAll(dataSeries);

        for (String key : set) {
            System.out.println(key.toString());
            var data = new XYChart.Data<>(key, map.get(key));
            dataSeries.getData().add(data);
            data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        loadTable(Utils.untilDateToSqlDate(new SimpleDateFormat("MM/yyyy").parse(key)), type);
                    } catch (ParseException e) {
                        SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

        }



    }

    void loadTable(Date date, String type) {
        lblTitle.setText("Báo cáo mở đóng sổ tháng " + new SimpleDateFormat("MM/yyyy").format(date));
        lblType.setText("Loại tiết kiệm: " + type);
        var tableReports = reports.stream().filter(new Predicate<OpenCloseReport>() {
            @Override
            public boolean test(OpenCloseReport report) {
                return report.getDate().getMonth() == date.getMonth()
                        && report.getDate().getYear() == date.getYear()
                        && report.getSavingType().equals(type);
            }
        }).toList();
        tbReport.getItems().clear();
        tbReport.getItems().addAll(tableReports);
        btnExport.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Utils.handleExport(tbReport, date, "open_close_" + new SimpleDateFormat("MM_yyyy").format(date));
            }
        });

    }

    @FXML
    void onExport(ActionEvent event) {

    }

}
