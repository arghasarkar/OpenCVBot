package OpenCV.TemplateMatching;

import Helper.Directory;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.*;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class TemplateMatch {

    private Directory dir = new Directory();
    private String RESOURCES_DIR = dir.getResourcesDir("full");
    private String FILTER_DIR = dir.getOcrDir("filter");

    private String SCREENSHOT_FILE_NAME = "screenshot.png";
    private String PRAYER_ICON_FILE_NAME = "needle.png";
    private String FILTER_PRAYER = "filter_prayer.png";


    public boolean filterImage() {

        try {

            // Constants for the height and the width of the area of the prayer points in pixels
            int PRAYER_WIDTH = 63;
            int PRAYER_HEIGHT = 23;

            // Loading the OpenCV library.
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            File prayerIconFile = new File(RESOURCES_DIR + PRAYER_ICON_FILE_NAME);
            File screenshotFile = new File(RESOURCES_DIR + SCREENSHOT_FILE_NAME);

            // Sanity check to make sure that both files are readable and they exist
            if (!prayerIconFile.exists() || !screenshotFile.exists()) {
                return false;
            }

            // Loading the images normally in their coloured formats
            Mat screenshot = Highgui.imread(screenshotFile.getAbsolutePath());
            Mat template = Highgui.imread(prayerIconFile.getAbsolutePath());

            // Create the result matrix
            int result_cols = screenshot.cols() - template.cols() + 1;
            int result_rows = screenshot.rows() - template.rows() + 1;
            Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

            // The template matching method to be used: TM_CORR_NORMED
            int matchMethod = Imgproc.TM_CCORR_NORMED;

            // Performing template matching
            Imgproc.matchTemplate(screenshot, template, result, matchMethod);
            // Normalising the result
            Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
            // Localizing the best match using minMaxLoc
            Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

            Point matchLoc;
            if (matchMethod == Imgproc.TM_SQDIFF || matchMethod == Imgproc.TM_SQDIFF_NORMED) {
                matchLoc = mmr.minLoc;
            } else {
                matchLoc = mmr.maxLoc;
            }

            // The top right corner around the matched template in the screenshot
            Point topRight = new Point(matchLoc.x + template.cols(), matchLoc.y);
            // Rectangle around the prayer sign (The template image)
            Core.rectangle(screenshot, matchLoc, new Point(
                            matchLoc.x + template.cols(), matchLoc.y + template.rows()),
                    new Scalar(0, 255, 0)
            );
            // Rectangle around the prayer points value
            Core.rectangle(screenshot, topRight, new Point (
                            matchLoc.x + template.cols() + PRAYER_WIDTH, matchLoc.y + PRAYER_HEIGHT),
                    new Scalar(255, 255, 255)
            );

            // Cropping the screenshot image to extract prayer points value
            Rect croppedRect = new Rect((int) topRight.x + 1, (int) topRight.y + 1, PRAYER_WIDTH - 1, PRAYER_HEIGHT - 1);
            Mat croppedImage = new Mat(screenshot, croppedRect);

            // Making the image larger by 10 times in both the X and the Y axis
            Imgproc.resize(croppedImage, croppedImage, new Size(croppedImage.width() * 10, croppedImage.height() * 10));

            // Applying a gaussian filter to smoothen / blur the image. This makes it easier for the OCR software
            Imgproc.GaussianBlur(croppedImage, croppedImage, new Size(croppedImage.width() + 1, croppedImage.height() + 1), 6.0);

            // Using binary threshold to convert the image into Black and White. (Not greyscale)
            Imgproc.threshold(croppedImage, croppedImage, 120, 255, 0);

            // Saving the processed image to a file on disk
            Highgui.imwrite(FILTER_DIR + FILTER_PRAYER, croppedImage);

            return true;
        } catch (Exception ex) {  ex.printStackTrace(System.err);}
        
        return false;
    }
}
