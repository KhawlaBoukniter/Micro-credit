package Views;

import Enums.SituationFamiliale;
import Enums.TypeContrat;
import Models.Professionnel;
import Services.ProfessionnelService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuProfessionnel {

    private Scanner sc;
    private ProfessionnelService professionnelService;

    public MenuProfessionnel(Scanner sc, ProfessionnelService professionnelService) {
        this.sc = sc;
        this.professionnelService = professionnelService;
    }

    public void afficherMenuProfessionnel() {
        int choix;
        do {
            System.out.println("\n=== GESTION DES PROFESSIONNELS ===");
            System.out.println("1. Ajouter un professionnel");
            System.out.println("2. Modifier un professionnel");
            System.out.println("3. Supprimer un professionnel");
            System.out.println("4. Afficher tous les professionnels");
            System.out.println("5. Rechercher un professionnel par ID");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1: ajouterProfessionnel(); break;
                case 2: modifierProfessionnel(); break;
                case 3: supprimerProfessionnel(); break;
                case 4: afficherProfessionnels(); break;
                case 5: rechercherProfessionnel(); break;
                case 0: System.out.println("Retour au menu principal..."); break;
                default: System.out.println("⚠️ Choix invalide !");
            }
        } while (choix != 0);
    }

    private SituationFamiliale menuSituationFamiliale() {
        System.out.println("\n=== Choisir la situation familiale ===");
        System.out.println("1. MARIE");
        System.out.println("2. CELIBATAIRE");
        System.out.print("Votre choix : ");
        int choix = sc.nextInt();
        sc.nextLine();
        switch (choix) {
            case 1: return SituationFamiliale.MARIE;
            case 2: return SituationFamiliale.CELIBATAIRE;
            default: System.out.println("Choix invalide, CELIBATAIRE par défaut"); return SituationFamiliale.CELIBATAIRE;
        }
    }

    private TypeContrat menuTypeContrat() {
        System.out.println("\n=== Choisir le type de contrat ===");
        System.out.println("1. PROFESSION_LIBERALE_STABLE");
        System.out.println("2. AUTO_ENTREPRENEUR");
        System.out.print("Votre choix : ");
        int choix = sc.nextInt();
        sc.nextLine();
        switch (choix) {
            case 1: return TypeContrat.PROFESSION_LIBERALE_STABLE;
            case 2: return TypeContrat.AUTO_ENTREPRENEUR;
            default: System.out.println("Choix invalide, AUTO_ENTREPRENEUR par défaut"); return TypeContrat.AUTO_ENTREPRENEUR;
        }
    }

    private void ajouterProfessionnel() {
        System.out.println("\n=== AJOUT D’UN PROFESSIONNEL ===");

        System.out.print("Nom : ");
        String nom = sc.nextLine();

        System.out.print("Prénom : ");
        String prenom = sc.nextLine();

        System.out.print("Date de naissance (YYYY-MM-DD) : ");
        LocalDate dateNaissance = LocalDate.parse(sc.nextLine());

        System.out.print("Âge : ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Ville : ");
        String ville = sc.nextLine();

        System.out.print("Nombre d’enfants : ");
        int enfants = sc.nextInt();
        sc.nextLine();

        System.out.print("Investissement (true/false) : ");
        boolean investissement = sc.nextBoolean();

        System.out.print("Placement (true/false) : ");
        boolean placement = sc.nextBoolean();
        sc.nextLine();

        SituationFamiliale situation = menuSituationFamiliale();

        System.out.print("Revenu (DH) : ");
        double revenu = sc.nextDouble();
        sc.nextLine();

        System.out.print("Immatriculation fiscale : ");
        String immatriculationFiscale = sc.nextLine();

        System.out.print("Secteur d’activité : ");
        String secteurActivite = sc.nextLine();

        System.out.print("Type d’activité : ");
        String activite = sc.nextLine();

        TypeContrat statut = menuTypeContrat();

        Professionnel p = new Professionnel(
                nom, prenom, dateNaissance, ville, enfants, investissement, placement,
                situation, LocalDateTime.now(), 0, age, revenu,
                immatriculationFiscale, secteurActivite, activite, statut
        );

        professionnelService.addProfessionnel(p);
        System.out.println("✅ Professionnel ajouté avec succès !");
    }

    private void modifierProfessionnel() {
        System.out.print("ID du professionnel à modifier : ");
        String id = sc.nextLine();
        Professionnel p = professionnelService.findProfessionnelById(id);
        if (p == null) {
            System.out.println("⚠️ Aucun professionnel trouvé !");
            return;
        }

        System.out.println("Modification du professionnel (" + p.getNom() + " " + p.getPrenom() + ")");
        System.out.print("Nouveau revenu : ");
        p.setRevenu(sc.nextDouble());
        sc.nextLine();

        System.out.print("Nouvelle activité : ");
        p.setActivite(sc.nextLine());

        System.out.print("Nouveau secteur activité : ");
        p.setSecteurActivite(sc.nextLine());

        professionnelService.updateProfessionnel(p);
        System.out.println("✅ Professionnel modifié avec succès !");
    }

    private void supprimerProfessionnel() {
        System.out.print("ID du professionnel à supprimer : ");
        String id = sc.nextLine();
        professionnelService.deleteProfessionnel(id);
        System.out.println("✅ Professionnel supprimé avec succès !");
    }

    private void afficherProfessionnels() {
        List<Professionnel> pros = professionnelService.getAllProfessionnels();
        if (pros.isEmpty()) {
            System.out.println("Aucun professionnel enregistré.");
            return;
        }

        System.out.println("\n=== LISTE DES PROFESSIONNELS ===");
        pros.forEach(p -> {
            System.out.println("---------------------------------");
            System.out.println(p.toString());
        });
    }

    private void rechercherProfessionnel() {
        System.out.print("ID du professionnel à rechercher : ");
        String id = sc.nextLine();
        Professionnel p = professionnelService.findProfessionnelById(id);
        if (p == null) {
            System.out.println("⚠️ Aucun professionnel trouvé !");
            return;
        }

        System.out.println("\n=== DÉTAILS PROFESSIONNEL ===");
        System.out.println(p);
    }
}
