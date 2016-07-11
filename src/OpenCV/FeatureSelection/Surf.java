package OpenCV.FeatureSelection;

import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Surf {

    private static String NEEDLE_PATH = "";
    private static String HAYSTACK_PATH = "";
    private static String OUTPUT_PATH = "";

    public static void performSurf(String needlePath, String haystackPath, String outputPath) {
        NEEDLE_PATH = needlePath;
        HAYSTACK_PATH = haystackPath;
        OUTPUT_PATH = outputPath;

        // Loading the opencv library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Loading up the two raw images from the raw image files
        Mat needleImage = Imgcodecs.imread(NEEDLE_PATH);
        Mat haystackImage = Imgcodecs.imread(HAYSTACK_PATH);

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

        FeatureDetector featureDetector = FeatureDetector.create(FeatureDetector.FAST);
        DescriptorExtractor descriptorExtractor = DescriptorExtractor.create(DescriptorExtractor.BRISK);

        MatOfKeyPoint keyPointNeedle = new MatOfKeyPoint();
        featureDetector.detect(needleGray, keyPointNeedle);

        MatOfKeyPoint keyPointHaystack = new MatOfKeyPoint();
        featureDetector.detect(haystackGray, keyPointHaystack);

        Mat descriptorsNeedle = new Mat(needleImage.rows(), needleImage.cols(), needleImage.type());
        descriptorExtractor.compute(needleGray, keyPointNeedle, descriptorsNeedle);

        Mat descriptorsHaystack = new Mat(haystackImage.rows(), haystackImage.cols(), haystackImage.type());
        descriptorExtractor.compute(haystackGray, keyPointHaystack, descriptorsHaystack);

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

        Imgcodecs.imwrite(OUTPUT_PATH, matchedImage);
    }

}
