package OCR.Util.ScreenReader.Prayer;

import java.io.File;
import Helper.Directory;
import net.sourceforge.tess4j.*;

public class Recogniser {

    private Directory dir = new Directory();
    private String FILTER_DIR = dir.getOcrDir("filter");
    private String FILE_NAME = "";

    private String PRAYER_STRING = "";

    public Recogniser() { }
    public Recogniser(String filename) {
        this.FILE_NAME = filename;
    }

    private void extractPrayerString() {
        String filePath = FILTER_DIR + FILE_NAME;

        File imageFile = new File(filePath);

        if (imageFile.exists()) {
            try {
                ITesseract instance = new Tesseract();
                PRAYER_STRING = instance.doOCR(imageFile);
            } catch (TesseractException e) {
                PRAYER_STRING = null;
            }
        } else {
            PRAYER_STRING = null;
        }
    }

    public String getPrayerString(String filename) {
        FILE_NAME = filename;
        return getPrayerString();
    }
    public String getPrayerString() {
        extractPrayerString();
        return PRAYER_STRING;
    }

    public void setFilename(String filename) {
        this.FILE_NAME = filename;
    }

    public String getFilename() {
        return FILE_NAME;
    }
}
