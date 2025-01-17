import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SeriousGame {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Avatar> avatars = new ArrayList<>();
    private static List<Question> questions = new ArrayList<>();
    private static List<Defi> defis = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        initialiserFichiersCSV();
        chargerDonnees();
        InterfaceUtilisateur.gererMenuPrincipal(avatars, questions, defis);
    }

    private static void initialiserFichiersCSV() {
        Question.initialiserFichierQuestions();
        Defi.initialiserFichierDefis();
    }

    private static void chargerDonnees() throws IOException {
        avatars = Avatar.ChargementAvatar();
        questions = Question.chargerQuestions();
        defis = Defi.chargerDefis();
    }

    public static void creerNouvelAvatar(String nom) throws IOException {
        int id = avatars.size() + 1;
        Avatar avatar = Avatar.creerNouvelAvatar(id, nom);
        avatars.add(avatar);
        System.out.println("Avatar créé : " + avatar.getNom());
    }

    public static void lancerDefi(Avatar initiateur, Avatar adversaire, Question question) throws IOException {
      
        int id = defis.size() + 1;
        Defi defi = new Defi(id, initiateur, adversaire, question);
        defis.add(defi);
        defi.sauvegarderDefi();

        System.out.println("Défi lancé à " + adversaire.getNom() + " avec la question : " + question.getEnonce());
    }

    public static void repondreDefi(Avatar avatar, Defi defi, List<String> reponse) throws IOException {
        defi.setReponse(reponse);
        defi.sauvegarderDefi();
    
        boolean bonneReponse = false;
        for (String rep : reponse) {
            if (defi.getQuestion().getReponse().equalsIgnoreCase(rep)) {
                bonneReponse = true;
                break;
            }
        }
    
        if (bonneReponse) {
            System.out.println("Bonne réponse! Vous gagnez " + defi.getQuestion().getPoints() + " points.");
            avatar.incrementerPointsDeVie(defi.getQuestion().getPoints());
        } else {
            System.out.println("Mauvaise réponse! Vous perdez " + defi.getQuestion().getPoints() + " points.");
            avatar.decrementerPointsDeVie(defi.getQuestion().getPoints());
        }
    
        // Assuming defis is a class-level list, so it needs to be passed as a parameter or handled accordingly
        // defis.remove(defi);
        // Defi.supprimerDefi(defi);
    }
}
