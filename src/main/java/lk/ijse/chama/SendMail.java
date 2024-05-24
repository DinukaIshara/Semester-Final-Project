package lk.ijse.chama;

import javafx.scene.control.Alert;

import java.io.File;


public class SendMail {
    public static void sendEmail(String recipientEmail, String attachmentPath , int num) {
        String senderEmail = "dinukaishara692@gmail.com";
        String senderPassword = "ptrhbobvzetisgdb";
        String subject = "";
        String body = "";

        if(num == 1){
            subject = "Your QR Code - Bring this on your return.";
            body = "Congratulations...! You are created account chama computers successfully.";
        } else if (num == 2) {
            subject = "Your Bill ";
            body = "Thank You Chosen us... Bring this when making a warranty claim";
        }


        java.util.Properties properties = new java.util.Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            javax.mail.Message message = new javax.mail.internet.MimeMessage(session);
            message.setFrom(new javax.mail.internet.InternetAddress(senderEmail));
            message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            javax.mail.Multipart multipart = new javax.mail.internet.MimeMultipart();

            javax.mail.internet.MimeBodyPart messageBodyPart = new javax.mail.internet.MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            javax.mail.internet.MimeBodyPart attachmentPart = new javax.mail.internet.MimeBodyPart();
            attachmentPart.attachFile(new File(attachmentPath));
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            javax.mail.Transport.send(message);

            System.out.println("Email sent successfully with Document.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Email sent successfully..! with Document");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
