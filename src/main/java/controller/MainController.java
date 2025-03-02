package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Correo;
import service.GmailService;
import service.LoginWindow;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    private TableView<Correo> tablaCorreos;
    @FXML
    private TableColumn<Correo, String> columnaAsunto;
    @FXML
    private TableColumn<Correo, String> columnaEtiqueta;
    @FXML
    private Button btnReiniciarSesion; // Botón para reiniciar sesión

    private String correo;
    private String password;

    @FXML
    private void initialize() {
        columnaAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
        columnaEtiqueta.setCellValueFactory(new PropertyValueFactory<>("etiqueta"));

        // Asignar acción al botón para reiniciar sesión
        btnReiniciarSesion.setOnAction(event -> mostrarVentanaLogin());
    }

    public void setCredenciales(String correo, String password) {
        this.correo = correo;
        this.password = password;
        cargarCorreos();
    }

    private void cargarCorreos() {
        try {
            List<modelo.Correo> correosEtiquetados = GmailService.obtenerCorreosEtiquetados(correo, password);

            tablaCorreos.getItems().clear();
            tablaCorreos.getItems().addAll(correosEtiquetados);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // ✅ **Agrega este método para evitar el error**
    private void mostrarVentanaLogin() {
        try {
            LoginWindow loginWindow = new LoginWindow(this);
            loginWindow.mostrar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
