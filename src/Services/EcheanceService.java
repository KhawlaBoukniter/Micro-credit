package Services;

import DAO.CreditDAO;
import DAO.EcheanceDAO;
import DAO.EmployeDAO;
import DAO.IncidentDAO;
import Enums.StatusPaiement;
import Models.*;

import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EcheanceService {
    private EcheanceDAO echeanceDAO;
    private CreditDAO creditDAO;
    private IncidentDAO incidentDAO;
    private ScoringService scoringService;
    private EmployeDAO employeDAO;

    public EcheanceService() {
        this.echeanceDAO = new EcheanceDAO();
        this.creditDAO = new CreditDAO();
        this.incidentDAO = new IncidentDAO();
        this.scoringService = new ScoringService();
        this.employeDAO = new EmployeDAO();

    }

    public void genererEcheances(Credit credit) {
        List<Echeance> echeances = new ArrayList<>();
        Double mensualite = credit.getMontantOctroye() / credit.getDureeMois();
        LocalDate dateDebut = credit.getDateCredit().plusMonths(1);

        for (Integer i = 0; i < credit.getDureeMois(); i++) {
            Echeance e = new Echeance(
                    dateDebut.plusMonths(i),
                    mensualite,
                    null,
                    StatusPaiement.EN_RETARD
            );
            e.setCredit(credit);
            echeanceDAO.addEcheance(e);
            echeances.add(e);
        }
    }

    public void enregistrerPaiement(Echeance echeance, LocalDate datePaiement) {
        if (datePaiement == null) {
            echeance.setStatusPaiement(StatusPaiement.IMPAYE_NON_REGLE);
            echeanceDAO.updateEcheance(echeance);

            Incident incident = new Incident(
                    LocalDate.now(),
                    echeance,
                    scoringService.calculerScoreIncident(echeance),
                    StatusPaiement.IMPAYE_NON_REGLE
            );
            incidentDAO.addIncident(incident);
            scoringService.calculerScore(echeance.getCredit().getClient());
        } else {
            echeance.setDatePaiement(datePaiement);

            Long joursRetard = java.time.temporal.ChronoUnit.DAYS.between(echeance.getDateEcheance(), datePaiement);

            if (joursRetard <= 5) echeance.setStatusPaiement(StatusPaiement.PAYE_A_TEMPS);
            else if (joursRetard <= 30) echeance.setStatusPaiement(StatusPaiement.PAYE_EN_RETARD);
            else echeance.setStatusPaiement(StatusPaiement.IMPAYE_REGLE);

            echeanceDAO.updateEcheance(echeance);

            if (echeance.getStatusPaiement() != StatusPaiement.PAYE_A_TEMPS) {
                Incident incident = new Incident(
                        datePaiement,
                        echeance,
                        scoringService.calculerScoreIncident(echeance),
                        echeance.getStatusPaiement()
                );
                incidentDAO.addIncident(incident);
                scoringService.calculerScore(echeance.getCredit().getClient());

            }

            Person client = echeance.getCredit().getClient();
            client.setScore(scoringService.calculerScore(client));
        }
    }

    public List<Echeance> getEcheancesByCredit(Credit credit) {
        return echeanceDAO.getAll().stream()
                .filter(e -> e.getCredit().getId().equals(credit.getId()))
                .collect(Collectors.toList());
    }

    public List<Echeance> getEcheancesEnRetardByClient(String id) {
        return echeanceDAO.getAll().stream()
                .filter(e -> e.getCredit().getClient().getId().equals(id))
                .filter(e -> e.getStatusPaiement() == StatusPaiement.EN_RETARD)
                .collect(Collectors.toList());
    }

}
