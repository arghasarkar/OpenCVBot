package RoughWork.MotionDetection.BackgroundSubtraction;

import Helper.Directory;
import Util.ScreenCapture.ScreenShot;
import org.omg.PortableServer.ThreadPolicy;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import OpenCV.BackgroundSubtraction.BackgroundSubtractor;

public class BackgroundSubtractionV2 {

    private static Directory dir = new Directory();
    private static String RESOURCES_DIR = dir.getResourcesDir("fullMd");

    public static void main(String[] args) {

        final int HEIGHT = 800;
        final int WIDTH = 1500;
        final int X_BUFFER = 20;
        final int Y_BUFFER = 50;

        long SLEEP = 2000;
        String OUTPUT_PATH = "C:\\Users\\ArghaWin10\\Desktop\\output.png";
        String OUTPUT_PATH_FGM1 = "C:\\Users\\ArghaWin10\\Desktop\\fgMask1.png";
        String OUTPUT_PATH_FGM2 = "C:\\Users\\ArghaWin10\\Desktop\\fgMask2.png";
        String OUTPUT_PATH_FGM3 = "C:\\Users\\ArghaWin10\\Desktop\\fgMask3.png";

        String OUTPUT_PATH_SS1 = "\"C:\\\\Users\\\\ArghaWin10\\\\Desktop\\\\ss1.png";
        String OUTPUT_PATH_SS2 = "\"C:\\\\Users\\\\ArghaWin10\\\\Desktop\\\\ss2.png";
        String OUTPUT_PATH_SS3 = "\"C:\\\\Users\\\\ArghaWin10\\\\Desktop\\\\ss3.png";

        // Loading the opencv library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /**
         * 1) Take screenshot
         * 2) Take screenshot #2
         * 3) Generate fgmask
         * 4) Save fgMask to disk
         */

        ScreenShot ss = new ScreenShot();
        BackgroundSubtractor bg;

        Mat ss1, ss2, ss3, fgMask1, fgMask2, fgMask3, output;

        int counter = 0;

        try {

            ss1 = ss.captureScreenMat(X_BUFFER, Y_BUFFER, WIDTH, HEIGHT);
            System.out.println("Screen shot: 1 " + ss1.hashCode());
            Thread.sleep(SLEEP);

            ss2 = ss.captureScreenMat(X_BUFFER, Y_BUFFER, WIDTH, HEIGHT);;
            System.out.println("Screen shot: 2 " + ss2.hashCode());
            Thread.sleep(SLEEP);

            ss3 = ss.captureScreenMat(X_BUFFER, Y_BUFFER, WIDTH, HEIGHT);
            System.out.println("Screen shot: 3" + ss3.hashCode());
            Thread.sleep(SLEEP);

            bg = new BackgroundSubtractor(ss1, ss2);
            fgMask1 = bg.generateFgMask();
            bg.saveFgMaskToDisk(OUTPUT_PATH_FGM1);
            bg = new BackgroundSubtractor(ss2, ss3);
            fgMask2 = bg.generateFgMask();
            bg.saveFgMaskToDisk(OUTPUT_PATH_FGM2);

            bg = new BackgroundSubtractor(fgMask1, fgMask2);
            bg.saveFgMaskToDisk(OUTPUT_PATH);

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
    }
}
