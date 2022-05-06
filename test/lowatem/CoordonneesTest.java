package lowatem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests unitaires de la classe Coordonnees.
 */
public class CoordonneesTest {

    @Test
    public void testConstructeurInt() {
        Coordonnees c = new Coordonnees(1, 12);
        assertEquals(1, c.ligne);
        assertEquals(12, c.colonne);
        c = new Coordonnees(0, 0);
        assertEquals(0, c.ligne);
        assertEquals(0, c.colonne);
    }

    @Test
    public void testDepuisChar() {
        Coordonnees c = Coordonnees.depuisCars('c', 'A');
        assertEquals(2, c.ligne);
        assertEquals(0, c.colonne);
    }

    @Test
    public void testSuivantes() {
        Coordonnees c = new Coordonnees(8, 3);
        assertEquals(new Coordonnees(9, 3), c.suivantes(Direction.SUD));
        assertEquals(new Coordonnees(7, 3), c.suivantes(Direction.NORD));
        assertEquals(new Coordonnees(8, 4), c.suivantes(Direction.EST));
        assertEquals(new Coordonnees(8, 2), c.suivantes(Direction.OUEST));
    }

    @Test
    public void testEstDansPlateau() {
        assertTrue((new Coordonnees(0, 0)).estDansPlateau());
        assertTrue((new Coordonnees(13, 13)).estDansPlateau());
        assertTrue((new Coordonnees(0, 1)).estDansPlateau());
        assertFalse((new Coordonnees(-1, 1)).estDansPlateau());
        assertTrue((new Coordonnees(7, 13)).estDansPlateau());
        assertFalse((new Coordonnees(7, 14)).estDansPlateau());
        assertTrue((new Coordonnees(13, 0)).estDansPlateau());
        assertFalse((new Coordonnees(14, 0)).estDansPlateau());
        assertTrue((new Coordonnees(7, 0)).estDansPlateau());
        assertFalse((new Coordonnees(7, -1)).estDansPlateau());
    }

    /**
     * Test de carLigne pour les valeurs admises.
     */
    @Test
    public void testCarLigne() {
        assertEquals('a', (new Coordonnees(0, 3)).carLigne());
        assertEquals('b', (new Coordonnees(1, 32)).carLigne());
        assertEquals('n', (new Coordonnees(13, 0)).carLigne());
    }

    /**
     * Test de carLigne pour un argument trop petit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarLigneException1() {
        (new Coordonnees(-1, 0)).carLigne();
    }

    /**
     * Test de carLigne pour un argument trop grand.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarLigneException2() {
        (new Coordonnees(14, 0)).carLigne();
    }

    /**
     * Test de carColonne pour les valeurs admises.
     */
    @Test
    public void testCarColonne() {
        assertEquals('A', (new Coordonnees(7, 0)).carColonne());
        assertEquals('B', (new Coordonnees(0, 1)).carColonne());
        assertEquals('N', (new Coordonnees(5, 13)).carColonne());
    }

    /**
     * Test de carColonne pour un argument trop petit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarColonneException1() {
        (new Coordonnees(0, -1)).carColonne();
    }

    /**
     * Test de carColonne pour un argument trop grand.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarColonneException2() {
        (new Coordonnees(0, 14)).carColonne();
    }

    /**
     * Test de carLigneVersNum pour les valeurs admises.
     */
    @Test
    public void testCarLigneVersNum() {
        assertEquals(0, Coordonnees.carLigneVersNum('a'));
        assertEquals(1, Coordonnees.carLigneVersNum('b'));
        assertEquals(13, Coordonnees.carLigneVersNum('n'));
    }

    /**
     * Test de carLigneVersNum pour un argument trop petit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarLigneVersNumException1() {
        final char c = Coordonnees.CAR_PREMIERE_LIGNE - 1;
        Coordonnees.carLigneVersNum(c);
    }

    /**
     * Test de carLigneVersNum pour un argument trop grand.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarLigneVersNumException2() {
        final char c = Coordonnees.CAR_PREMIERE_LIGNE + Coordonnees.NB_LIGNES;
        Coordonnees.carLigneVersNum(c);
    }

    /**
     * Test de carColonneVersNum pour les valeurs admises.
     */
    @Test
    public void testCarColonneVersNum() {
        assertEquals(0, Coordonnees.carColonneVersNum('A'));
        assertEquals(1, Coordonnees.carColonneVersNum('B'));
        assertEquals(13, Coordonnees.carColonneVersNum('N'));
    }

    /**
     * Test de carColonneVersNum pour un argument trop petit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarColonneVersNumException1() {
        final char c = Coordonnees.CAR_PREMIERE_COLONNE - 1;
        Coordonnees.carColonneVersNum(c);
    }

    /**
     * Test de carColonneVersNum pour un argument trop grand.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCarColonneVersNumException2() {
        final char c = Coordonnees.CAR_PREMIERE_COLONNE + Coordonnees.NB_COLONNES;
        Coordonnees.carColonneVersNum(c);
    }

    /**
     * Test de memeLigne().
     */
    @Test
    public void testMemeLigne() {
        Coordonnees c = new Coordonnees(7, 2);
        assertTrue(c.memeLigne(new Coordonnees(7, 0)));
        assertTrue(c.memeLigne(new Coordonnees(7, 1)));
        assertTrue(c.memeLigne(new Coordonnees(7, 7)));
        assertTrue(c.memeLigne(new Coordonnees(7, 12)));
        assertFalse(c.memeLigne(new Coordonnees(8, 2)));
    }

    /**
     * Test de memeColonne().
     */
    @Test
    public void testMemeColonne() {
        Coordonnees c = new Coordonnees(7, 2);
        assertTrue(c.memeColonne(new Coordonnees(0, 2)));
        assertTrue(c.memeColonne(new Coordonnees(1, 2)));
        assertTrue(c.memeColonne(new Coordonnees(7, 2)));
        assertTrue(c.memeColonne(new Coordonnees(13, 2)));
        assertFalse(c.memeColonne(new Coordonnees(7, 1)));
    }
}
