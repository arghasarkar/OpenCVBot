package Tests.Unit.Util.Audio;

import Util.Audio.Beep;
import org.junit.Test;
import static org.junit.Assert.*;

public class BeepTest {
    /**
     * This test causes the Beep class to beep 5 times. If the Beep is not heard, then there is something wrong.
     * @throws Exception
     */
    @Test
    public void beep() throws Exception {
        Beep beep = new Beep(500, 5);
        beep.run();

        assertEquals(true, true);
    }

}