package OCR;

import java.io.File;
import net.sourceforge.tess4j.*;

import Helper.Directory;

public class TesseractExample {

    private static Directory dir = new Directory();

    private static String ROOT_DIR = dir.getRootDir();
    private static String FILTER_DIR = dir.getOcrDir("filter");

    public static void main(String[] args) {
        String filename_818 = FILTER_DIR + "filter_pic.png";
        File imageFile = new File(filename_818);

        ITesseract instance = new Tesseract();  // JNA Interface Mapping

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}