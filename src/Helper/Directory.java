package Helper;

public class Directory {

    private final String ROOT_DIR = System.getProperty("user.dir") + "\\";
    private final String SOURCE_DIR = ROOT_DIR + "src\\";

    private final String OCR_DIR = SOURCE_DIR + "OCR\\";
    private final String OPENCV_DIR = SOURCE_DIR + "OpenCV\\";
    private final String TEST_DIR = SOURCE_DIR + "Tests\\";

    private final String RESOURCES_DIR = SOURCE_DIR + "resources\\";

    public String getRootDir() {
        return ROOT_DIR;
    }

    public String getResourcesDir() {
        return getResourcesDir("");
    }
    public String getResourcesDir(String arg) {
        if (arg.equals("fullMd")) {
            return getResourcesDir() + "full\\motionDetection\\";
        }
        if (arg.equals("fullDiv")) {
            return getResourcesDir() + "full\\div\\";
        }
        if (arg.equals("full")) {
            return getResourcesDir() + "full\\";
        }
        return RESOURCES_DIR;
    }

    public String getOcrDir() {
        return getOcrDir("");
    }
    public String getOcrDir(String arg) {
        if (arg.equals("filterDiv")) {
            return getOcrDir("train") + "filter\\div\\";
        }
        if (arg.equals("filter")) {
            return getOcrDir("train") + "filter\\";
        }
        if (arg.equals("train")) {
            return getOcrDir() + "TrainImages\\";
        }
        if (arg.equals("util")) {
            return getOcrDir() + "Utils\\";
        }
        if (arg.equals("testUnit")) {
            return TEST_DIR + "Unit\\OCR\\";
        }
        if (arg.equals("testInt")) {
            return TEST_DIR + "Integration\\OCR\\";
        }
        return OCR_DIR;
    }

    public String getOpencvDir() {
        return getOcrDir("");
    }
    public String getOpencvDir(String arg) {
        if (arg.equals("testUnit")) {
            return TEST_DIR + "Unit\\OpenCV\\";
        }
        if (arg.equals("testInt")) {
            return TEST_DIR + "Integration\\OpenCV\\";
        }
        return OPENCV_DIR;
    }

}
