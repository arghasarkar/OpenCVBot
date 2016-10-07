package Tests.Unit.Util.ScreenCapture;

import org.junit.Test;
import static org.junit.Assert.*;
import Util.ScreenCapture.ScreenShot;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShotTest {

    @Test
    public void teat_to_see_if_the_in_array_functions_rejects_a_fail_case() throws Exception {
        String[] testArray  = { "jpg", "tiff", "png", "tiff" };
        ScreenShot ss = new ScreenShot();

        String testString = "abcd";

        int expectedValue = -1;
        int actualValue = ss.inArray(testArray, testString);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void teat_to_see_if_the_in_array_functions_displays_the_index_of_a_present_element_correctly() throws Exception {
        String[] testArray  = { "jpg", "tiff", "png", "tiff" };
        ScreenShot ss = new ScreenShot();

        String testString = "tiff";

        int expectedValue = 1;
        int actualValue = ss.inArray(testArray, testString);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void test_to_see_if_a_valid_image_format_is_accepted() {
        String testImageFormat = "tiff";
        String expectedImageFormat = "tiff";

        ScreenShot ss = new ScreenShot(testImageFormat);
        String actualImageFormat = ss.getImageFormat();

        assertEquals(expectedImageFormat, actualImageFormat);
    }

    @Test
    public void test_to_see_if_an_invalid_image_format_is_reverted_to_default() {
        String testImageFormat = "abcd";
        String expectedImageFormat = "png";

        ScreenShot ss = new ScreenShot(testImageFormat);
        String actualImageFormat = ss.getImageFormat();

        assertEquals(expectedImageFormat, actualImageFormat);
    }

    @Test
    public void test_to_see_if_the_screenshot_method_is_working() {
        String imageFormat = "png";
        ScreenShot ss = new ScreenShot(imageFormat);

        boolean expectedScreenshotStatus = true;
        boolean actualScreenshotStatus = ss.captureScreen();

        assertEquals(expectedScreenshotStatus, actualScreenshotStatus);
    }

    @Test
    public void test_to_see_if_the_screenshot_method_is_able_to_take_a_screenshot() {
        String imageFormat = "png";
        ScreenShot ss = new ScreenShot(imageFormat);
        ss.setFileName("screenshot_cropped_test.png");

        boolean expectedScreenshotStatus = true;
        boolean actualScreenshotStatus = ss.captureScreen(20, 50, 1500, 800);;

        assertEquals(expectedScreenshotStatus, actualScreenshotStatus);
    }

    @Test
    public void test_to_see_if_the_screenshot_segment_is_of_the_correct_resolution() {
        String imageFormat = "png";
        ScreenShot ss = new ScreenShot(imageFormat);
        ss.setFileName("screenshot_cropped_test.png");

        final String filePath = ss.getDirectory() + ss.getFileName();
        System.out.println(filePath);

        final int HEIGHT = 1500;
        final int WIDTH = 800;
        final int X_BUFFER = 20;
        final int Y_BUFFER = 50;

        boolean actualScreenshotStatus = ss.captureScreen(X_BUFFER, Y_BUFFER, WIDTH, HEIGHT);

        if (!actualScreenshotStatus) {
            fail();
        }

        try {
            BufferedImage screenshot = ImageIO.read(new File(filePath));
            assertEquals(HEIGHT, screenshot.getHeight());
            assertEquals(WIDTH, screenshot.getWidth());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void test_to_see_if_captureScreenBi_is_returning_a_valid_buffered_image_containing_a_valid_screenshot() {
        ScreenShot ss = new ScreenShot();
        BufferedImage expectedScreenshot = ss.captureScreenBi();
        if (expectedScreenshot == null) {
            fail();
        }
    }

    @Test
    public void test_to_see_if_captureScreenMat_is_returning_a_valid_Mat_containing_screenshot_data() {
        ScreenShot ss = new ScreenShot();
        Mat screenshotMat = ss.captureScreenMat();
        if (screenshotMat == null) {
            fail();
        }
    }

    @Test
    public void test_to_see_if_captureScreenMat_is_returning_a_valid_Mat_type_and_has_the_correct_resolution() {
        final int HEIGHT = 800;
        final int WIDTH = 1500;
        final int X_BUFFER = 20;
        final int Y_BUFFER = 50;

        ScreenShot ss = new ScreenShot();
        Mat screenshotMat = ss.captureScreenMat(X_BUFFER, Y_BUFFER, WIDTH, HEIGHT);
        if (screenshotMat == null) {
            fail();
        } else {
            int ACTUAL_HEIGHT = screenshotMat.height();
            int ACTUAL_WIDTH = screenshotMat.width();

            assertEquals(WIDTH, ACTUAL_WIDTH);
            assertEquals(HEIGHT, ACTUAL_HEIGHT);
        }

    }
    
}