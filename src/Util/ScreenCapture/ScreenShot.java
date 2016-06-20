package Util.ScreenCapture;

import Helper.Directory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenShot {

    private Directory dir = new Directory();
    private String RESOURCES_DIR = dir.getResourcesDir("full");
    private String FILE_NAME = "screenshot.png";

    private String IMAGE_FORMAT = "png";
    private String[] ACCEPTED_FORMATS  = { "jpg", "tiff", "png", "tiff" };

    public ScreenShot() {
        setImageFormat(IMAGE_FORMAT);
    }
    public ScreenShot(String imageFormat) {
        setImageFormat(imageFormat);
    }

    public boolean captureScreen() {
        try {
            Robot robot = new Robot();
            Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenShot = robot.createScreenCapture(screenRectangle);
            ImageIO.write(screenShot, IMAGE_FORMAT, new File(RESOURCES_DIR + FILE_NAME));
            return true;
        } catch (AWTException e) {
        } catch (IOException e) {
        }
        return true;
    }

    public int inArray(String[] haystack, String needle) {
        for (int i = 0; i < haystack.length; i++) {
            if (haystack[i].equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    public void setImageFormat(String imageFormat) {
        if (inArray(ACCEPTED_FORMATS, imageFormat) >= 0) {
            this.IMAGE_FORMAT = imageFormat;
        }
    }

    public String getImageFormat() {
        return IMAGE_FORMAT;
    }

    public void setFileName(String filename) {
        this.FILE_NAME = filename;
    }

    public String getFileName() {
        return FILE_NAME;
    }
}
