package lowatem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests unitaires de l'énumération Direction.
 */
public class DirectionTest {
    
    @Test
    public void testToutes() {
        boolean nord = false, sud = false, est = false, ouest = false;
        Direction[] toutes = Direction.toutes();
        assertEquals(4, toutes.length);
        for (int i = 0; i < 4; i++) {
            switch (toutes[i]) {
                case NORD:
                    nord = true;
                    break;
                case SUD:
                    sud = true;
                    break;
                case EST:
                    est = true;
                    break;
                case OUEST:
                    ouest = true;
                    break;
            }
        }
        assertTrue( nord && sud && est && ouest);
    }
    
    @Test
    public void testMvtHoriz() {
        assertEquals(0, Direction.NORD.mvtHoriz());
        assertEquals(0, Direction.SUD.mvtHoriz());
        assertEquals(1, Direction.EST.mvtHoriz());
        assertEquals(-1, Direction.OUEST.mvtHoriz());
    }
    
    @Test
    public void testMvtVertic() {
        assertEquals(-1, Direction.NORD.mvtVertic());
        assertEquals(1, Direction.SUD.mvtVertic());
        assertEquals(0, Direction.EST.mvtVertic());
        assertEquals(0, Direction.OUEST.mvtVertic());
    }
}
