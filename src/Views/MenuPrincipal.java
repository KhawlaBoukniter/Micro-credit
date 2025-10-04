package Views;

import Services.*;
import DAO.*;
import java.util.Scanner;

public class MenuPrincipal {
    private Scanner sc;

    public MenuPrincipal(Scanner sc) {
        this.sc = sc;
    }

    public void afficherMenuPrincipal() {

        EmployeDAO employeDAO = new EmployeDAO();
        ProfessionnelDAO professionnelDAO = new ProfessionnelDAO();
        CreditDAO creditDAO = new CreditDAO();
        EcheanceDAO echeanceDAO = new EcheanceDAO();
        IncidentDAO incidentDAO = new IncidentDAO();

        ScoringService scoringService = new ScoringService(null, null); // temporaire
        IncidentService incidentService = new IncidentService(incidentDAO);
        CreditService creditService = new CreditService(scoringService);
        EcheanceService echeanceService = new EcheanceService(echeanceDAO, incidentDAO, scoringService);
        EmployeService employeService = new EmployeService(employeDAO, scoringService);
        ProfessionnelService professionnelService = new ProfessionnelService(professionnelDAO, scoringService);
        AnalyticsService analyticsService = new AnalyticsService(employeService, professionnelService, incidentService, creditService);

        scoringService.setCreditService(creditService);
        scoringService.setIncidentService(incidentService);

        MenuEmploye menuEmploye = new MenuEmploye(sc, employeService);
        MenuProfessionnel menuProfessionnel = new MenuProfessionnel(sc, professionnelService);
        MenuCredit menuCredit = new MenuCredit(sc, creditService, employeService, professionnelService);
        MenuPaiement menuPaiement = new MenuPaiement(sc, echeanceService, creditService);
        MenuAnalytics menuAnalytics = new MenuAnalytics(sc, analyticsService);

        int choix;
        do {
            System.out.println("\n=== APPLICATION MICRO-CREDIT ===");
            System.out.println("1. Gestion des Employés");
            System.out.println("2. Gestion des Professionnels");
            System.out.println("3. Gestion des Crédits");
            System.out.println("4. Paiement des Échéances");
            System.out.println("5. Analyse & Statistiques");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    menuEmploye.afficherMenuEmploye();
                    break;
                case 2:
                    menuProfessionnel.afficherMenuProfessionnel();
                    break;
                case 3:
                    menuCredit.afficherMenuCredit();
                    break;
                case 4:
                    menuPaiement.afficherMenuPaiement();
                    break;
                case 5:
                    menuAnalytics.afficherMenuAnalytics();
                    break;
                case 0:
                    System.out.println("Au revoir 👋");
                    break;
                default:
                    System.out.println("Choix invalide !");
                    break;
            }

        } while (choix != 0);
    }
}
