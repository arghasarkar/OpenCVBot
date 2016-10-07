package RoughWork.MotionDetection.BackgroundSubtraction;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.highgui.VideoCapture;

import Util.ScreenCapture.ScreenShot;
import Helper.Directory;

import java.io.File;

public class BackgroundSubtraction {

    private static Directory dir = new Directory();
    private static String RESOURCES_DIR = dir.getResourcesDir("fullMd");

    private static String IMG_BACKGROUND_NAME = "md_1.png";
    private static String IMG_BACKGROUND = RESOURCES_DIR + IMG_BACKGROUND_NAME;

    private static String IMG_FRAME_NAME = "md_3.png";
    private static String IMG_FRAME = RESOURCES_DIR + IMG_FRAME_NAME;

    private static String IMG_OUTPUT_NAME = "md_output.png";
    private static String IMG_OUTPUT = RESOURCES_DIR + IMG_OUTPUT_NAME;

    private static final String ALGORITHM = "MOG2";

    public static void main(String[] args) {
        // Loading the opencv library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        File fileBackground = new File(IMG_BACKGROUND);
        File fileFrame = new File(IMG_FRAME);

        if (!fileBackground.exists() || !fileFrame.exists()) {
            System.out.println("Cannot read either / both of the files!");
            System.exit(-1);
        }

        Mat background = Highgui.imread(IMG_BACKGROUND);
        Mat frame = Highgui.imread(IMG_FRAME);

        Mat fgMask = BS(background, frame, ALGORITHM);

        Highgui.imwrite(IMG_OUTPUT, fgMask);
    }

    public static Mat BS(Mat backgroundFrame, Mat currentFrame, String algorithm) {

        if (backgroundFrame.empty() || currentFrame.empty()) {
            return null;
        }

        // Creating background subtractor object
        BackgroundSubtractor pMOG = null;

        algorithm = algorithm.toLowerCase();

        if (algorithm.equals("mog")) {
            pMOG = new BackgroundSubtractorMOG();
        } else {
            pMOG = new BackgroundSubtractorMOG2();
        }

        // Creating a foreground mask matrix
        Mat fgMask = new Mat();

        // Creating a background mask
        pMOG.apply(backgroundFrame, fgMask);
        // Applying the foreground to the background mask to create a foreground threshold
        pMOG.apply(currentFrame, fgMask);

        return fgMask;
    }

}
