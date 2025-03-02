package torrero.olivares.practicacorreo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import controller.MainController;
import service.LoginWindow;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);

        MainController mainController = loader.getController();
        mostrarVentanaLogin(mainController);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/gmail.png")));
        stage.setTitle("Gestor de Correos");
        stage.setScene(scene);
        stage.show();
    }

    private void mostrarVentanaLogin(MainController mainController) {
        try {
            LoginWindow loginWindow = new LoginWindow(mainController);
            loginWindow.mostrar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
