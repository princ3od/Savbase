import constants.ResourcePaths;
import navigation.ScenePaths;
import constants.Strings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import navigation.Navigation;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(ScenePaths.SPLASH));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(ResourcePaths.LOGO));
        stage.setTitle(Strings.APP_TITLE);
        stage.setScene(scene);
        stage.show();
        centerScreen(stage);
        Navigation.getInstance().setMainStage(stage);

    }

    public static void main(String[] args) {
        launch();
    }

    void centerScreen(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
}