module torrero.olivares.practicacorreo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.desktop;

    opens torrero.olivares.practicacorreo to javafx.fxml;
    exports torrero.olivares.practicacorreo;

    opens controller to javafx.fxml;
    exports controller;

    opens modelo to javafx.base;
    exports modelo;
}
