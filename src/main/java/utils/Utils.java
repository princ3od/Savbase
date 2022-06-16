package utils;

import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;

public class Utils {

    public static HashMap<Integer, String> decodePosition = new HashMap<Integer, String>() {
        {
            put(1, "Quản lý");
            put(2, "Nhân viên");
            put(3, "Admin");
        }
    };

    public static HashMap<String, String> decodeGender = new HashMap<String, String>() {
        {
            put("true", "Nam");
            put("false", "Nữ");
        }
    };
    public static Format dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    public static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public static java.sql.Date untilDateToSqlDate(java.util.Date rawDate) {
        return new java.sql.Date(rawDate.getTime());
    }

    public static java.sql.Date localDateToSqlDate(LocalDate rawDate) {
        return new java.sql.Date(rawDate.getYear() - 1900, rawDate.getMonth().getValue() - 1, rawDate.getDayOfMonth());
    }

    private static StackPane _stackPane;

    public static StackPane getRoot() {
        return _stackPane;
    }

    public static void handleExport(TableView<?> tbReport, java.sql.Date date, String title) {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tbReport.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tbReport.getColumns().get(j).getText());
        }

        for (int i = 0; i < tbReport.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tbReport.getColumns().size(); j++) {
                if(tbReport.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(tbReport.getColumns().get(j).getCellData(i).toString());
                }
                else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(title);
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XLS files (*.xls)", "*.xls")
            );
            File saveFile = fileChooser.showSaveDialog(tbReport.getScene().getWindow());
            if (saveFile != null) {
                FileOutputStream fileOut = new FileOutputStream(saveFile.getAbsolutePath());
                workbook.write(fileOut);
                fileOut.close();
                SnackBarUtils.getInstance().show(Utils.getRoot(), "Xuất báo cáo thành công");
            }

        } catch (Exception e) {
            SnackBarUtils.getInstance().show(Utils.getRoot(), "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setRoot(StackPane stackPane) {
        _stackPane = stackPane;
    }
}
