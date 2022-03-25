package lowatem;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests unitaires de la classe ActionsPossibles.
 */
public class ActionsPossiblesTest {
    
    @Test
    public void testEnleverPointsDeVie() {
        assertEquals("aEDaF", ActionsPossibles.enleverPointsDeVie("aEDaF,3,4"));
        assertEquals("aEDaFAnN", ActionsPossibles.enleverPointsDeVie("aEDaFAnN,3,4"));
    }

    /**
     * Test de la fonction nettoyerTableau().
     */
    @Test
    public void testNettoyerTableau() {

        String tab[], tabNettoye[];

        // tableau de taille 0
        tab = new String[0];
        tabNettoye = ActionsPossibles.nettoyerTableau(tab);
        assertEquals(0, tabNettoye.length);

        // tableau de taille 1 avec 1 élément
        tab = new String[1];
        tab[0] = "coucou";
        tabNettoye = ActionsPossibles.nettoyerTableau(tab);
        assertEquals(1, tabNettoye.length);
        assertEquals("coucou", tabNettoye[0]);

        // tableau de taille 1 avec 0 élément (null)
        tab = new String[1];
        tab[0] = null;
        tabNettoye = ActionsPossibles.nettoyerTableau(tab);
        assertEquals(0, tabNettoye.length);

        // tableau de taille 1 avec 0 élément ("")
        tab = new String[1];
        tab[0] = "";
        tabNettoye = ActionsPossibles.nettoyerTableau(tab);
        assertEquals(0, tabNettoye.length);

        // tableau de taille 2 avec 1 élément ("")
        tab = new String[2];
        tab[0] = "";
        tab[1] = "hello";
        tabNettoye = ActionsPossibles.nettoyerTableau(tab);
        assertEquals(1, tabNettoye.length);
        assertEquals("hello", tabNettoye[0]);

        // un cas plus complet
        tab = new String[7];
        tab[0] = "";
        tab[1] = "hello";
        tab[2] = null;
        tab[3] = "";
        tab[4] = "hello";
        tab[5] = "";
        tab[6] = null;
        tabNettoye = ActionsPossibles.nettoyerTableau(tab);
        assertEquals(2, tabNettoye.length);
        assertEquals("hello", tabNettoye[0]);
        assertEquals("hello", tabNettoye[1]);
    }

}
