package service;

import modelo.Correo;

import javax.mail.*;
import java.util.*;

public class GmailService {

    public static boolean validarCredenciales(String correo, String password) {
        try {
            Properties propiedades = new Properties();
            propiedades.put("mail.store.protocol", "imaps");
            propiedades.put("mail.imap.host", "imap.gmail.com");
            propiedades.put("mail.imap.port", "993");
            propiedades.put("mail.imap.ssl.enable", "true");

            Session sesion = Session.getInstance(propiedades);
            Store store = sesion.getStore("imaps");
            store.connect("imap.gmail.com", correo, password); // Intenta conectar

            store.close(); // Si conecta correctamente, cierra la conexión
            return true;
        } catch (MessagingException e) {
            return false; // Si hay error, las credenciales son incorrectas
        }
    }

    public static List<Correo> obtenerCorreosEtiquetados(String correo, String password) throws MessagingException {
        List<Correo> resultados = new ArrayList<>();

        Properties propiedades = new Properties();
        propiedades.put("mail.store.protocol", "imaps");
        propiedades.put("mail.imap.host", "imap.gmail.com");
        propiedades.put("mail.imap.port", "993");
        propiedades.put("mail.imap.ssl.enable", "true");

        Session sesion = Session.getInstance(propiedades);
        Store store = sesion.getStore("imaps");
        store.connect("imap.gmail.com", correo, password);

        Folder[] carpetas = store.getDefaultFolder().list();
        Map<Message, List<String>> correosEtiquetados = new HashMap<>();

        for (Folder carpeta : carpetas) {
            if ((carpeta.getType() & Folder.HOLDS_MESSAGES) == 0 || !esEtiquetaPersonalizada(carpeta.getName())) {
                continue;
            }

            carpeta.open(Folder.READ_ONLY);
            int totalMensajes = carpeta.getMessageCount();
            if (totalMensajes == 0) {
                carpeta.close(false);
                continue;
            }

            int inicio = Math.max(1, totalMensajes - 6);
            Message[] mensajes = carpeta.getMessages(inicio, totalMensajes);

            FetchProfile perfil = new FetchProfile();
            perfil.add(FetchProfile.Item.ENVELOPE);
            carpeta.fetch(mensajes, perfil);

            for (Message mensaje : mensajes) {
                correosEtiquetados.computeIfAbsent(mensaje, k -> new ArrayList<>()).add(carpeta.getName());
            }

            carpeta.close(false);
        }

        store.close();

        for (Map.Entry<Message, List<String>> entry : correosEtiquetados.entrySet()) {
            Message mensaje = entry.getKey();
            String asunto = mensaje.getSubject() != null ? mensaje.getSubject() : "Sin Asunto";
            String etiquetas = String.join(", ", entry.getValue());

            resultados.add(new Correo(asunto, etiquetas));
        }

        return resultados;
    }

    private static boolean esEtiquetaPersonalizada(String etiqueta) {
        List<String> etiquetasPredeterminadas = Arrays.asList("INBOX", "IMPORTANT", "SENT", "DRAFTS", "SPAM", "TRASH", "STARRED", "UNREAD");
        return !etiquetasPredeterminadas.contains(etiqueta.toUpperCase());
    }
}
