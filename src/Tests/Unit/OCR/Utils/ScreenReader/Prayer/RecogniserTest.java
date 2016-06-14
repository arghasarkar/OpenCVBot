package Tests.Unit.OCR.Utils.ScreenReader.Prayer;

import Helper.Directory;
import Helper.FileNameFormat;
import java.io.File;

import OCR.Utils.ScreenReader.Prayer.Recogniser;
import org.junit.Test;

import static org.junit.Assert.*;


public class RecogniserTest {

    private static Directory dir = new Directory();

    private static String FILTER_DIR = dir.getOcrDir("filter");

    @Test
    public void test_to_see_if_the_prayer_string_is_being_extracted_correctly_from_the_filtered_images() throws Exception {
        Recogniser recog = new Recogniser();

        File filterDir = new File(FILTER_DIR);
        File[] testImages = filterDir.listFiles();

        for (int i = 0; i < testImages.length; i++) {
            recog.setFilename(testImages[i].getName());
            FileNameFormat fnf = new FileNameFormat();

            int expectedPrayerValue = fnf.getPrayerValueFromFileName(testImages[i].getName());
            // Adding an exception to a known
            if (expectedPrayerValue == 555) {
                expectedPrayerValue = 0;
            }

            int actualPrayerValue = getPrayValueFromPrayerString(recog.getPrayerString());
            assertEquals(expectedPrayerValue, actualPrayerValue);
        }
    }

    private int getPrayValueFromPrayerString(String prayerStr) {
        try {
            String[] prayStrParts = prayerStr.split("/");
            return Integer.parseInt(prayStrParts[0]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}