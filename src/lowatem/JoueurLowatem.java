package lowatem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Joueur implémentant les actions possibles à partir d'un plateau, pour un
 * niveau donné.
 */
public class JoueurLowatem implements IJoueurLowatem {

    /**
     * Cette méthode renvoie, pour un plateau donné et un joueur donné, toutes
     * les actions possibles pour ce joueur.
     *
     * @param plateau le plateau considéré
     * @param couleurJoueur couleur du joueur
     * @param niveau le niveau de la partie à jouer
     * @return l'ensemble des actions possibles
     */
    @Override
    public String[] actionsPossibles(Case[][] plateau, char couleurJoueur, int niveau) {
        // afficher l'heure de lancement
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("actionsPossibles : lancement le " + format.format(new Date()));
        // se préparer à stocker les actions possibles
        ActionsPossibles actions = new ActionsPossibles();
        // calculer les points de vie sur le plateau initial
        NbPointsDeVie nbPv = nbPointsDeVie(plateau);
        // détection s'il y a une unité sur la case
        for (int i = 0; i < Coordonnees.NB_COLONNES; i++){
            for (int j = 0; j < Coordonnees.NB_LIGNES; j++){
                if (plateau[i][j].unitePresente() && plateau[i][j].couleurUnite == couleurJoueur){
                    // déplacements possibles depuis la case où est détecté l'unité
                    ajoutDeplDepuis(new Coordonnees(i,j), actions, nbPv, plateau, couleurJoueur);
                }
            }
        }
        System.out.println("actionsPossibles : fin");
        return actions.nettoyer();
    }
    
    /**
     * Nombre de points de vie de chaque joueur sur le plateau.
     *
     * @param plateau le plateau
     * @return le nombre de pions de cette couleur sur le plateau
     */
    static NbPointsDeVie nbPointsDeVie(Case[][] plateau) {
        NbPointsDeVie nbPV = new NbPointsDeVie();
        for (int i = 0; i < Coordonnees.NB_COLONNES; i++){
            for (int j = 0; j < Coordonnees.NB_LIGNES; j++){
                if (plateau[i][j].couleurUnite == 'R'){
                    nbPV.nbPvRouge += plateau[i][j].pointsDeVie;
                }
                if (plateau[i][j].couleurUnite == 'N'){
                    nbPV.nbPvNoir += plateau[i][j].pointsDeVie;
                }
            }
        }
        return nbPV;
    }

    /**
     * Ajouter tous les déplacements depuis une case donnée.
     *
     * @param coord coordonnées de la case d'origine
     * @param actions ensemble des actions possibles, à compléter
     * @param nbPv nombre de points de vie de chaque joueur sur le plateau
     * initial
     */
    void ajoutDeplDepuis(Coordonnees coord, ActionsPossibles actions, NbPointsDeVie nbPv, Case[][] plateau, char couleurJoueur) {
        // on part dans chacune des 4 directions
        for (Direction dir : Direction.toutes()) {
            ajoutDeplDansDirection(dir, coord, actions, nbPv, plateau, couleurJoueur);
        }
        // on ajoute le déplacement "sur place"
        ajoutDepl(coord, coord, actions, nbPv, plateau, couleurJoueur);
    }

    /**
     * Ajouter tous les déplacements depuis une case donnée, dans une direction
     * donnée.
     *
     * @param dir direction à suivre
     * @param src coordonnées de la case d'origine
     * @param actions ensemble des actions possibles, à compléter
     * @param nbPv nombre de points de vie de chaque joueur sur le plateauç
     * @param plateau plateau du jeu
     * initial
     */
    void ajoutDeplDansDirection(Direction dir, Coordonnees src,
            ActionsPossibles actions, NbPointsDeVie nbPv, Case[][] plateau, char couleurJoueur) {
        Coordonnees dst = src.suivantes(dir);
        while (dst.estDansPlateau()) {
            if (!plateau[dst.ligne][dst.colonne].unitePresente()){
                ajoutDepl(src, dst, actions, nbPv, plateau, couleurJoueur);
            }
            dst = dst.suivantes(dir);
        }
    }

