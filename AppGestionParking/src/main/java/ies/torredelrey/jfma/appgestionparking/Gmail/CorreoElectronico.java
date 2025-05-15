package ies.torredelrey.jfma.appgestionparking.Gmail;

import java.io.IOException;
import java.util.*;
import jakarta.mail.*;
import java.io.File;
import jakarta.mail.internet.*;

public class CorreoElectronico {


    public static boolean enviarConGMail(String destinatario, String asunto, String cuerpo,String rutaFactura) {
        // La dirección de correo de envío
        String remitente = "juanfranmarque@gmail.com";
        // La clave de aplicación (no la contraseña habitual de Gmail)
        String claveemail = "ijfg ufsm qxmg dnch";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);

            // Cuerpo del mensaje
            MimeBodyPart cuerpoTexto = new MimeBodyPart();
            cuerpoTexto.setText(cuerpo);

            // Parte del adjunto (PDF)
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.attachFile(new File(rutaFactura));

            // Combinar ambas partes
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoTexto);
            multipart.addBodyPart(adjunto);

            message.setContent(multipart);

            // Enviar
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            return true;
        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

