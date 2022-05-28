package utils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Utils {

    public static HashMap<Integer, String> decodePosition =  new HashMap<Integer, String>(){
        {
            put(1, "Nhân viên");
            put(2,"Trưởng phòng");
            put(3,"Admin");
        }
    };

     public static  HashMap<String,String> decodeGender = new HashMap<String, String>(){
        {
            put("true","Nam");
            put("false","Nữ");
        }
    };
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
