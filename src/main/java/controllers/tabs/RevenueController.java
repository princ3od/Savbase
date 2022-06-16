package controllers.tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import command.GetRevenueReportsCommand;
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
import models.RevenueReport;
import utils.SnackBarUtils;
import utils.Utils;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RevenueController {

    @FXML
    private Label lblTitle;

    @FXML
    private TableView<RevenueReport> tbReport;

    @FXML
    private TableColumn<RevenueReport, String> sttColumn = new TableColumn<RevenueReport, String>("STT");

    @FXML
    private TableColumn<RevenueReport, String> typeColumn = new TableColumn<>("Loại tiết kiệm");

    @FXML
    private TableColumn<RevenueReport, String> revenueColumn = new TableColumn<>("Tổng thu");

    @FXML
    private TableColumn<RevenueReport, String> costColumn = new TableColumn<>("Tổng chi");

    @FXML
    private TableColumn<RevenueReport, String> differenceColumn = new TableColumn<>("Chênh lệch");


    @FXML
    private AreaChart<Date, Double> acReport;

    @FXML
    private JFXDatePicker dpFrom;

    @FXML
    private JFXDatePicker dpTo;

    @FXML
    private JFXButton btnExport;

    ObservableList<RevenueReport> reports;

    @FXML
    void initialize() {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("savingType"));
        typeColumn.setPrefWidth(200);
        sttColumn.setCellFactory(col -> new TableCell<RevenueReport, String>() {
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
        revenueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RevenueReport, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RevenueReport, String> param) {
                return new SimpleStringProperty(Utils.currencyFormat.format(param.getValue().getTotalRevenue()));
            }
        });
        revenueColumn.setPrefWidth(100);
        costColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RevenueReport, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RevenueReport, String> param) {
                return new SimpleStringProperty(Utils.currencyFormat.format(param.getValue().getTotalCost()));
            }
        });
        costColumn.setPrefWidth(100);
        differenceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RevenueReport, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<RevenueReport, String> param) {
                return new SimpleStringProperty(Utils.currencyFormat.format(param.getValue().getDifference()));
            }
        });
        differenceColumn.setPrefWidth(100);
        tbReport.getColumns().clear();
        tbReport.getColumns().addAll(sttColumn, typeColumn, revenueColumn, costColumn, differenceColumn);
        dpFrom.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 == null || dpTo.getValue() == null) loadChart(null,null);
                else loadChart(Utils.localDateToSqlDate(t1), Utils.localDateToSqlDate(dpTo.getValue()));
            }
        });
        dpTo.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 == null || dpFrom.getValue() == null) loadChart(null,null);
                else loadChart(Utils.localDateToSqlDate(dpFrom.getValue()), Utils.localDateToSqlDate(t1));
            }
        });
        loadData();
    }

    void loadData() {
        GetRevenueReportsCommand command = new GetRevenueReportsCommand();
        command.setOnSucceed(new Callback() {
            @Override
            public Object call(Object o) {
                reports = (ObservableList<RevenueReport>) command.getResult();
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
    }

    void loadChart(Date from, Date to) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        XYChart.Series dataSeries =  new XYChart.Series<Date, Double>();
        var newReports = (from != null && to != null)
                ? FXCollections.observableArrayList(reports.stream()
                .filter(new Predicate<RevenueReport>() {
                    @Override
                    public boolean test(RevenueReport revenueReport) {
                        return revenueReport.getDate().compareTo(from) >= 0 && revenueReport.getDate().compareTo(to) <= 0;
                    }
                })
                .toList())
                : FXCollections.observableArrayList(new ArrayList<RevenueReport>());
        Map<Date, Double> map = new HashMap<Date, Double>();
        for (int i = 0; i < newReports.size(); i++) {
            var report = newReports.get(i);
            System.out.println("Djt me: " + report.getDate().toString() );

            if (map.get(report.getDate()) == null) {
                map.put(report.getDate(), report.getDifference());
            }
            else {
                map.put(report.getDate(), report.getDifference() + map.get(report.getDate()));
            }
        }

        Set<Date> set = map.keySet();
        set = set.stream().sorted(new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        }).collect(Collectors.toCollection(LinkedHashSet::new));

        acReport.getData().clear();
        acReport.getData().addAll(dataSeries);

        for (Date key : set) {
            System.out.println(key.toString());
            var data = new XYChart.Data<>(key.toString(), map.get(key));
            dataSeries.getData().add(data);
            data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    loadTable(key);
                }
            });

        }



    }

    void loadTable(Date date) {
        lblTitle.setText("Báo cáo doanh số hoạt động ngày " + Utils.dateFormatter.format(date));
        var tableReports = reports.stream().filter(new Predicate<RevenueReport>() {
            @Override
            public boolean test(RevenueReport revenueReport) {
                return revenueReport.getDate().equals(date);
            }
        }).toList();
        tbReport.getItems().clear();
        tbReport.getItems().addAll(tableReports);
        btnExport.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Utils.handleExport(tbReport, date, "revenue_" + new SimpleDateFormat("dd_MM_yyyy").format(date));
            }
        });

    }

    @FXML
    void onExport(ActionEvent event) {

    }

}
