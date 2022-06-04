package utils;

import javafx.scene.layout.StackPane;

import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

public class Utils {

    public static HashMap<Integer, String> decodePosition = new HashMap<Integer, String>() {
        {
            put(1, "Nhân viên");
            put(2, "Trưởng phòng");
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

    public static NumberFormat curencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public static java.sql.Date untilDateToSqlDate(java.util.Date rawDate) {
        return new java.sql.Date(rawDate.getTime());
    }

    private static StackPane _stackPane;

    public static StackPane getRoot() {
        return _stackPane;
    }

    public static void setRoot(StackPane stackPane) {
        _stackPane = stackPane;
    }
}
