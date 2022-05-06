package lowatem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Votre IA pour le jeu Lowatem, au niveau 6.
 */
public class IALowatem {

    /**
     * Hôte du grand ordonnateur.
     */
    String hote = null;

    /**
     * Port du grand ordonnateur.
     */
    int port = -1;

    /**
     * Couleur de votre joueur (IA) : 'R'ouge ou 'N'oir.
     */
    final char couleur;

    /**
     * Interface pour le protocole du grand ordonnateur.
     */
    TcpGrandOrdonnateur grandOrdo = null;

    /**
     * Taille du plateau.
     */
    static final int TAILLE = 14;

    /**
     * Nombre maximal de tours de jeu.
     */
    static final int NB_TOURS_JEU_MAX = 40;

    /**
     * Constructeur.
     *
     * @param hote Hôte.
     * @param port Port.
     * @param uneCouleur Couleur de ce joueur
     */
    public IALowatem(String hote, int port, char uneCouleur) {
        this.hote = hote;
        this.port = port;
        this.grandOrdo = new TcpGrandOrdonnateur();
        this.couleur = uneCouleur;
    }

    /**
     * Connexion au Grand Ordonnateur.
     *
     * @throws IOException exception sur les entrées/sorties
     */
    void connexion() throws IOException {
        System.out.print(
                "Connexion au Grand Ordonnateur : " + hote + " " + port + "...");
        System.out.flush();
        grandOrdo.connexion(hote, port);
        System.out.println(" ok.");
        System.out.flush();
    }

    /**
     * Boucle de jeu : envoi des actions que vous souhaitez jouer, et réception
     * des actions de l'adversaire.
     *
     * @throws IOException exception sur les entrées/sorties
     */
    void toursDeJeu() throws IOException {
        // paramètres
        System.out.println("Je suis le joueur " + couleur + ".");
        // le plateau initial
        System.out.println("Réception du plateau initial...");
        Case[][] plateau = grandOrdo.recevoirPlateauInitial();
        System.out.println("Plateau reçu.");
        // compteur de tours de jeu (entre 1 et 40)
        int nbToursJeu = 1;
        // la couleur du joueur courant (change à chaque tour de jeu)
        char couleurTourDeJeu = Case.CAR_ROUGE;
        // booléen pour détecter la fin du jeu
        boolean fin = false;

        while (!fin) {

            boolean disqualification = false;

            if (couleurTourDeJeu == couleur) {
                // à nous de jouer !
                jouer(plateau, nbToursJeu);
            } else {
                // à l'adversaire de jouer : on récupère son action
                System.out.println("Attente de réception action adversaire...");
                String actionAdversaire = grandOrdo.recevoirAction();
                System.out.println("Action adversaire reçue : " + actionAdversaire);
                if ("Z".equals(actionAdversaire)) {
                    System.out.println("L'adversaire est disqualifié.");
                    disqualification = true;
                } else if (!fin) {
                    System.out.println("L'adversaire joue : "
                            + actionAdversaire + ".");
                    mettreAJour(plateau, actionAdversaire);
                }
            }
            if (nbToursJeu == NB_TOURS_JEU_MAX || disqualification) {
                // fini
                fin = true;
            } else {
                // au suivant
                nbToursJeu++;
                couleurTourDeJeu = suivant(couleurTourDeJeu);
            }
        }
    }

