package Tests.Integration.OpenCV.TemplateMatching;

import Helper.Directory;
import OpenCV.TemplateMatching.Prayer_TM;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.File;
import static org.junit.Assert.*;

public class PrayerTMTest {

    private Directory dir = new Directory();
    private String FILTER_DIR = dir.getOcrDir("filter");
    private String FILTER_PRAYER = "filter_prayer.png";

    @Test
    public void test_to_see_if_a_filtered_image_has_been_created() throws Exception {
        File f = new File(FILTER_DIR + FILTER_PRAYER);
        boolean expectedFileStatus = true;

        Prayer_TM prayerTM = new Prayer_TM();
        prayerTM.filterImage();
        boolean actualFileStatus = f.exists();

        assertEquals(expectedFileStatus, actualFileStatus);
    }

    @Test
    public void test_to_see_if_the_filtered_image_is_of_the_correct_resolution() throws Exception {
        File f = new File(FILTER_DIR + FILTER_PRAYER);
        int expectedHeight = 220;
        int expectedWidth = 620;

        System.out.println(f.getAbsolutePath());

        // Loading the OpenCV library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat file = Highgui.imread(f.getAbsolutePath());

        int actualHeight = file.rows();
        int actualWidth = file.cols();

        assertEquals(expectedHeight, actualHeight);
        assertEquals(expectedWidth, actualWidth);
    }

}