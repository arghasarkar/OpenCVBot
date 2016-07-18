package Tests.Unit.Helper;

import Helper.Directory;
import org.junit.Test;
import static org.junit.Assert.*;
public class DirectoryTest {

    Directory dir = new Directory();
    String SOURCE_DIR = System.getProperty("user.dir") + "\\src\\";
    String TEST_DIR = System.getProperty("user.dir") + "\\src\\Tests\\";

    @Test
    public void test_to_see_if_the_RESOURCES_directories_are_giving_the_correct_path() {
        String RESOURCES_DIR = SOURCE_DIR + "resources\\";

        assertEquals(RESOURCES_DIR, dir.getResourcesDir());
        assertEquals(RESOURCES_DIR + "full\\", dir.getResourcesDir("full"));
        assertEquals(RESOURCES_DIR + "full\\div\\", dir.getResourcesDir("fullDiv"));
        assertEquals(RESOURCES_DIR + "full\\motionDetection\\", dir.getResourcesDir("fullMd"));
    }

    @Test
    public void test_to_see_if_the_OCR_directories_are_giving_the_correct_path() {
        String OCR_DIR = SOURCE_DIR + "OCR\\";

        assertEquals(OCR_DIR, dir.getOcrDir());
        assertEquals(OCR_DIR + "TrainImages\\filter\\", dir.getOcrDir("filter"));
        assertEquals(OCR_DIR + "TrainImages\\filter\\div\\", dir.getOcrDir("filterDiv"));
        assertEquals(OCR_DIR + "TrainImages\\", dir.getOcrDir("train"));
        assertEquals(TEST_DIR + "Unit\\OCR\\", dir.getOcrDir("testUnit"));
        assertEquals(TEST_DIR + "Integration\\OCR\\", dir.getOcrDir("testInt"));
    }

    @Test
    public void test_to_see_if_the_OpenCV_directories_are_giving_the_correct_path() {
        String OPENCV_DIR = SOURCE_DIR + "OpenCV\\";

        assertEquals(TEST_DIR + "Unit\\OpenCV\\", dir.getOpencvDir("testUnit"));
        assertEquals(TEST_DIR + "Integration\\OpenCV\\", dir.getOpencvDir("testInt"));
    }

}