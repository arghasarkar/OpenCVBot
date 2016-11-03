package ExecutionEngine;

import Util.ScreenCapture.ScreenShot;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import OpenCV.BackgroundSubtraction.BackgroundSubtractor;

public class BackgroundSubtraction {

    public static void main(String[] args) {

        long SLEEP = 5000;
        String OUTPUT_PATH = "C:\\Users\\ArghaWin10\\Desktop\\output.png";

        // Loading the opencv library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        /**
         * 1) Take screenshot
         * 2) Take screenshot #2
         * 3) Generate fgmask
         * 4) Save fgMask to disk
         */

        ScreenShot ss = new ScreenShot();

        Mat ss1, ss2, output;

        try {
            ss1 = ss.captureScreenMat();

            Thread.sleep(SLEEP);

            ss2 = ss.captureScreenMat();

            BackgroundSubtractor bgrSub = new BackgroundSubtractor(ss1, ss2);

            bgrSub.saveFgMaskToDisk(OUTPUT_PATH);

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }

    }

}
