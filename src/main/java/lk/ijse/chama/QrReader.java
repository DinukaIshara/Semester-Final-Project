package lk.ijse.chama;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import lk.ijse.chama.model.QrResult;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QrReader {

    private static WebcamPanel panel = null;
    private static JFrame window;
    private static Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Result result = null;
    private QrResult qrResultModel;


    public QrReader(QrResult qrResultModel) {
        this.qrResultModel = qrResultModel;
        initWebcam();
    }

    public void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        webcam.setViewSize(new Dimension(640, 480));
        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setFPSLimited(true);
        panel.setFPSLimit(20);

        window = new JFrame("QR Scanner");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        executor.execute(() -> {
            do {
                BufferedImage image = null;

                if (webcam.isOpen()) {
                    if ((image = webcam.getImage()) == null) {
                        continue;
                    }
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // No result...
                }

                if (result != null) {
                    qrResultModel.setResult(result.getText());
                    closeWebcamPanel();
                    return;
                }

            } while (true);
        });
    }

    public static void closeWebcamPanel() {
        try {
            if (panel != null) {
                panel.stop();
            }
            if (window != null) {
                window.dispose();
            }
        } finally {
            if (webcam != null) {
                webcam.close();
            }
            if (Webcam.getDiscoveryService().isRunning()) {
                Webcam.getDiscoveryService().stop();
            }
        }
    }
}
