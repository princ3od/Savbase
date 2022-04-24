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
    private String currentScene;
    private Stack<String> scenes = new Stack();

    public String getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(String currentScene) {
        this.currentScene = currentScene;
    }


    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public  void restart() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScenePaths.SPLASH));
        mainStage.close();
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setScene(scene);
        mainStage.show();
    }
    public void push(String sceneName, boolean resizeStage) {
        if (mainStage != null) {
            System.out.println("Navigate to " + sceneName);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sceneName));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            scenes.push(sceneName);
            mainStage.setScene(scene);
            if (resizeStage)
                mainStage.sizeToScene();
        }
    }

    public void back() {
        if (mainStage != null) {
            String previousScene = scenes.pop();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(previousScene));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainStage.setScene(scene);
            mainStage.sizeToScene();
        }
    }
}