    /**
     * Fonction exécutée lorsque c'est à notre tour de jouer. Cette fonction
     * envoie donc l'action choisie au serveur.
     *
     * @param plateau le plateau de jeu
     * @param nbToursJeu numéro du tour de jeu
     * @throws IOException exception sur les entrées / sorties
     */
    void jouer(Case[][] plateau, int nbToursJeu) throws IOException {
        //
        // tirage du déplacement permettant de porter le plus de dégats en en
        // perdant le moins
        //
        // on instancie votre implémentation du niveau 6
        JoueurLowatem joueurLowatem = new JoueurLowatem();
        String[] actionsPossibles = ActionsPossibles.nettoyerTableau(
                joueurLowatem.actionsPossibles(plateau, couleur, 6));
        // initialisation des variables
        char couleurJoueur = plateau[Coordonnees.carLigneVersNum(actionsPossibles[0].charAt(0))][Coordonnees.carColonneVersNum(actionsPossibles[0].charAt(1))].couleurUnite;
        // Nombre de pv d'un joueur au début du tour
        NbPointsDeVie nbPVavant = JoueurLowatem.nbPointsDeVie(plateau);
        int[] diffPv = new int[actionsPossibles.length];
        if (actionsPossibles.length > 0) {
            // parcours tous les déplacements du tableau
            for(int indice = 0; indice < actionsPossibles.length; indice ++){
                String actionPossible = actionsPossibles[indice];
                diffPv[indice] = ecartPv(actionPossible, couleurJoueur, nbPVavant);
            }
            String actionJouee = ActionsPossibles.enleverPointsDeVie(
                    actionsPossibles[choixIndice(diffPv)]);
            
            // jouer l'action
            System.out.println("On joue : " + actionJouee);
            grandOrdo.envoyerAction(actionJouee);
            mettreAJour(plateau, actionJouee);
            
            
        } else {
            // Problème : le serveur vous demande une action alors que vous n'en
            // trouvez plus...
            System.out.println("Aucun action trouvée : abandon...");
            grandOrdo.envoyerAction("ABANDON");
        }

    }
    
    /**
     * récupère le nombre de Pv rouges après l'action
     * 
     * @param action action possible
     * @return le nombre de Pv rouge
     */
    int recupPvRouge(String action){
        int pv;
        if (action.indexOf("A", 5) == -1){
            pv = Integer.parseInt(action.substring(6, action.indexOf(",", 6)));
        } else{
            pv = Integer.parseInt(action.substring(9, action.indexOf(",", 9)));
        }
        return pv;
    }
    
    /**
     * récupère le nombre de Pv noirs après l'action
     * 
     * @param action action possible
     * @return le nombre de Pv noir
     */
    int recupPvNoir(String action){
        int pv;
        if (action.indexOf("A", 5) == -1){
            pv = Integer.parseInt(action.substring(action.indexOf(",", 6)+1));
        } else {
            pv = Integer.parseInt(action.substring(action.indexOf(",", 9)+1));
        }
        return pv;
    }
    
    /**
     * Calcul la perte de Pv de chaque joueur en fonction de chaque déplacement
     * 
     * @param action l'action possible
     * @param couleurJ couleur du joueur dont c'est le tour
     * @param nbPvAvant le nombre de pv avant l'action
     * @return difference de Pv perdu
     */
    int ecartPv(String action, char couleurJ, NbPointsDeVie nbPvAvant){
        int diffPv;
        int diffPvR = nbPvAvant.nbPvRouge - recupPvRouge(action);
        int diffPvN = nbPvAvant.nbPvNoir - recupPvNoir(action);
        switch(couleurJ){
        // joueur rouge
            case 'R':
            // Ecart de pv entre le joueur noir et le joueur rouge
            diffPv = diffPvN - diffPvR;
            break;
            default:
            // Ecart de pv entre le joueur noir et le joueur rouge
            diffPv = diffPvR - diffPvN;
            break;
        }
        return diffPv;
    }
    
    /**
     * choisi l'indice qui fait perdre le plus de point de vie à l'adversaire en en perdant le moins
     * 
     * @param diffPv tableau des difference de pv pour chaque action possible
     * @return l'indice de la meilleur difference
     */
    int choixIndice(int[] diffPv){
        int indiceChoisi = 0;
        int meilleurDiff = -100;
        for(int rang = 0; rang < diffPv.length; rang ++){
            if (diffPv[rang] > meilleurDiff){
                meilleurDiff = diffPv[rang];
                indiceChoisi = rang;
            }
        }
        return indiceChoisi;
    }

    /**
     * Calcule la couleur du prochain joueur.
     *
     * @param couleurCourante la couleur du joueur courant
     * @return la couleur du prochain joueur
     */
    char suivant(char couleurCourante) {
        return couleurCourante == 'R' ? 'N' : 'R';
    }

