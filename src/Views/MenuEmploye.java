package Views;

import Enums.Secteur;
import Enums.SituationFamiliale;
import Enums.TypeContrat;
import Models.Employe;
import Services.EmployeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MenuEmploye {

    private Scanner sc;
    private EmployeService employeService;

    public MenuEmploye(Scanner sc, EmployeService employeService) {
        this.sc = sc;
        this.employeService = employeService;
    }

    public void afficherMenuEmploye() {
        int choix;
        do {
            System.out.println("\n=== GESTION DES EMPLOYÉS ===");
            System.out.println("1. Ajouter un employé");
            System.out.println("2. Modifier un employé");
            System.out.println("3. Supprimer un employé");
            System.out.println("4. Afficher tous les employés");
            System.out.println("5. Rechercher un employé par ID");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    ajouterEmploye();
                    break;
                case 2:
                    modifierEmploye();
                    break;
                case 3:
                    supprimerEmploye();
                    break;
                case 4:
                    afficherEmployes();
                    break;
                case 5:
                    rechercherEmploye();
                    break;
                case 0:
                    System.out.println("Retour au menu principal...");
                    break;
                default:
                    System.out.println("⚠️ Choix invalide !");
            }
        } while (choix != 0);
    }

    // === Menu de sélection du Secteur ===
    private Secteur menuSecteur() {
        System.out.println("\n=== Choisir le secteur ===");
        System.out.println("1. PUBLIC");
        System.out.println("2. GRANDE_ENTREPRISE");
        System.out.println("3. PME");
        System.out.print("Votre choix : ");
        int choix = sc.nextInt();
        sc.nextLine();

        switch (choix) {
            case 1: return Secteur.PUBLIC;
            case 2: return Secteur.GRANDE_ENTREPRISE;
            case 3: return Secteur.PME;
            default:
                System.out.println("⚠️ Choix invalide, secteur par défaut : PUBLIC");
                return Secteur.PUBLIC;
        }
    }

    private TypeContrat menuTypeContrat() {
        System.out.println("\n=== Choisir le type de contrat ===");
        System.out.println("1. CDI_PUBLIC");
        System.out.println("2. CDI_PRIVE_GRANDE_ENTREPRISE");
        System.out.println("3. CDI_PRIVE_PME");
        System.out.println("4. CDD");
        System.out.print("Votre choix : ");
        int choix = sc.nextInt();
        sc.nextLine();

        switch (choix) {
            case 1: return TypeContrat.CDI_PUBLIC;
            case 2: return TypeContrat.CDI_PRIVE_GRANDE_ENTREPRISE;
            case 3: return TypeContrat.CDI_PRIVE_PME;
            case 4: return TypeContrat.CDD_INTERIM;
            default:
                System.out.println("⚠️ Choix invalide, contrat par défaut : CDD");
                return TypeContrat.CDD_INTERIM;
        }
    }


    private void ajouterEmploye() {
        System.out.println("\n=== AJOUT D’UN EMPLOYÉ ===");

        System.out.print("Nom : ");
        String nom = sc.nextLine();

        System.out.print("Prénom : ");
        String prenom = sc.nextLine();

        System.out.print("Date de naissance (format : 1995-04-10) : ");
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

        System.out.println("Situation familiale (1. MARIE, 2. CELIBATAIRE) : ");
        int choixSit = sc.nextInt();
        sc.nextLine();
        SituationFamiliale situation;
        switch (choixSit) {
            case 1: situation = SituationFamiliale.MARIE; break;
            case 2: situation = SituationFamiliale.CELIBATAIRE; break;
            default: situation = SituationFamiliale.CELIBATAIRE; break;
        }

        System.out.print("Salaire (DH) : ");
        double salaire = sc.nextDouble();
        sc.nextLine();

        System.out.print("Ancienneté (années) : ");
        int anciennete = sc.nextInt();
        sc.nextLine();

        System.out.print("Poste : ");
        String poste = sc.nextLine();

        TypeContrat typeContrat = menuTypeContrat();
        Secteur secteur = menuSecteur();

        Employe e = new Employe(
                nom, prenom, dateNaissance, ville, enfants,
                investissement, placement, situation,
                LocalDateTime.now(),
                null,
                age, salaire, anciennete, poste, typeContrat, secteur
        );

        employeService.addEmploye(e);
        System.out.println("✅ Employé ajouté avec succès !");
    }


    private void modifierEmploye() {
        System.out.print("ID de l’employé à modifier : ");
        String id = sc.nextLine();

        Employe e = employeService.findEmployeById(id);
        if (e == null) {
            System.out.println("⚠️ Aucun employé trouvé avec cet ID !");
            return;
        }

        System.out.println("Modification de l’employé (" + e.getNom() + " " + e.getPrenom() + ")");
        System.out.print("Nouveau salaire : ");
        e.setSalaire(sc.nextDouble());
        sc.nextLine();

        System.out.print("Nouveau poste : ");
        e.setPoste(sc.nextLine());

        System.out.print("Nouvelle ancienneté : ");
        e.setAnciennete(sc.nextInt());
        sc.nextLine();

        employeService.updateEmploye(e);
        System.out.println("✅ Employé modifié avec succès !");
    }


    private void supprimerEmploye() {
        System.out.print("ID de l’employé à supprimer : ");
        String id = sc.nextLine();
        employeService.deleteEmploye(id);
        System.out.println("✅ Employé supprimé avec succès !");
    }


    private void afficherEmployes() {
        List<Employe> employes = employeService.getAllEmployes();
        if (employes.isEmpty()) {
            System.out.println("Aucun employé enregistré.");
            return;
        }

        System.out.println("\n=== LISTE DES EMPLOYÉS ===");
        employes.forEach(e -> {
            System.out.println("---------------------------------");
            System.out.println(e.toString());
        });
    }


    private void rechercherEmploye() {
        System.out.print("ID de l’employé à rechercher : ");
        String id = sc.nextLine();

        Employe e = employeService.findEmployeById(id);
        if (e == null) {
            System.out.println("⚠️ Aucun employé trouvé !");
            return;
        }

        System.out.println("\n=== DÉTAILS EMPLOYÉ ===");
        System.out.println(e);
    }
}
