package lk.ijse.chama;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QrGenerateor {
    static String namee;

    public static void setData(String mobileNumber, String gmail, String name) {
        namee = name;

        System.out.println("Mobile Number: " + mobileNumber);
        try {
            String data = mobileNumber;
            String filePath = "qrcode.png";
            generateQRCodeImage(data, 350, 350, filePath);

            sendEmail(gmail, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateQRCodeImage(String data, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        File outputFile = new File(filePath);
        ImageIO.write(bufferedImage, "png", outputFile);
    }

    private static void sendEmail(String recipientEmail, String attachmentPath) {
        String senderEmail = "dinukaishara692@gmail.com";
        String senderPassword = "ptrhbobvzetisgdb";
        String subject = "Your QR Code - Bring this on your return.";
        String body = "Congratulations \"" + namee + "\".. you are created account chama computers successfully.";

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

            System.out.println("Email sent successfully with the QR code attachment.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Email sent successfully with the QR code attachment.");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
