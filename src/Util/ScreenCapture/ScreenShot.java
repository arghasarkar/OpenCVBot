package Util.ScreenCapture;

import Helper.Directory;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class ScreenShot {

    private Directory dir = new Directory();
    private String RESOURCES_DIR = dir.getResourcesDir("full");
    private String FILE_NAME = "screenshot.png";

    private String IMAGE_FORMAT = "png";
    private String[] ACCEPTED_FORMATS  = { "jpg", "tiff", "png", "tiff" };

    private int X = 0;
    private int Y = 0;
    private int WIDTH = 0;
    private int HEIGHT = 0;

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
        return false;
    }

    public boolean captureScreen(int x, int y, int width, int height) {
        try {
            Robot robot = new Robot();
            Rectangle screenRectangle = new Rectangle(x, y, width, height);
            BufferedImage screenShot = robot.createScreenCapture(screenRectangle);
            ImageIO.write(screenShot, IMAGE_FORMAT, new File(RESOURCES_DIR + FILE_NAME));
            return true;
        } catch (AWTException e) {
        } catch (IOException e) {
        }
        return false;
    }

    public BufferedImage captureScreenBi() {
        try {
            Robot robot = new Robot();
            Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenShot = robot.createScreenCapture(screenRectangle);

            return screenShot;
        } catch (AWTException e) {
        }

        return null;
    }

    public Mat captureScreenMat() {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            Robot robot = new Robot();
            Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenShot = new BufferedImage(screenRectangle.height, screenRectangle.width, BufferedImage.TYPE_3BYTE_BGR);
            screenShot.setData(robot.createScreenCapture(screenRectangle).getData());

            return bufferedImageToMat(screenShot);
        } catch (AWTException e) { }

        return null;
    }

    public Mat captureScreenMat(int x, int y, int width, int height) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            
            Robot robot = new Robot();
            Rectangle screenRectangle = new Rectangle(x, y, width, height);
            BufferedImage screenShot = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            screenShot.setData(robot.createScreenCapture(screenRectangle).getData());

            return bufferedImageToMat(screenShot);
        } catch (AWTException e) { }
        return null;
    }

    private Mat bufferedImageToMat(BufferedImage bi) {
        Mat imageMat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);

        byte[] pixelData = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();

        imageMat.put(0, 0, pixelData);
        return imageMat;
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

    public void setDirectory(String dirPath) {
        this.RESOURCES_DIR = dirPath;
    }

    public String getDirectory() {
        return RESOURCES_DIR;
    }
}
