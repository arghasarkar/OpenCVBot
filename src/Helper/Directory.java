package Helper;

public class Directory {

    private final String ROOT_DIR = System.getProperty("user.dir") + "\\";

    private final String OCR_DIR = ROOT_DIR + "src\\OCR\\";
    private final String OPENCV_DIR = ROOT_DIR + "src\\OpenCV\\";

    private final String RESOURCES_DIR = ROOT_DIR + "src\\resources\\";

    public String getRootDir() {
        return ROOT_DIR;
    }

    public String getResourcesDir() {
        return getResourcesDir("");
    }
    public String getResourcesDir(String arg) {
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
        return OCR_DIR;
    }

    public String getOpencvDir() {
        return getOcrDir("");
    }
    public String getOpencvDir(String arg) {
        return OPENCV_DIR;
    }

}
