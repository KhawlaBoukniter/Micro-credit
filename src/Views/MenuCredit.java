package Views;

import Enums.Decision;
import Models.Credit;
import Models.Person;
import Services.CreditService;
import Services.EmployeService;
import Services.ProfessionnelService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuCredit {

    private Scanner sc;
    private CreditService creditService;
    private EmployeService employeService;
    private ProfessionnelService professionnelService;

    public MenuCredit(Scanner sc, CreditService creditService, EmployeService employeService, ProfessionnelService professionnelService) {
        this.sc = sc;
        this.creditService = creditService;
        this.employeService = employeService;
        this.professionnelService = professionnelService;
    }

    public void afficherMenuCredit() {
        int choix;
        do {
            System.out.println("\n=== GESTION DES CRÉDITS ===");
            System.out.println("1. Ajouter un crédit");
            System.out.println("2. Afficher tous les crédits");
            System.out.println("3. Rechercher un crédit par ID");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    ajouterCredit();
                    break;
                case 2:
                    afficherCredits();
                    break;
                case 3:
                    rechercherCredit();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }

    private void ajouterCredit() {
        System.out.println("\n=== AJOUT D’UN CRÉDIT ===");

        System.out.print("ID du client : ");
        String clientId = sc.nextLine();

        Person client = employeService.getAllEmployes().stream()
                .filter(e -> e.getId().equals(clientId))
                .map(e -> (Person) e)
                .findFirst()
                .orElseGet(() -> professionnelService.getAllProfessionnels().stream()
                        .filter(p -> p.getId().equals(clientId))
                        .findFirst()
                        .orElse(null));

        if (client == null) {
            System.out.println("⚠️ Client introuvable !");
            return;
        }

        try {
            System.out.print("Date du crédit (YYYY-MM-DD) : ");
            LocalDate dateCredit = LocalDate.parse(sc.nextLine());

            System.out.print("Montant demandé : ");
            Double montantDemande = sc.nextDouble();

            System.out.print("Taux d'intérêt (%) : ");
            Double taux = sc.nextDouble();

            System.out.print("Durée (en mois) : ");
            int duree = sc.nextInt();
            sc.nextLine();

            System.out.print("Type de crédit : ");
            String typeCredit = sc.nextLine();

            Credit credit = new Credit();
            credit.setDateCredit(dateCredit);
            credit.setMontantDemande(montantDemande);
            credit.setTauxInteret(taux);
            credit.setDureeMois(duree);
            credit.setTypeCredit(typeCredit);

            creditService.addCredit(credit, client);

            System.out.println("✅ Crédit ajouté avec succès !");
            System.out.println("Décision : " + credit.getDecision());

            if (credit.getDecision() == Decision.ACCORD_IMMEDIAT) {
                System.out.println("🗓️ Les échéances ont été générées automatiquement !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void afficherCredits() {
        List<Credit> credits = creditService.getAllCredits();
        if (credits.isEmpty()) {
            System.out.println("Aucun crédit disponible.");
            return;
        }

        System.out.println("\n=== LISTE DES CRÉDITS ===");
        for (Credit c : credits) {
            System.out.println(c.toString());
        }
    }

    private void rechercherCredit() {
        System.out.print("ID du crédit à rechercher : ");
        String id = sc.nextLine();

        Optional<Credit> creditOpt = creditService.getAllCredits().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (!creditOpt.isPresent()) {
            System.out.println("⚠️ Crédit introuvable !");
            return;
        }

        Credit c = creditOpt.get();
        System.out.println("\n=== DÉTAILS CRÉDIT ===");
        System.out.println(c);
    }
}