    /**
     * Ajout d'une action de déplacement dans l'ensemble des actions possibles.
     *
     * @param src coordonnées de la case à l'origine du déplacement
     * @param dst coordonnées de la case destination du déplacement
     * @param actions l'ensemble des actions possibles (en construction)
     * @param nbPv nombre de points de vie de chaque joueur sur le plateau
     * initial
     */
    void ajoutDepl(Coordonnees src, Coordonnees dst, ActionsPossibles actions,
            NbPointsDeVie nbPv, Case[][] plateau, char couleurJoueur) {
        actions.ajouterAction(chaineActionDepl(src, dst, nbPv));
        attaquePossible(src, dst, actions, nbPv, plateau, couleurJoueur);
    }
    
    void attaquePossible(Coordonnees src, Coordonnees dst, ActionsPossibles actions,
            NbPointsDeVie nbPv, Case[][] plateau, char couleurJoueur){
        for (Direction dir : Direction.toutes()){
            Coordonnees visee = dst.suivantes(dir);
            if (visee.estDansPlateau() && plateau[visee.ligne][visee.colonne].couleurUnite != couleurJoueur
                    && plateau[visee.ligne][visee.colonne].unitePresente()){
                actions.ajouterAction(chaineActionAttaque(src, dst, visee, attaque(src, visee, nbPv, plateau)));
            }
        }
    }

    NbPointsDeVie attaque(Coordonnees src, Coordonnees visee, NbPointsDeVie nbPv, Case[][] plateau){
        NbPointsDeVie newNbPv = new NbPointsDeVie();
        if (plateau[src.ligne][src.colonne].couleurUnite == 'R'){
            newNbPv.nbPvRouge = nbPv.nbPvRouge - plateau[src.ligne][src.colonne].pointsDeVie + newNbPvAttaquant(src, visee, plateau);
            newNbPv.nbPvNoir = nbPv.nbPvNoir - plateau[visee.ligne][visee.colonne].pointsDeVie + newNbPvAttaque(src, visee, plateau);
        } else {
            newNbPv.nbPvRouge = nbPv.nbPvRouge - plateau[visee.ligne][visee.colonne].pointsDeVie + newNbPvAttaque(src, visee, plateau);
            newNbPv.nbPvNoir = nbPv.nbPvNoir - plateau[src.ligne][src.colonne].pointsDeVie + newNbPvAttaquant(src, visee, plateau);
        }
        return newNbPv;
    }
    
    int newNbPvAttaquant(Coordonnees src, Coordonnees visee, Case[][] plateau){
        int pvAttaquant = plateau[src.ligne][src.colonne].pointsDeVie - 2 - (int)((plateau[visee.ligne][visee.colonne].pointsDeVie-5)/2);
        if (pvAttaquant < 0){
            pvAttaquant = 0;
        }
        return pvAttaquant;
    }
    
    int newNbPvAttaque(Coordonnees src, Coordonnees visee, Case[][] plateau){
        int pvAttaque = plateau[visee.ligne][visee.colonne].pointsDeVie - 4 - (int)((plateau[src.ligne][src.colonne].pointsDeVie-5)/2);
        if (pvAttaque < 0){
            pvAttaque = 0;
        }
        return pvAttaque;
    }
    
    /**
     * Chaîne de caractères correspondant à une action-mesure de déplacement.
     *
     * @param src coordonnées de la case à l'origine du déplacement
     * @param dst coordonnées de la case destination du déplacement
     * @param nbPv nombre de points de vie de chaque joueur après l'action
     * @return la chaîne codant cette action-mesure
     */
    static String chaineActionDepl(Coordonnees src, Coordonnees dst, NbPointsDeVie nbPv) {
        return "" + src.carLigne() + src.carColonne()
                + "D" + dst.carLigne() + dst.carColonne()
                + "," + nbPv.nbPvRouge + "," + nbPv.nbPvNoir;
    }
    
    /**
     * Chaîne de caractères correspondant à une action-mesure de déplacement.
     *
     * @param src coordonnées de la case à l'origine du déplacement
     * @param dst coordonnées de la case destination du déplacement
     * @param nbPv nombre de points de vie de chaque joueur après l'action
     * @return la chaîne codant cette action-mesure
     */
    static String chaineActionAttaque(Coordonnees src, Coordonnees dst, Coordonnees visee, NbPointsDeVie nbPv) {
        return "" + src.carLigne() + src.carColonne()
                + "D" + dst.carLigne() + dst.carColonne()
                + "A" + visee.carLigne() + visee.carColonne()
                + "," + nbPv.nbPvRouge + "," + nbPv.nbPvNoir;
    }
}
