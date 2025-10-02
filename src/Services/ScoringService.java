package Services;

import DAO.CreditDAO;
import DAO.EcheanceDAO;
import DAO.IncidentDAO;
import Enums.SituationFamiliale;
import Enums.StatusPaiement;
import Enums.TypeContrat;
import Models.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScoringService {
    private IncidentDAO incidentDAO;
    private CreditDAO creditDAO;
    private EcheanceDAO echeanceDAO;
    private IncidentService incidentService;

    public ScoringService() {
        this.incidentDAO = new IncidentDAO();
        this.creditDAO = new CreditDAO();
        this.echeanceDAO = new EcheanceDAO();
        this.incidentService = new IncidentService();
    }

    public Integer calculerScore(Person client) {
        Integer score = 0;
        score += stabilliteProfessionnelle(client);
        score += capaciteFinanciere(client);
        score += historique(client);
        score += relationClient(client);
        score += patrimoine(client);

        return Math.min(Math.max(score, 0), 100);
    }

    private Integer stabilliteProfessionnelle(Person client) {
        Integer base = 0;
        if (client instanceof Employe) {
            Employe e = (Employe) client;
            Integer anciennete = e.getAnciennete();
            TypeContrat typeContrat = e.getTypeContrat();

            if (anciennete >= 5) base += 5;
            else if (anciennete >= 2) base += 3;
            else if (anciennete >= 1) base += 1;
            else base += 0;

            if (typeContrat.equals(TypeContrat.CDI_PUBLIC)) base += 25;
            else if (typeContrat.equals(TypeContrat.CDI_PRIVE_GRANDE_ENTREPRISE)) base += 15;
            else if (typeContrat.equals(TypeContrat.CDD_INTERIM)) base += 10;
            else if (typeContrat.equals(TypeContrat.CDI_PRIVE_PME)) base += 12;
            else base += 0;

        } else if (client instanceof Professionnel) {
            Professionnel p = (Professionnel) client;
            TypeContrat statuProfessionnel = p.getStatut_professionnel();

            base += 9;

            if (statuProfessionnel != null) {
                if (statuProfessionnel.equals(TypeContrat.PROFESSION_LIBERALE_STABLE)) base += 18;
                else if (statuProfessionnel.equals(TypeContrat.AUTO_ENTREPRENEUR)) base += 12;
                else base += 0;
            } else base += 0;

        } else base += 0;
        return base;
    }

//    private Integer stabilliteProfessionnelle2(Person client) {
//        if (client instanceof Employe) {
//            Employe e = (Employe) client;
//            TypeContrat typeContrat = e.getTypeContrat();
//            if (typeContrat == null) return 0;
//            else if (typeContrat.equals(TypeContrat.CDI_PUBLIC)) return 25;
//            else if (typeContrat.equals(TypeContrat.CDI_PRIVE_GRANDE_ENTREPRISE)) return 15;
//            else if (typeContrat.equals(TypeContrat.CDD_INTERIM)) return 10;
//            else if (typeContrat.equals(TypeContrat.CDI_PRIVE_PME)) return 12;
//            else return 0;
//        }
//        else if (client instanceof  Professionnel) {
//                Professionnel p = (Professionnel) client;
//                TypeContrat statuProfessionnel = p.getStatut_professionnel();
//                if (statuProfessionnel == null) return 0;
//            if (statuProfessionnel.equals(TypeContrat.PROFESSION_LIBERALE_STABLE)) return 18;
//            else if (statuProfessionnel.equals(TypeContrat.AUTO_ENTREPRENEUR)) return 12;
//            else return 0;
//        }
//        else return 0;
//    }

    private Integer capaciteFinanciere(Person client) {
        if (client instanceof Professionnel) {
            Professionnel p = (Professionnel) client;
            Double revenue = p.getRevenu();
            
            if (revenue >= 10000) return 10;
            else if (revenue >= 8000) return 25;
            else if (revenue >= 5000) return 20;
            else if (revenue >= 3000) return 15;
            else return 10;
            
        } else if (client instanceof Employe) {
            Employe p = (Employe) client;
            Double salaire = p.getSalaire();
            
            if (salaire >= 10000) return 10;
            else if (salaire >= 8000) return 25;
            else if (salaire >= 5000) return 20;
            else if (salaire >= 3000) return 15;
            else return 10;
            
        } else return 0;
    }

    private Integer relationClient(Person client) {
        Integer rslt = 0;
        if (client.getScore() == null) {
            if (client.getAge() > 55) rslt += 6;
            else if (client.getAge() > 36) rslt += 10;
            else if (client.getAge() > 26) rslt += 8;
            else rslt += 4;

            if (client.getSituationFamiliale().equals(SituationFamiliale.MARIE)) rslt += 3;
            else if (client.getSituationFamiliale().equals(SituationFamiliale.CELIBATAIRE)) rslt += 2;
            else rslt += 0;

            if (client.getNombreEnfants() == 0) rslt += 2;
            else if (client.getNombreEnfants() < 2) rslt += 1;
            else rslt += 0;

        } else {
            List<Credit> credits = creditDAO.getAll().stream()
                    .filter(c -> c.getClient().equals(client))
                    .collect(Collectors.toList());

            if (credits.stream().anyMatch(c -> c.getDateCredit().isBefore(LocalDate.now().minusYears(3)))) {
                rslt += 10;
            } else if (credits.stream().anyMatch(c -> c.getDateCredit().isBefore(LocalDate.now().minusYears(1)))) {
                rslt += 8;
            } else rslt += 5;
        }
        return rslt;
    }

    private Integer patrimoine(Person client) {
        if (client.getInvestissement().equals(Boolean.TRUE) || client.getPlacement().equals(Boolean.TRUE)) return 10;
        else return 0;
    }

    private Integer historique(Person client) {
        Integer rslt = 0;

            List<Incident> incidents = incidentService.getIncidentsByClient(client);
            Long nbrImpayeN = incidentService.getImpayeNonRegle(incidents);
            Long nbrImpayeR = incidentService.getImpayeRegle(incidents);
            Long nbrRetards = incidentService.incidentsEnRetard(incidents);

            if (nbrRetards == 0 && incidents.isEmpty()) rslt += 10;
            else {
                if (nbrRetards >= 1 && nbrRetards <= 3) rslt -= 3;
                else if (nbrRetards >= 4) rslt -= 5;
                if (nbrImpayeR > 0) rslt += 5;
                if (nbrImpayeN > 0) rslt -= 10;
            }

        return rslt;
    }


    private Optional<Credit> activeCreditsByClientId(String id) {
        return creditDAO.getAll().stream()
                .filter(c -> c.getClient() != null && c.getClient().getId().equals(id))
                .sorted(Comparator.comparing(Credit::getDateCredit).reversed())
                .findFirst();
    }

    private List<Echeance> activeCreditsEcheances(String id) {
        return echeanceDAO.getAll().stream()
                .filter(e -> e.getCredit() != null && e.getCredit().equals(activeCreditsByClientId(id).orElse(null)))
                .filter(e -> e.getStatusPaiement().equals(StatusPaiement.EN_RETARD))
                .collect(Collectors.toList());
    }

    public Integer calculerScoreIncident(Echeance echeance) {
        Integer score = 0;

        if (echeance.getStatusPaiement().equals(StatusPaiement.IMPAYE_NON_REGLE)) score += 0;
        else if (echeance.getStatusPaiement() == StatusPaiement.IMPAYE_REGLE) score += 5;
        else if (echeance.getStatusPaiement() == StatusPaiement.PAYE_EN_RETARD) score += 3;
        else if (echeance.getStatusPaiement() == StatusPaiement.PAYE_A_TEMPS) score += 10;
        return score;
    }
}




