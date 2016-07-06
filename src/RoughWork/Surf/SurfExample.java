package RoughWork.Surf;

import Helper.Directory;

import Util.ScreenCapture.ScreenShot;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.GenericDescriptorMatcher;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class SurfExample {

    private static final Directory dir = new Directory();
    private static final String RESOURCED_DIR = dir.getResourcesDir("fullDiv");

    private static final String NEEDLE_FILE_NAME = "div_vib_wisp_7.png";
    private static final String NEEDLE_PATH = RESOURCED_DIR + NEEDLE_FILE_NAME;
    private static final String HAYSTACK_FILE_NAME = "screenshot_div.png";
    private static final String HAYSTACK_PATH = RESOURCED_DIR + HAYSTACK_FILE_NAME;

    private static final String OUTPUT_FILE_NAME = "output.png";
    private static final String OUTPUT_PATH = RESOURCED_DIR + OUTPUT_FILE_NAME;

    public static void main(String[] args) {

        // Loading the opencv library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Setting up the screenshot utility
        ScreenShot ss = new ScreenShot();
        ss.setFileName(HAYSTACK_FILE_NAME);
        ss.setDirectory(RESOURCED_DIR);
        ss.captureScreen();

        // Loading up the two raw images from the raw image files
        Mat needleImage = Highgui.imread(NEEDLE_PATH);
        Mat haystackImage = Highgui.imread(HAYSTACK_PATH);

        if (needleImage == null || haystackImage == null) {
            System.out.println("Can't read file");
            System.exit(-1);
        }
        System.out.println("Successfully read both the raw image files!");

        Mat needleGray = new Mat(needleImage.rows(), needleImage.cols(), needleImage.type());
        Imgproc.cvtColor(needleImage, needleGray, Imgproc.COLOR_BGRA2GRAY);
        Core.normalize(needleGray, needleGray, 0, 255, Core.NORM_MINMAX);

        Mat haystackGray = new Mat(haystackImage.rows(), haystackImage.cols(), haystackImage.type());
        Imgproc.cvtColor(haystackImage, haystackGray, Imgproc.COLOR_BGRA2GRAY);
        Core.normalize(haystackGray, haystackGray, 0, 255, Core.NORM_MINMAX);

        FeatureDetector siftDetector = FeatureDetector.create(FeatureDetector.SIFT);
        DescriptorExtractor siftExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);

        MatOfKeyPoint keyPointNeedle = new MatOfKeyPoint();
        siftDetector.detect(needleGray, keyPointNeedle);

        MatOfKeyPoint keyPointHaystack = new MatOfKeyPoint();
        siftDetector.detect(haystackGray, keyPointHaystack);

        Mat descriptorsNeedle = new Mat(needleImage.rows(), needleImage.cols(), needleImage.type());
        siftExtractor.compute(needleGray, keyPointNeedle, descriptorsNeedle);

        Mat descriptorsHaystack = new Mat(haystackImage.rows(), haystackImage.cols(), haystackImage.type());
        siftExtractor.compute(haystackGray, keyPointHaystack, descriptorsHaystack);

        MatOfDMatch matches = new MatOfDMatch();
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        matcher.match(descriptorsNeedle, descriptorsHaystack, matches);

        int N = 50;
        DMatch[] tmp01 = matches.toArray();
        DMatch[] tmp02 = new DMatch[N];
        System.out.println("Tmp: " + tmp01.length + "---- tmp2: " + tmp02.length);
        for (int i=0; i < tmp02.length; i++) {
            tmp02[i] = tmp01[i];
        }
        matches.fromArray(tmp02);

        Mat matchedImage = new Mat(needleImage.rows(), needleImage.cols()*2, needleImage.type());
        Features2d.drawMatches(needleImage, keyPointNeedle, haystackImage, keyPointHaystack, matches, matchedImage);

        Highgui.imwrite(OUTPUT_PATH, matchedImage);
    }

}
