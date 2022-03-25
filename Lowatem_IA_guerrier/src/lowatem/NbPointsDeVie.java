package lowatem;

/**
 * Nombre de points de vie, pour chaque joueur.
 */
public class NbPointsDeVie {
    
    /**
     * Nombre de points de vie pour le joueur rouge.
     */
    int nbPvRouge;
    
    /**
     * Nombre de points de vie pour le joueur noir.
     */
    int nbPvNoir;
    
    /**
     * Constructeur vide.
     */
    NbPointsDeVie() {
        nbPvRouge = 0;
        nbPvNoir = 0;
    }
    
    /**
     * Constructeur à partir de valeurs connues.
     * 
     * @param pvRouge nombre de points de vie du joueur rouge
     * @param pvNoir nombre de points de vie du joueur noir
     */
    NbPointsDeVie(int pvRouge, int pvNoir) {
        nbPvRouge = pvRouge;
        nbPvNoir = pvNoir;
    }

    /**
     * Constructeur par copie : permet d'obtenir une copie de cet objet.
     * 
     * @param nbPv l'objet à copier
     */
    NbPointsDeVie(NbPointsDeVie nbPv) {
        this.nbPvRouge = nbPv.nbPvRouge;
        this.nbPvNoir = nbPv.nbPvNoir;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.nbPvRouge;
        hash = 89 * hash + this.nbPvNoir;
        return hash;
    }

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
        final NbPointsDeVie other = (NbPointsDeVie) obj;
        if (this.nbPvRouge != other.nbPvRouge) {
            return false;
        }
        if (this.nbPvNoir != other.nbPvNoir) {
            return false;
        }
        return true;
    }
}