    /**
     * Mettre à jour le plateau suite à une action, supposée valide. Vous ne
     * devez pas modifier cette méthode.
     *
     * @param plateau le plateau
     * @param action l'action à appliquer
     */
    static void mettreAJour(Case[][] plateau, String action) {
        // vérification des arguments
        if (plateau == null || action == null || action.length() < 5
                || action.charAt(2) != 'D') {
            return;
        }
        // déplacement
        Coordonnees coordSrc = Coordonnees.depuisCars(action.charAt(0), action.charAt(1));
        Coordonnees coordDst = Coordonnees.depuisCars(action.charAt(3), action.charAt(4));
        Case src = plateau[coordSrc.ligne][coordSrc.colonne];
        Case dst = plateau[coordDst.ligne][coordDst.colonne];
        if (!coordSrc.memeLigne(coordDst) || !coordSrc.memeColonne(coordDst)) {
            deplacerUnite(src, dst);
        }
        // attaque, le cas échéant
        if (action.length() == 8 && action.charAt(5) == 'A') {
            Coordonnees coordAtq = Coordonnees.depuisCars(action.charAt(6), action.charAt(7));
            Case atq = plateau[coordAtq.ligne][coordAtq.colonne];
            attaquerUnite(atq, dst);
        }
    }

    /**
     * Déplacer une unité, d'une case à une autre.
     *
     * @param src la case source
     * @param dst la case destination
     */
    static void deplacerUnite(Case src, Case dst) {
        dst.typeUnite = src.typeUnite;
        dst.couleurUnite = src.couleurUnite;
        dst.pointsDeVie = src.pointsDeVie;
        retirerUnite(src);
    }

    /**
     * Appliquer une attaque.
     *
     * @param atq l'unité attaquée
     * @param dst l'unité menant l'attaque
     */
    static void attaquerUnite(Case atq, Case dst) {
        int oldPvAttaquant = dst.pointsDeVie;
        int oldPvAttaque = atq.pointsDeVie;
        dst.pointsDeVie = oldPvAttaquant - 2 - (int) ((oldPvAttaque - 5) / 2);
        atq.pointsDeVie = oldPvAttaque - 4 - (int) ((oldPvAttaquant - 5) / 2);
        if (dst.pointsDeVie <= 0) {
            retirerUnite(dst);
        }
        if (atq.pointsDeVie <= 0) {
            retirerUnite(atq);
        }
    }

    /**
     * Retirer une unité d'une case.
     *
     * @param laCase la case dont on doit retirer l'unité
     */
    static void retirerUnite(Case laCase) {
        laCase.typeUnite = Case.CAR_VIDE;
        laCase.couleurUnite = lowatem.Case.CAR_NOIR;
        laCase.pointsDeVie = 0;
    }

    /**
     * Programme principal. Il sera lancé automatiquement, ce n'est pas à vous
     * de le lancer.
     *
     * @param args Arguments.
     */
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        System.out.println("Démarrage le " + format.format(new Date()));
        System.out.flush();
        // « create » du protocole du grand ordonnateur.
        final String USAGE
                = System.lineSeparator()
                + "\tUsage : java " + IALowatem.class.getName()
                + " <hôte> <port> <ordre>";
        if (args.length != 3) {
            System.out.println("Nombre de paramètres incorrect." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        String hote = args[0];
        int port = -1;
        try {
            port = Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Le port doit être un entier." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        int ordre = -1;
        try {
            ordre = Integer.valueOf(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("L'ordre doit être un entier." + USAGE);
            System.out.flush();
            System.exit(1);
        }
        try {
            char couleurJoueur = (ordre == 1 ? 'R' : 'N');
            IALowatem iaLowatem = new IALowatem(hote, port, couleurJoueur);
            iaLowatem.connexion();
            iaLowatem.toursDeJeu();
        } catch (IOException e) {
            System.out.println("Erreur à l'exécution du programme : \n" + e);
            System.out.flush();
            System.exit(1);
        }
    }
}
