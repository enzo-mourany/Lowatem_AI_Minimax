package lowatem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests unitaires de la classe Case.
 */
public class CaseTest {
    
    @Test
    public void testCase() {
        Case laCase = new Case(Case.CAR_SOLDATS, Case.CAR_NOIR, 3, 0, Case.CAR_TERRE);
        assertEquals(Case.CAR_SOLDATS, laCase.typeUnite);
        assertEquals(Case.CAR_NOIR, laCase.couleurUnite);
        assertEquals(3, laCase.pointsDeVie);
        assertEquals(0, laCase.altitude);
        assertEquals(Case.CAR_TERRE, laCase.nature);
    }
    
    @Test
    public void testUnitePresente() {
        Case case1 = new Case(Case.CAR_SOLDATS, Case.CAR_NOIR, 3, 0, Case.CAR_TERRE);
        assertTrue(case1.unitePresente());
        Case case2 = new Case(Case.CAR_VIDE, Case.CAR_NOIR, 3, 0, Case.CAR_TERRE);
        assertFalse(case2.unitePresente());
    }
}
