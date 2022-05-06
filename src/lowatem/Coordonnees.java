package lowatem;

/**
 * Coordonnées d'une case du plateau.
 * 
 * VOUS NE DEVEZ PAS MODIFIER CE FICHIER.
 */
class Coordonnees {

    /**
     * Numéro de la ligne.
     */
    int ligne;
    
    /**
     * Numéro de la colonne.
     */
    int colonne;

    /**
     * Nombre de lignes du plateau.
     */
    static final int NB_LIGNES = 14;
    /**
     * Nombre de colonnes du plateau.
     */
    static final int NB_COLONNES = 14;
    
    /**
     * Caractère de la première ligne.
     */
    static final char CAR_PREMIERE_LIGNE = 'a';

    /**
     * Caractère de la première colonne.
     */
    static final char CAR_PREMIERE_COLONNE = 'A';

    /**
     * Constructeur prenant les numéros de ligne/colonne en paramètre.
     * 
     * @param numLigne numéro de la ligne
     * @param numColonne numéro de la colonne
     */
    Coordonnees(int numLigne, int numColonne) {
        ligne = numLigne;
        colonne = numColonne;
    }

    /**
     * Retourne un objet Coordonnees à partir des caractères des coordonnées 
     * donnés en paramètre.
     * 
     * Remarque : on aurait pu faire un constructeur Coordonnees(char, char),
     * mais la conversion implicite entre int et char sème la pagaille.
     * 
     * @param carLigne caractère de la ligne
     * @param carColonne caractère de la colonne
     * @return l'objet Coordonnees correspondant à ces lignes/colonnes
     */
    static Coordonnees depuisCars(char carLigne, char carColonne) {
        return new Coordonnees(
                carLigneVersNum(carLigne), carColonneVersNum(carColonne));
    }

    /**
     * Renvoie les coordonnées de la case suivante, en suivant une direction
     * donnée.
     * 
     * @param d la direction à suivre
     * @return les coordonnées de la case suivante
     */
    Coordonnees suivantes(Direction d) {
        // TODO introduire BUG NIVEAU 1 :
        // return new Coordonnees(ligne + d.mvtHoriz(), colonne + d.mvtVertic());
        return new Coordonnees(ligne + d.mvtVertic(), colonne + d.mvtHoriz());
    }
    
    /**
     * Indique si ces coordonnées sont dans le plateau.
     * @return vrai ssi ces coordonnées sont dans le plateau
     */
    boolean estDansPlateau() {
        return 0 <= ligne && ligne < NB_LIGNES 
                && 0 <= colonne && colonne < NB_COLONNES;
    }
    
    /**
     * Renvoie le caractère correspondant à la ligne.
     * 
     * @return le caractère correspondant à la ligne
     */
    char carLigne() {
        if ((ligne < 0) || (ligne >= NB_LIGNES)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carLigne, avec ligne = " + ligne
                    + ". Les valeurs autorisées sont les entiers entre "
                    + "0 et " + (NB_LIGNES - 1));
        }
        return (char) (CAR_PREMIERE_LIGNE + ligne);
    }

    /**
     * Renvoie le caractère correspondant à la colonne.
     * 
     * @return le caractère correspondant à la colonne
     */
    char carColonne() {
        if ((colonne < 0) || (colonne >= NB_COLONNES)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carColonne, avec colonne = " + colonne
                    + ". Les valeurs autorisées sont les entiers entre "
                    + "0 et " + (NB_COLONNES - 1));
        }
        return (char) (CAR_PREMIERE_COLONNE + colonne);
    }

    /**
     * Convertit un nom de ligne (par exemple 'c') en numéro de ligne (ici 2).
     *
     * @param nomLigne le nom de ligne à convertir
     * @return le numéro de cette ligne
     */
    static int carLigneVersNum(char nomLigne) {
        final char carMin = CAR_PREMIERE_LIGNE;
        final char carMax = CAR_PREMIERE_LIGNE + NB_LIGNES - 1;
        if ((nomLigne < carMin) || (nomLigne > carMax)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carVersNum, avec car = " + nomLigne
                    + ". Les valeurs autorisées sont les caractères entre "
                    + carMin + " et " + carMax + ".");
        }
        return nomLigne - CAR_PREMIERE_LIGNE;
    }

    /**
     * Convertit un nom de colonnes (par exemple 'C') en numéro de colonne (ici
     * 2).
     *
     * @param nomColonne le nom de colonne à convertir
     * @return le numéro de cette colonne
     */
    static int carColonneVersNum(char nomColonne) {
        final char carMin = CAR_PREMIERE_COLONNE;
        final char carMax = CAR_PREMIERE_COLONNE + NB_COLONNES - 1;
        if ((nomColonne < carMin) || (nomColonne > carMax)) {
            throw new IllegalArgumentException(
                    "Appel incorrect à carVersNum, avec car = " + nomColonne
                    + ". Les valeurs autorisées sont les caractères entre "
                    + carMin + " et " + carMax + ".");
        }
        return nomColonne - CAR_PREMIERE_COLONNE;
    }
    
    /**
     * Indique si une autre case, passée en paramètre, est sur la même ligne.
     * 
     * @param autre les coordonnées de l'autre case
     * @return vrai ssi les cases sont sur la même ligne
     */
    boolean memeLigne(Coordonnees autre) {
        return this.ligne == autre.ligne;
    }

    /**
     * Indique si une autre case, passée en paramètre, est sur la même colonne.
     * 
     * @param autre les coordonnées de l'autre case
     * @return vrai ssi les cases sont sur la même colonne
     */
    boolean memeColonne(Coordonnees autre) {
        return this.colonne == autre.colonne;
    }

    /**
     * Test d'égalité entre coordonnées.
     * 
     * @param obj les coordonnées avec lesquelles comparer ces coordonnées
     * @return vrai ssi les coordonnées représentent la même case
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordonnees other = (Coordonnees) obj;
        if (this.ligne != other.ligne) {
            return false;
        }
        if (this.colonne != other.colonne) {
            return false;
        }
        return true;
    }

    /**
     * Code de hachage, si besoin (pas nécessaire).
     * 
     * @return code de hachage de ces coordonnées
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.ligne;
        hash = 37 * hash + this.colonne;
        return hash;
    }
}
