package ExecutionEngine;

import Util.Audio.Beep;
import Util.ScreenCapture.ScreenShot;
import OpenCV.TemplateMatching.TemplateMatch;
import OCR.ScreenReader.Prayer.Recogniser;
import OCR.ScreenReader.Prayer.Parser;

public class Prayer {

    public static void main(String[] args) {

        /**
         * 1) Take screenshot
         * 2) Template match from full to filter directory
         * 3) OCR: Recognise
         * 4) OCR: Parse the text
         * 5) Print out prayer points. Beep if needed.
         */

        Beep beeper = new Beep(300, 3);

        while(true) {
            // Storing all the status variables indicating the success of each step
            boolean screenshot = false;
            boolean templateMatch = false;

            // Taking the screenshot
            ScreenShot ss = new ScreenShot();
            screenshot = ss.captureScreen();

            if (!screenshot) {
                System.exit(-1);
            }

            // Performing template matching
            TemplateMatch prayer = new TemplateMatch();
            templateMatch = prayer.filterImage();

            if (!templateMatch) {
                System.exit(-1);
            }

            // Performing OCR to extract the raw text
            Recogniser recogniser = new Recogniser();
            String rawPrayerStr = recogniser.getPrayerString();
            if (rawPrayerStr == null || rawPrayerStr.length() < 1) {
                rawPrayerStr = "0/960";
            }

            // Using the parser to extract the prayer level from the raw prayer string.
            Parser parser = new Parser(rawPrayerStr);
            int prayerPoints = parser.getPrayerPts();

            // Actioner: What to do after learning the prayer points
            if (prayerPoints < 450) {
                beeper.run();
            }

            // Print out the prayer points
            System.out.println("Prayer points: " + prayerPoints);
        }
    }
}
