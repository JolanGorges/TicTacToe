package com.example.tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.prefs.Preferences;
import java.io.IOException;
import java.lang.reflect.Constructor;

public class App extends Application {
    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    public static void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadView(String fxmlPath, Object model) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            loader.setControllerFactory(controllerClass -> {
                try {
                    Constructor<?> constructor = controllerClass.getConstructor(model.getClass());
                    return constructor.newInstance(model);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(Parent root) {
        if (scene == null) {
            scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("TicTacToe");
            stage.setScene(scene);
            stage.show();
        } else {
            scene.setRoot(root);
            scene.getWindow().sizeToScene();
        }
    }

    @Override
    public void start(Stage stage) {
        loadView("views/MenuView.fxml");
    }
}