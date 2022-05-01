package navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Navigation {
    private static Navigation instance = null;

    private Navigation() {
    }

    public static Navigation getInstance() {
        if (instance == null)
            instance = new Navigation();
        return instance;
    }

    private Stage mainStage;
    private Stack<String> scenes = new Stack();

    public String getCurrentScene() {
        return scenes.peek();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void restart() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScenePaths.SPLASH));
        mainStage.close();
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void push(String sceneName) {
        if (mainStage != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));
            try {
                mainStage.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            scenes.push(sceneName);
        }
    }

    public void replace(String sceneName) {
        if (!scenes.empty()) {
            scenes.pop();
        }
        push(sceneName);
    }

    public void pushAndRemoveAll(String sceneName) {
        while (!scenes.empty()) {
            scenes.pop();
        }
        push(sceneName);
    }

    public void back() {
        if (mainStage != null) {
            scenes.pop();
            String previousScene = scenes.peek();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(previousScene));
            try {
                mainStage.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainStage.sizeToScene();
        }
    }
}
