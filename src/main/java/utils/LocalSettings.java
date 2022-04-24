package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public abstract class LocalSettings {

    private static String connectionString;

    public static String getConnectionString() throws Exception {
        if (connectionString != null)
            return connectionString;
        Properties properties = getFromFile(CONNECTION_STRING_FILE);
        return properties.getProperty("connection_string");
    }

    private static Properties getFromFile(String filename) throws Exception {
        FileReader reader = null;
        try {
            reader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            StringBuilder stringBuilder = new StringBuilder("Không tìm thấy file cài đặt ").append(filename);
            throw new Exception(stringBuilder.toString());
        }
        Properties properties = new Properties();
        properties.load(reader);
        return properties;
    }

    public static void saveTofile(Properties properties, String filename, String comment) throws Exception {
        properties.store(new FileWriter(filename), comment);
    }

    public static String CONNECTION_STRING_FILE = "db.properties";
    public static String LAST_USER_FILE = "last_user.properties";

}
