package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.GmailService;

import javax.mail.MessagingException;

public class LoginController {
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;

    private MainController mainController;
    private Stage stage;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleLogin() {
        String correo = txtCorreo.getText();
        String password = txtPassword.getText();

        if (correo.isEmpty() || password.isEmpty()) {
            mostrarAlertaError("Campos vacíos", "Por favor, ingrese su correo y contraseña.");
            return;
        }

        try {
            // Intenta conectar a Gmail para validar credenciales
            if (GmailService.validarCredenciales(correo, password)) {
                mainController.setCredenciales(correo, password);
                stage.close(); // Cierra la ventana solo si la autenticación es exitosa
            } else {
                mostrarAlertaError("Error de autenticación", "Credenciales incorrectas. Inténtalo de nuevo.");
            }
        } catch (Exception e) {
            mostrarAlertaError("Error de conexión", "No se pudo conectar con Gmail.");
        }
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
