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

        long SLEEP = 4000;
        String OUTPUT_PATH = "C:\\Users\\ArghaWin10\\Desktop\\output.png";
        String OUTPUT_PATH_FGM1 = "C:\\Users\\ArghaWin10\\Desktop\\fgMask1.png";
        String OUTPUT_PATH_FGM2 = "C:\\Users\\ArghaWin10\\Desktop\\fgMask2.png";
        String OUTPUT_PATH_FGM3 = "C:\\Users\\ArghaWin10\\Desktop\\fgMask3.png";

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

            ss1 = ss.captureScreenMat();
            Thread.sleep(SLEEP);
            System.out.println("Screen shot: 1");

            ss2 = ss.captureScreenMat();
            Thread.sleep(SLEEP);
            System.out.println("Screen shot: 2");

            ss3 = ss.captureScreenMat();
            Thread.sleep(SLEEP);
            System.out.println("Screen shot: 3");

            bg = new BackgroundSubtractor(ss1, ss2);
            fgMask1 = bg.generateFgMask();
            bg.saveFgMaskToDisk(OUTPUT_PATH_FGM1);

            bg = new BackgroundSubtractor(ss2, ss3);
            fgMask2 = bg.generateFgMask();
            bg.saveFgMaskToDisk(OUTPUT_PATH_FGM2);

            bg = new BackgroundSubtractor(fgMask1, fgMask2);
            bg.saveFgMaskToDisk(OUTPUT_PATH_FGM3);

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
    }
}
