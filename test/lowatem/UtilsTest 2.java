package lowatem;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests sur la classe Utils.
 */
public class UtilsTest {

    /**
     * Test de la fonction caseDepuisCodage.
     */
    @Test
    public void testCaseDepuisCodage() {
        Case laCase;
        // case vide
        laCase = Utils.caseDepuisCodage("---", "   ");
        assertEquals(Case.CAR_TERRE, laCase.nature);
        assertEquals(0, laCase.altitude);
        assertEquals(Case.CAR_VIDE, laCase.typeUnite);
        // une unité de soldats rouges
        laCase = Utils.caseDepuisCodage("---", "SR4");
        assertEquals(Case.CAR_TERRE, laCase.nature);
        assertEquals(0, laCase.altitude);
        assertEquals(Case.CAR_SOLDATS, laCase.typeUnite);
        assertEquals(Case.CAR_ROUGE, laCase.couleurUnite);
        assertEquals(4, laCase.pointsDeVie);
        // une unité de soldats noirs
        laCase = Utils.caseDepuisCodage("---", "SN9");
        assertEquals(Case.CAR_TERRE, laCase.nature);
        assertEquals(0, laCase.altitude);
        assertEquals(Case.CAR_SOLDATS, laCase.typeUnite);
        assertEquals(Case.CAR_NOIR, laCase.couleurUnite);
        assertEquals(9, laCase.pointsDeVie);
    }

    /**
     * Test de la fonction plateauDepuisTexte().
     */
    @Test
    public void testPlateauDepuisTexte() {
        Case[][] plateau = Utils.plateauDepuisTexte(PLATEAU1);
        Case laCase;
        // une case avec une unité noire
        laCase = plateau[0][0];
        assertEquals(Case.CAR_TERRE, laCase.nature);
        assertEquals(0, laCase.altitude);
        assertEquals(Case.CAR_SOLDATS, laCase.typeUnite);
        assertEquals(Case.CAR_NOIR, laCase.couleurUnite);
        assertEquals(4, laCase.pointsDeVie);
        // une case avec une unité rouge
        laCase = plateau[13][13];
        assertEquals(Case.CAR_TERRE, laCase.nature);
        assertEquals(0, laCase.altitude);
        assertEquals(Case.CAR_SOLDATS, laCase.typeUnite);
        assertEquals(Case.CAR_ROUGE, laCase.couleurUnite);
        assertEquals(1, laCase.pointsDeVie);
        // une case vide
        laCase = plateau[0][1];
        assertEquals(Case.CAR_TERRE, laCase.nature);
        assertEquals(0, laCase.altitude);
        assertEquals(Case.CAR_VIDE, laCase.typeUnite);
    }

    final String PLATEAU1
            = "   A   B   C   D   E   F   G   H   I   J   K   L   M   N \n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "a|SN4|   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "b|SR9|   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "c|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "d|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "e|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "f|   |SR3|   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "g|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "h|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "i|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "j|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "k|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "l|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "m|   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n"
            + "n|   |   |   |   |   |   |   |   |   |   |   |   |   |SR1|\n"
            + " +---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
}
