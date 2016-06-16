package OCR.Util.ScreenReader.Prayer;

public class Parser {

    private String prayerStr = "";
    private int maxPrayerPts = 990;
    private int currentPrayerPts = 0;

    public Parser(String prayerStr) {
        this.prayerStr = prayerStr;
    }

    public int getPrayerLevel() {
        Parse();
        return maxPrayerPts / 10;
    }

    public int getMaxPrayerPts() {
        Parse();
        return maxPrayerPts;
    }

    public int getPrayerPts() {
        Parse();
        return currentPrayerPts;
    }

    private void Parse() {
        String[] prayerVals = prayerStr.split("/");
        CharacterMap cm = new CharacterMap(prayerVals[0]);
        currentPrayerPts = cm.map();

        maxPrayerPts = cm.map(prayerVals[1]);
    }

    class CharacterMap {

        private int prayerPts = 0;

        CharacterMap(String str) {
            map(str);
        }

        public int map(String str) {
            try {
                // Trying to parse the first
                prayerPts = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                String[] charArray = str.split("");
                String parsedInt = "";

                for (int i = 0; i < charArray.length; i++) {
                    parsedInt += "" + CharMap(charArray[i]);
                }

                try {
                    // If the replacement hasn't worked, cover it up via a try and catch
                    prayerPts = Integer.parseInt(parsedInt);
                } catch (NumberFormatException ex) {
                    prayerPts = 0;
                }
            }
            return prayerPts;
        }
        public int map() {
            return prayerPts;
        }

        private int CharMap(String str) {
            try {
                // Checking if the value given is already an integer or not
                int val = Integer.parseInt(str);
                return val;
            } catch (NumberFormatException e) {}

            if (str.equals("D")) {
                return 0;
            }
            if (str.equals("I") || str.equals("l") || str.equals("i")) {
                return 1;
            }
            if (str.equals("E")) {
                return 3;
            }
            if (str.equals("A")) {
                return 4;
            }
            if (str.equals("S")|| str.equals("s")) {
                return 5;
            }
            if (str.equals("Q")) {
                return 6;
            }
            if (str.equals("B")) {
                return 8;
            }
            if (str.equals("q")) {
                return 9;
            }

            return 0;
        }
    }

}

