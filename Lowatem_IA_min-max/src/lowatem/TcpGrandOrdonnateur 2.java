package lowatem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Remarques destinées aux compétiteurs : utilisez cette classe pour communiquer
 * avec le grand ordonnateur ; ne changez rien au code de cette classe.
 *
 * Interface pour le protocole du grand ordonnateur.
 */
public class TcpGrandOrdonnateur {

    /**
     * journal
     */
    private static final Logger LOG = Logger.getLogger(TcpGrandOrdonnateur.class.getName());

    /**
     * Séparateur entre chaînes échangées par TCP
     */
    public final static char SEPARATEUR = '~';

    /**
     * Socket.
     */
    private Socket socket;

    /**
     * Création d'une interface pour le protocole du grand ordonnateur.
     */
    public TcpGrandOrdonnateur() {
        socket = null;
    }

    /**
     * Connexion au serveur.
     *
     * @param hote Hôte.
     * @param port Port.
     * @throws IOException
     */
    public void connexion(String hote, int port) throws IOException {
        socket = new Socket(hote, port);
        socket.getInputStream();
        socket.getOutputStream();
    }

    /**
     * Envoi d'un entier au serveur.
     *
     * @param entier Entier à envoyer.
     * @throws IOException
     */
    public void envoiEntier(final int entier) throws IOException {
        testerSocket();
        final OutputStream fluxSortie = socket.getOutputStream();
        if (fluxSortie == null) {
            throw new IllegalArgumentException("Le flux en sortie est null.");
        } else if (estValidePourEnvoi(entier)) {
            fluxSortie.write(entier);
            fluxSortie.flush();
        } else {
            throw new IllegalArgumentException(
                    "L'entier " + entier + " n'est pas valide pour être envoyé au serveur.");
        }
    }

    /**
     * Envoi d'un caractère au serveur.
     *
     * @param caractere Caractère à envoyer.
     * @throws IOException
     */
    public void envoiCaractere(final char caractere) throws IOException {
        envoiEntier((int) caractere);
    }

    /**
     * Envoi d'une chaîne
     *
     * @param chaine la chaîne à envoyer
     * @throws IOException
     */
    public void envoiChaine(final String chaine) throws IOException {
        if (chaine == null) {
            throw new IllegalArgumentException(
                    "Demande d'envoi d'une chaine null. Abandon.");
        }

        if (!chaineValidePourTransmission(chaine)) {
            final String message
                    = "Demande (non réalisée) d'envoi de la chaine suivante, "
                    + "contenant des caractères spéciaux : " + chaine;
            LOG.severe(message);
            throw new IllegalArgumentException(message);
        }

        LOG.info("Envoi de la chaine : " + chaine);

        for (int i = 0; i < chaine.length(); i++) {
            char caractere = chaine.charAt(i);
            envoiCaractere(caractere);
        }
        envoiCaractere(SEPARATEUR);
    }

    /**
     * Réception d'une chaîne de caractères. La chaîne est reçue caractère par
     * caractère, jusqu'au séparateur.
     *
     * @param clientSocket socket client
     * @return la chaîne reçue
     * @throws IOException
     */
    public String receptionChaine(final Socket clientSocket) throws IOException {
        LOG.info("receptionChaine");
        final StringBuffer chaine = new StringBuffer();
        boolean separateurRecu = false;
        boolean ok = true;
        while (ok && !separateurRecu) {
            final char recu = receptionCaractere();
            ok = (recu != (char) -1);
            separateurRecu = (recu == SEPARATEUR);
            if (ok && !separateurRecu) {
                chaine.append(recu);
            }
        }
        LOG.info("receptionChaine : chaine = " + chaine.toString());
        return (ok ? chaine.toString() : null);
    }

    /**
     * Teste si un entier peut être envoyé au serveur, c'est-à-dire s'il fait
     * partie des valeurs autorisées.
     *
     * @param entier Entier à tester pour être envoyé.
     * @return L'entier est-il valide pour être envoyé ?
     */
    private boolean estValidePourEnvoi(final int entier) {
        return chaineValidePourTransmission("" + entier);
    }

    /**
     * Réception d'un entier depuis le serveur.
     *
     * @return Entier reçu.
     * @throws IOException
     */
    public int receptionEntier() throws IOException {
        testerSocket();
        final InputStream fluxEntree = socket.getInputStream();
        if (fluxEntree == null) {
            throw new IllegalArgumentException("Le flux en entrée est null.");
        } else {
            int entierRecu = -1;
            entierRecu = fluxEntree.read();
            return entierRecu;
        }
    }

    /**
     * Réception d'un caractère depuis le serveur.
     *
     * @return Caractère reçu.
     * @throws IOException
     */
    public char receptionCaractere() throws IOException {
        return (char) receptionEntier();
    }

    /**
     * Envoyer une action au Grand Ordonnateur.
     *
     * @param action l'action à envoyer
     */
    public void envoyerAction(String action) {
        try {
            envoiChaine(action);
        } catch (IOException ex) {
            System.err.println("Problème d'envoi au grand ordonnateur.");
        }
    }

    /**
     * Recevoir une action du Grand Ordonnateur.
     *
     * @return l'action reçue
     */
    public String recevoirAction() {
        String action = null;
        try {
            action = receptionChaine(socket);
        } catch (IOException ex) {
            System.out.println("Problème à la réception d'une chaîne.");
        }
        return action;
    }

    /**
     * Recevoir et construire le plateau initial depsui le Grand Ordonnateur.
     *
     * @return la plateau initial
     */
    public Case[][] recevoirPlateauInitial() {
        String plateau = null;
        try {
            plateau = receptionChaine(socket);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            System.out.println("Erreur à la réception du plateau. Abandon.");
            System.exit(1);
        }
        return Utils.plateauDepuisTexte(plateau);
    }

    /**
     * Déconnexion du serveur.
     *
     * @throws IOException
     */
    public void deconnexion() throws IOException {
        testerSocket();
        InputStream fluxEntree = null;
        fluxEntree = socket.getInputStream();
        if (fluxEntree != null) {
            fluxEntree.close();
        }
        OutputStream fluxSortie = null;
        fluxSortie = socket.getOutputStream();
        if (fluxSortie != null) {
            fluxSortie.close();
        }
        socket.close();
    }

    /**
     * Teste si le socket est initialisé ; sinon, arrêt du programme.
     */
    private void testerSocket() {
        if (socket == null) {
            System.out.println("Le socket n'est pas initialisé.");
            System.out.flush();
            System.exit(1);
        }
    }

    /**
     * Teste si une chaine est valide pour être envoyée, c'est-à-dire si elle ne
     * contient pas de caractère spécial.
     *
     * @param chaine
     * @return vrai ssi la chaine est valide pour l'envoi
     */
    public boolean chaineValidePourTransmission(final String chaine) {
        if (chaine == null) {
            return false;
        }
        final String regex = "[\\x20-\\x7d]*"; // intervalle ASCII [32;125]
        return Pattern.matches(regex, chaine);
    }
}
