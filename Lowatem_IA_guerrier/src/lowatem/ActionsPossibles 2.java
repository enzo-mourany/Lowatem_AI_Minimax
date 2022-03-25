package lowatem;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Ensemble des actions possibles pour un joueur, sur un plateau donné.
 */
public class ActionsPossibles {

    /**
     * Nombre maximal d'actions possibles, tous niveaux confondus.
     */
    final static int MAX_NB_ACTIONS = 35285;

    /**
     * Compte le nombre d'actions possibles déjà entrées dans le tableau des
     * actions possibles.
     */
    int nbActions;

    /**
     * Tableau contenant les actions possibles.
     */
    String[] actions;

    /**
     * Constructeur.
     */
    ActionsPossibles() {
        actions = new String[MAX_NB_ACTIONS];
        nbActions = 0;
    }
    
    /**
     * Constructeur, à partir d'un tableau d'actions possibles existant.
     * 
     * @param actionsPossibles tableau d'actions possibles
     */
    ActionsPossibles(String[] actionsPossibles) {
        actions = actionsPossibles;
        nbActions = actionsPossibles.length;
    }

    /**
     * Ajouter une action parmi les actions possibles.
     *
     * @param action l'action à ajouter
     */
    void ajouterAction(String action) {
        actions[nbActions] = action;
        nbActions++;
    }

    /**
     * Fonction qui renvoie un tableau contenant les actions possibles, sans les
     * actions vides.
     *
     * @return un tableau contenant les actions possibles
     */
    String[] nettoyer() {
        return nettoyerTableau(actions);
    }

    /**
     * Fonction qui renvoie une copie du tableau sans les cases non utilisées,
     * c'est-à-dire contenant null ou la chaîne vide.Par exemple {"Coucou", "",
     * null, "Hello", null} renvoie {"Coucou", "Hello"}.
     *
     * @param tabActions le tableau des actions à nettoyer
     * @return le tableau des actions nettoyé
     */
    public static String[] nettoyerTableau(String[] tabActions) {
        return Arrays.stream(tabActions)
                .filter(a -> ((a != null) && (!"".equals(a))))
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    /**
     * Retourne l'action sans les points de vie. Par exemple, sur "aFDaE,4,5",
     * cela renvoit "aFDaE".
     *
     * @param actionPv l'action avec des points de vie
     * @return l'action sans les points de vie
     */
    static String enleverPointsDeVie(String actionPv) {
        int posVirgule = actionPv.indexOf(",");
        return posVirgule == -1 ? actionPv : actionPv.substring(0, posVirgule);
    }

    /**
     * Afficher les actions possibles déjà calculées.
     */
    void afficher() {
        System.out.println(Arrays.deepToString(actions));
    }

    /**
     * Indique si une action est présente parmi les actions possibles calculées.
     *
     * @param action l'action à tester
     * @return vrai ssi l'action est présente parmi les actions possibles
     */
    boolean contient(String action) {
        return Arrays.asList(actions).contains(action);
    }
}
