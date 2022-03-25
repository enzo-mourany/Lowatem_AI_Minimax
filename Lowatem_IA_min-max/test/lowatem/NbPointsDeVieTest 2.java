package lowatem;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests unitaires de la classe NbPointsDeVie.
 */
public class NbPointsDeVieTest {
    
    @Test
    public void testNbPointsDeVie() {
        // constructeur par défaut
        NbPointsDeVie nbPv0 = new NbPointsDeVie();
        assertEquals(0, nbPv0.nbPvRouge);
        assertEquals(0, nbPv0.nbPvNoir);
        // constructeur paramétré
        NbPointsDeVie nbPv1 = new NbPointsDeVie(17, 48);
        assertEquals(17, nbPv1.nbPvRouge);
        assertEquals(48, nbPv1.nbPvNoir);
        // constructeur par copie
        NbPointsDeVie nbPv2 = new NbPointsDeVie(nbPv1);
        assertEquals(17, nbPv2.nbPvRouge);
        assertEquals(48, nbPv2.nbPvNoir);
    }
}
