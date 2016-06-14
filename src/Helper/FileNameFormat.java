package Helper;

/**
 * Created by ArghaWin10 on 14/06/2016.
 */
public class FileNameFormat {

    public int getPrayerValueFromFileName(String filename) {
        String[] fileNameParts = filename.split("_");
        String[] prayerValue = fileNameParts[1].split("\\.");
        return Integer.parseInt(prayerValue[0]);
    }

    public String getPrayerStringFromFileName(String filename) {
        return "" + getPrayerValueFromFileName(filename);
    }

}
