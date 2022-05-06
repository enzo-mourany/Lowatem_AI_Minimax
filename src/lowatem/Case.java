package lowatem;

/**
 * Case du plateau.
 * 
 * VOUS NE DEVEZ PAS MODIFIER CE FICHIER.
 */
public final class Case {

    /**
     * Caractère pour indiquer une unité noire (dans l'attribut couleurUnite).
     */
    public final static char CAR_NOIR = 'N';

    /**
     * Caractère pour indiquer une unité rouge (dans l'attribut couleurUnite).
     */
    public final static char CAR_ROUGE = 'R';

    /**
     * Caractère indiquant la nature "Terre" de la case (dans l'attribut nature).
     */
    public final static char CAR_TERRE = 'T';

    /**
     * Caractère pour indiquer une case sans unité (dans l'attribut typeUnite).
     */
    public final static char CAR_VIDE = ' ';

    /**
     * Caractère pour indiquer une case avec une unité de soldats (dans
     * l'attribut typeUnite).
     */
    public final static char CAR_SOLDATS = 'S';

    /**
     * Indique le type d'unité se trouvant sur cette case. 
     * L'absence d'unité est indiquée par le caractère Utils.CAR_VIDE.
     */
    char typeUnite;
    
    /**
     * Indique la couleur de l'unité sur cette case (s'il y en a une).
     * Convention : 'R' pour rouge, 'N' pour noir.
     */
    char couleurUnite;
    
    /**
     * Points de vie de l'unité, le cas échéant.
     */
    int pointsDeVie;
    
    /**
     * Altitude d'une case.
     */
    int altitude;
    
    /**
     * Nature d'une case.
     */
    char nature;

    /**
     * Constructeur d'une case.
     * 
     * @param unTypeUnite type d'unité sur la case
     * @param uneCouleurUnite couleur de l'unité sur cette case
     * @param desPointsDeVie points de vie de l'unité sur cette case
     * @param uneAltitude altitude de la case
     * @param uneNature nature de la case
     */
    public Case(char unTypeUnite, char uneCouleurUnite, int desPointsDeVie,
            int uneAltitude, char uneNature) {
        this.typeUnite = unTypeUnite;
        this.couleurUnite = uneCouleurUnite;
        this.pointsDeVie = desPointsDeVie;
        this.altitude = uneAltitude;
        this.nature = uneNature;
    }
    
    /**
     * Indique si une unité est rpésente sur cette case (c'est-à-dire si elle 
     * n'est pas vide).
     * 
     * @return vrai ssi une unité est présente
     */
    boolean unitePresente() {
        return this.typeUnite != CAR_VIDE;
    }
}
