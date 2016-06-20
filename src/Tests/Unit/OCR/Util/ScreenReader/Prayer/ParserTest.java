package Tests.Unit.OCR.Util.ScreenReader.Prayer;

import OCR.ScreenReader.Prayer.Parser;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest {

    private String[] testPrayerStrings = {
            "5/960",
            "33/960",
            "86/960",
            "483/960",
            "555/960",
            "767/960",
            "868/960",
            "960/960",
    };

    private int[] actualPrayerPts = {
            5,
            33,
            86,
            483,
            555,
            767,
            868,
            960
    };

    @Test
    public void test_to_see_whether_the_prayer_level_is_being_parsed_correctly_from_the_prayer_string() throws Exception {
        int expectedPrayerLevel = 96;
        for (int i = 0; i < testPrayerStrings.length; i++) {
            Parser parser = new Parser(testPrayerStrings[i]);
            int actualPrayerLevel = parser.getPrayerLevel();

            assertEquals(expectedPrayerLevel, actualPrayerLevel);
        }
    }

    @Test
    public void test_to_see_whether_the_maximum_prayer_points_is_being_parsed_correctly_from_the_prayer_string() throws Exception {
        int expectedMaxPrayerPts = 960;
        for (int i = 0; i < testPrayerStrings.length; i++) {
            Parser parser = new Parser(testPrayerStrings[i]);
            int actualMaxPrayerPoints = parser.getMaxPrayerPts();

            assertEquals(expectedMaxPrayerPts, actualMaxPrayerPoints);
        }
    }

    @Test
    public void test_to_see_whether_the_remaining_prayer_points_is_being_parsed_correctly_from_the_prayer_string() throws Exception {

        for (int i = 0; i < testPrayerStrings.length; i++) {
            int expectedPrayerPts = actualPrayerPts[i];

            Parser parser = new Parser(testPrayerStrings[i]);
            int actualPrayerPts = parser.getPrayerPts();

            assertEquals(expectedPrayerPts, actualPrayerPts);
        }
    }


}