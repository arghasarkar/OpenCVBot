package RoughWork.Surf;

import Helper.Directory;
import OpenCV.FeatureSelection.Surf;
import Util.ScreenCapture.ScreenShot;

import java.io.File;

public class SurfLoop {

    private static final Directory dir = new Directory();
    private static final String RESOURCED_DIR = dir.getResourcesDir("fullDiv");

    private static final String NEEDLE_FILE_NAME = "div_vib_wisp_3.png";
    private static final String NEEDLE_PATH = RESOURCED_DIR + NEEDLE_FILE_NAME;
    private static final String HAYSTACK_FILE_NAME = "screenshot_div.png";
    private static final String HAYSTACK_PATH = RESOURCED_DIR + HAYSTACK_FILE_NAME;

    private static final String OUTPUT_FILE_NAME = "output.png";
    //private static final String OUTPUT_PATH = "src\\resources\\full\\div\\" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_PATH = RESOURCED_DIR + OUTPUT_FILE_NAME;

    public static void main(String[] args) {
        final int HEIGHT = 800;
        final int WIDTH = 1500;
        final int X_BUFFER = 20;
        final int Y_BUFFER = 50;

        while (true) {
            // Setting up the screenshot utility
            ScreenShot ss = new ScreenShot();
            ss.setFileName(HAYSTACK_FILE_NAME);
            ss.setDirectory(RESOURCED_DIR);
            ss.captureScreen(X_BUFFER, Y_BUFFER, WIDTH, HEIGHT);

            // 2) Performing SURF and saving the output image
            Surf.performSurf(NEEDLE_PATH, HAYSTACK_PATH, OUTPUT_PATH);
        }

    }

}
