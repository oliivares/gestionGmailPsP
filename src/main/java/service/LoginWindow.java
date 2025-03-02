package service;

import controller.LoginController;
import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow {
    private MainController mainController;

    public LoginWindow(MainController mainController) {
        this.mainController = mainController;
    }

    public void mostrar() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);

        LoginController controller = loader.getController();
        controller.setMainController(mainController);
        controller.setStage(stage);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/login.png")));
        stage.setTitle("Iniciar sesión en Gmail");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
