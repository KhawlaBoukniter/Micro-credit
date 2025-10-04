package Views;

import Enums.TypeContrat;
import Models.Person;
import Services.AnalyticsService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuAnalytics {

    private Scanner sc;
    private AnalyticsService analyticsService;

    public MenuAnalytics(Scanner sc, AnalyticsService analyticsService) {
        this.sc = sc;
        this.analyticsService = analyticsService;
    }

    public void afficherMenuAnalytics() {
        int choix;
        do {
            System.out.println("\n=== ANALYTICS CLIENTS ===");
            System.out.println("1. Afficher les clients éligibles à un crédit immobilier");
            System.out.println("2. Afficher les clients à risque");
            System.out.println("3. Afficher tous les clients triés par score, revenu et ancienneté");
            System.out.println("4. Statistiques clients par type d’emploi");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:

                    afficherClientsEligiblesImmobilier();
                    break;
                case 2:
                    afficherClientsARisque();
                    break;
                case 3:
                    afficherClientsTries();
                    break;
                case 4:
                    afficherStatsClientsParEmploi();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("Choix invalide !");
                    break;
            }

        } while (choix != 0);
    }

    private void afficherClientsEligiblesImmobilier() {
        List<Person> clients = analyticsService.clientsEligiblesImmobilier();
        if (clients.isEmpty()) {
            System.out.println("Aucun client éligible trouvé.");
            return;
        }
        System.out.println("\n=== CLIENTS ÉLIGIBLES IMMOBILIER ===");
        clients.forEach(c -> System.out.println(c.getNom() + " " + c.getPrenom() + " | Score: " + c.getScore() + " | Age: " + c.getAge()));
    }

    private void afficherClientsARisque() {
        List<Person> clients = analyticsService.getRiskClients();
        if (clients.isEmpty()) {
            System.out.println("Aucun client à risque trouvé.");
            return;
        }
        System.out.println("\n=== CLIENTS À RISQUE ===");
        clients.forEach(c -> System.out.println(c.getNom() + " " + c.getPrenom() + " | Score: " + c.getScore()));
    }

    private void afficherClientsTries() {
        List<Person> clients = analyticsService.getClientsSorted();
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
            return;
        }
        System.out.println("\n=== CLIENTS TRIÉS ===");
        clients.forEach(c -> {
            double revenu = (c instanceof Models.Employe) ? ((Models.Employe) c).getSalaire() : ((Models.Professionnel) c).getRevenu();
            System.out.println(c.getNom() + " " + c.getPrenom() + " | Score: " + c.getScore() + " | Revenu: " + revenu);
        });
    }

    private void afficherStatsClientsParEmploi() {
        Map<TypeContrat, Map<String, Double>> stats = analyticsService.getClientsByEmploi();
        if (stats.isEmpty()) {
            System.out.println("Aucune statistique disponible.");
            return;
        }
        System.out.println("\n=== STATISTIQUES CLIENTS PAR EMPLOI ===");
        stats.forEach((type, subStats) -> {
            System.out.println("Type Contrat: " + type);
            System.out.println("  Nombre de clients: " + subStats.get("nbrClients").intValue());
            System.out.println("  Score moyen: " + String.format("%.2f", subStats.get("scoreMoyen")));
            System.out.println("  Revenu moyen: " + String.format("%.2f", subStats.get("revenusMoyens")));
            System.out.println("  Taux de crédits accordés: " + String.format("%.2f", subStats.get("taux")) + "%");
            System.out.println("---------------------------------");
        });
    }
}
