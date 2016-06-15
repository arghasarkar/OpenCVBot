package Tests.Unit.OpenCV.Util.ScreenCapture;

import org.junit.Test;
import static org.junit.Assert.*;
import OpenCV.Util.ScreenCapture.ScreenShot;

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
}