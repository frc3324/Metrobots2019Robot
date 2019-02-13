package frc.team3324.robot.util;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class VisionRunnable implements Runnable {
    public void run() {
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(640, 480);

        CvSink cvSink = CameraServer.getInstance().getVideo();
        CvSource outputStream = CameraServer.getInstance().putVideo("Bounding line", 640, 480);

        Mat source = new Mat();

        while (!Thread.interrupted()) {
            cvSink.grabFrame(source);
            Imgproc.line(source, new Point((source.size().width / 2), (source.size().height) * .6),
                    new Point((source.size().width), (source.size().height) * .6),
                    new Scalar(255, 0, 0), 5);
            outputStream.putFrame(source);
        }
    }
}
