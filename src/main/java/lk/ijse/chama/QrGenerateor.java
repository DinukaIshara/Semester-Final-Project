package lk.ijse.chama;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QrGenerateor {

    public static void setData(String mobileNumber, String gmail, String name) {

        System.out.println("Mobile Number: " + mobileNumber);
        try {
            String data = mobileNumber;
            String filePath = "qrcode.png";
            generateQRCodeImage(data, 350, 350, filePath);

            SendMail.sendEmail(gmail, filePath, 1);

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
}
