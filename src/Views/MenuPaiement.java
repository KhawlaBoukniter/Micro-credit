package Views;

import Enums.StatusPaiement;
import Models.Echeance;
import Services.CreditService;
import Services.EcheanceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuPaiement {

    private Scanner sc;
    private EcheanceService echeanceService;
    private CreditService creditService;

    public MenuPaiement(Scanner sc, EcheanceService echeanceService, CreditService creditService) {
        this.sc = sc;
        this.echeanceService = echeanceService;
        this.creditService = creditService;
    }

    public void afficherMenuPaiement() {
        int choix;
        do {
            System.out.println("\n=== GESTION DES ÉCHÉANCES ===");
            System.out.println("1. Afficher toutes les échéances");
            System.out.println("2. Marquer une échéance comme payée");
            System.out.println("3. Afficher les échéances en retard");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1 :
                    afficherEcheances();
                    break;
                case 2 :
                    marquerEcheancePayee();
                    break;
                case 3 :
                    afficherEcheancesEnRetard();
                    break;
                case 0 :
                    System.out.println("Retour au menu principal...");
                    break;
                default :
                    System.out.println("Choix invalide !");
                    break;
            }
        } while (choix != 0);
    }

    private void afficherEcheances() {
        List<Echeance> echeances = echeanceService.getAll();
        if (echeances.isEmpty()) {
            System.out.println("Aucune échéance disponible.");
            return;
        }

        System.out.println("\n=== LISTE DES ÉCHÉANCES ===");
        for (Echeance e : echeances) {
            System.out.println("---------------------------------");
            System.out.println("ID : " + e.getId());
            System.out.println("Client : " + e.getCredit().getClient().getNom() + " " + e.getCredit().getClient().getPrenom());
            System.out.println("Crédit ID : " + e.getCredit().getId());
            System.out.println("Montant : " + e.getCredit().getMontantOctroye());
            System.out.println("Date échéance : " + e.getDateEcheance());
            System.out.println("Mensualité : " + e.getMensualite());
            System.out.println("Date paiement : " + (e.getDatePaiement() != null ? e.getDatePaiement() : "Non payé"));
            System.out.println("Statut : " + e.getStatusPaiement());
        }
    }

    private void marquerEcheancePayee() {
        System.out.print("ID de l’échéance à marquer comme payée : ");
        String id = sc.nextLine();

        Echeance e = echeanceService.getById(id);
        if (e == null) {
            System.out.println("⚠️ Échéance introuvable !");
            return;
        }

        if (e.getStatusPaiement() == StatusPaiement.PAYE_A_TEMPS) {
            System.out.println("Cette échéance est déjà payée !");
            return;
        }

        echeanceService.enregistrerPaiement(e, LocalDate.now());
        System.out.println("✅ Échéance marquée comme payée !");
    }

    private void afficherEcheancesEnRetard() {
        List<Echeance> enRetard = echeanceService.getEcheancesEnRetard();
        if (enRetard.isEmpty()) {
            System.out.println("Aucune échéance en retard.");
            return;
        }

        System.out.println("\n=== ÉCHÉANCES EN RETARD ===");
        enRetard.forEach(e -> {
            System.out.println("---------------------------------");
            System.out.println("ID : " + e.getId());
            System.out.println("Client : " + e.getCredit().getClient().getNom() + " " + e.getCredit().getClient().getPrenom());
            System.out.println("Date échéance : " + e.getDateEcheance());
            System.out.println("Mensualité : " + e.getMensualite());
            System.out.println("Statut : " + e.getStatusPaiement());
        });
    }
}
