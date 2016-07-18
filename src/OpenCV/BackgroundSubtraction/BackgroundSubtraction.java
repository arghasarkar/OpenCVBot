package OpenCV.BackgroundSubtraction;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.BackgroundSubtractorMOG;
import org.opencv.video.BackgroundSubtractorMOG2;

import java.io.File;

public class BackgroundSubtraction {

    private Mat BACKGROUND = null;
    private Mat CURRENT = null;

    private String ALGORITHM = "MOG2";
    private String BACKGROUND_PATH = "";
    private String CURRENT_PATH = "";

    private boolean INITIALISED = false;

    public BackgroundSubtraction(String backgroundPath, String currentPath) {
        // Loading the opencv library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        this.BACKGROUND_PATH = backgroundPath;
        this.CURRENT_PATH = currentPath;

        File fileBackground = new File(backgroundPath);
        File fileFrame = new File(currentPath);

        if (!fileBackground.exists() || !fileFrame.exists()) {
            System.exit(-1);
        }

        this.BACKGROUND = Highgui.imread(BACKGROUND_PATH);
        this.CURRENT = Highgui.imread(CURRENT_PATH);

        INITIALISED = true;
    }

    public BackgroundSubtraction(Mat backgroundFrame, Mat currentFrame) {
        // Loading the opencv library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        this.BACKGROUND = backgroundFrame;
        this.CURRENT = currentFrame;

        INITIALISED = true;
    }

    public Mat generateFgMask() {
        if (INITIALISED) {
            return processImages(BACKGROUND, CURRENT, ALGORITHM);
        }
        return null;
    }

    public boolean saveFgMaskToDisk(String OUTPUT_PATH) {
        if (INITIALISED) {
            Mat fgMask = processImages(BACKGROUND, CURRENT, ALGORITHM);
            return Highgui.imwrite(OUTPUT_PATH, fgMask);
        }
        return false;
    }

    private Mat processImages(Mat backgroundFrame, Mat currentFrame, String algorithm) {
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

    public void setAlgorithm(String algorithm) {
        this.ALGORITHM = algorithm;
    }

    public String getAlgorithm() {
        return this.ALGORITHM;
    }
}
