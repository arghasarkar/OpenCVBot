package RoughWork.TemplateMatching;

import org.opencv.core.*;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import java.io.File;

import Helper.FileNameFormat;
import Helper.Directory;

public class TempMatch {

    private static final Directory dir = new Directory();

    private static final String NEEDLE_FILE_NAME = "needle.png";
    private static final String HAYSTACK_FILE_NAME = "full_708.png";
    private static final String OUTPUT_FILE = new FileNameFormat().getPrayerStringFromFileName(HAYSTACK_FILE_NAME) + ".png";

    private static final String RESOURCES_DIR = dir.getResourcesDir();
    private static final String ROOT_DIR = dir.getRootDir();
    private static final String OUTPUT_DIR = "src\\OCR\\TrainImages\\filter\\";

    public static void main (String args[]){

        // Constants for the height and the width of the area of the prayer points in pixels
        int PRAYER_WIDTH = 63;
        int PRAYER_HEIGHT = 23;
        
        // Loading the OpenCV library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        File needle = new File(dir.getResourcesDir("full") + NEEDLE_FILE_NAME);
        File haystack = new File(dir.getResourcesDir("full") + HAYSTACK_FILE_NAME);

        // Sanity check to make sure that both files are readable and they exist
        if (!needle.exists() || !haystack.exists()) {
            System.exit(-1);
        }

        System.out.println("Both files exists!");

        // Loading the images as grayscale
        //Mat image = Highgui.imread(haystack.getAbsolutePath(), Highgui.IMREAD_GRAYSCALE);
        //Mat template = Highgui.imread(needle.getAbsolutePath(), Highgui.IMREAD_GRAYSCALE);
        // Loading the images normally in their coloured formats
        Mat image = Highgui.imread(haystack.getAbsolutePath());
        Mat template = Highgui.imread(needle.getAbsolutePath());

        // / Create the result matrix
        int result_cols = image.cols() - template.cols() + 1;
        int result_rows = image.rows() - template.rows() + 1;
        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

        // Matching method
        int matchMethod = Imgproc.TM_CCORR_NORMED;


        // / Do the Matching and Normalize
        Imgproc.matchTemplate(image, template, result, matchMethod);
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        // / Localizing the best match with minMaxLoc
        MinMaxLocResult mmr = Core.minMaxLoc(result);

        Point matchLoc;
        if (matchMethod == Imgproc.TM_SQDIFF || matchMethod == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }

        // / Show me what you got
        Point topRight = new Point(matchLoc.x + template.cols(), matchLoc.y);
        // Rectangle around the prayer sign (The template image)
        Core.rectangle(image, matchLoc, new Point(
                matchLoc.x + template.cols(), matchLoc.y + template.rows()),
                new Scalar(0, 255, 0)
        );
        // Rectangle around the prayer points value
        Core.rectangle(image, topRight, new Point (
                matchLoc.x + template.cols() + PRAYER_WIDTH, matchLoc.y + PRAYER_HEIGHT),
                new Scalar(255, 255, 255)
        );

        // Cropping the prayer points image
        Rect croppedRect = new Rect( (int) topRight.x + 1, (int) topRight.y + 1, PRAYER_WIDTH - 1, PRAYER_HEIGHT - 1);
        Mat croppedImage = new Mat(image, croppedRect);

        // Converting the image into pure black and white (not greyscale) using binary thresholding
        //Imgproc.threshold(croppedImage, croppedImage, 128, 255, 0);

        // Making the image larger
        Mat largerImage = croppedImage;
        Imgproc.resize(croppedImage, largerImage, new Size(croppedImage.width() * 10, croppedImage.height() * 10));

        // Applying a gaussian filter to smoothen the image
        Mat filteredImage = largerImage;
        Imgproc.GaussianBlur(largerImage, filteredImage, new Size(largerImage.width() + 1, largerImage.height() + 1), 6.0);

        Imgproc.threshold(largerImage, largerImage, 120, 255, 0);

        // Saving the cropped image
        Highgui.imwrite(ROOT_DIR + OUTPUT_DIR + "filter_" + OUTPUT_FILE, filteredImage);

        // Save the visualized detection.
        System.out.println("Writing "+ OUTPUT_FILE);
        System.out.println(
                "x1: " + matchLoc.x +
                " y1: " + matchLoc.y +
                " cols: " + template.cols() +
                 " rows: " + template.rows()
        );

    }

    private static String getFileNumber(String rawFileName) {
        String[] fileNameParts = rawFileName.split("_");
        return fileNameParts[(fileNameParts.length - 1)];
    }

}
