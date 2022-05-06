package lowatem;

/**
 * Interface que vous devez implémenter correctement pour valider un niveau.
 * 
 * VOUS NE DEVEZ PAS MODIFIER CE FICHIER.
 */
public interface IJoueurLowatem {
    
    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, 
     * toutes les actions possibles pour ce joueur.
     * @param plateau le plateau considéré
     * @param couleurJoueur couleur du joueur ('R' ou 'N')
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    String[] actionsPossibles(Case[][] plateau, char couleurJoueur, int niveau);
}
