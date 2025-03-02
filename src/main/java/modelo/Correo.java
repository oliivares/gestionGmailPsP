package modelo;

public class Correo {
    private String asunto;
    private String etiqueta;

    public Correo(String asunto, String etiqueta) {
        this.asunto = asunto;
        this.etiqueta = etiqueta;
    }

    public String getAsunto() {
        return asunto;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
