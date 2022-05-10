package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Properties;

public abstract class LocalSettings {

    private static String connectionString;
    private static String lastUsername;
    private static String lastUserPassword;
    private static Boolean rememberMe;

    public static String getConnectionString() throws Exception {
        if (connectionString != null)
            return connectionString;
        Properties properties = getFromFile(CONNECTION_STRING_FILE);
        connectionString = properties.getProperty("connection_string");
        return connectionString;
    }

    public static HashMap<String, Object> getUserSetting() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (lastUsername == null || lastUserPassword == null || rememberMe == null) {
            Properties properties = null;
            try {
                properties = getFromFile(LAST_USER_FILE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            lastUsername = properties.getProperty(LAST_USERNAME_PROP);
            lastUserPassword = properties.getProperty(LAST_PASSWORD_PROP);
            rememberMe = Boolean.parseBoolean(properties.getProperty(REMEMBER_ME_PROP));
        }
        result.put(LAST_USERNAME_PROP, lastUsername);
        result.put(LAST_PASSWORD_PROP, lastUserPassword);
        result.put(REMEMBER_ME_PROP, rememberMe);
        return result;
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
    public static String LAST_USERNAME_PROP = "last_user_name";
    public static String LAST_PASSWORD_PROP = "last_user_password";
    public static String REMEMBER_ME_PROP = "remember_me";
}
