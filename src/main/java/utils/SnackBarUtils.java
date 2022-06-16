package utils;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import constants.ResourcePaths;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class SnackBarUtils {
    private static SnackBarUtils instance = null;

    private SnackBarUtils() {
    }

    public static SnackBarUtils getInstance() {
        if (instance == null)
            instance = new SnackBarUtils();
        return instance;
    }

    private Duration defaultTimeOut = Duration.seconds(3);

    public Duration getDefaultTimeOut() {
        return defaultTimeOut;
    }

    public void setDefaultTimeOut(Duration defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }

    public void showWithAction(StackPane parent, String message, String actionButtonText, EventHandler onAction, Duration timeout) {
        if (timeout == null) {
            timeout = defaultTimeOut;
        }
        JFXSnackbar snackbar = new JFXSnackbar(parent);
        JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout(message, actionButtonText, onAction);
        String css = getClass().getResource(ResourcePaths.CSS_SNACKBAR).toExternalForm();
        snackbar.getStylesheets().add(css);
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(
                snackbarLayout,
                timeout, null));
    }

    public void show(StackPane parent, String message, Duration timeout) {
        if (timeout == null) {
            timeout = defaultTimeOut;
        }
        JFXSnackbar snackbar = new JFXSnackbar(parent);
        JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout(message);
        String css = getClass().getClassLoader().getResource(ResourcePaths.CSS_SNACKBAR).toExternalForm();
        snackbar.getStylesheets().add(css);
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(
                snackbarLayout,
                timeout, null));
    }

    public void show(StackPane parent, String message) {
        show(parent, message, defaultTimeOut);
    }

    public void showWithAction(StackPane parent, String message, String actionButtonText, EventHandler onAction) {
        showWithAction(parent, message, actionButtonText, onAction, defaultTimeOut);
    }
}
