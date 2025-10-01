package Services;

import DAO.CreditDAO;
import Enums.Decision;
import Models.Credit;
import Models.Employe;
import Models.Person;
import Models.Professionnel;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreditService {
    private CreditDAO creditDAO;
    private ScoringService scoringService;

    public CreditService() {
        this.creditDAO = new CreditDAO();
        this.scoringService = new ScoringService();
    }

    public void addCredit(Credit credit, Person client) {
        client.setScore(scoringService.calculerScore(client));
        Decision decision = determineDecision(client);
        credit.setDecision(decision);
        credit.setClient(client);

        if (decision.equals(Decision.ACCORD_IMMEDIAT)) {
            Double montant = calculerMontantOctroye(client);
            credit.setMontantOctroye(montant);
            creditDAO.addCredit(credit, client.getId());
        } else if (decision.equals(Decision.ETUDE_MANUELLE)) {
            // etude manuelle
        } else {
            // ne pas créer le crédit
        }

    }

    public void updateCredit(Credit credit, Person client) {
        Decision decision = determineDecision(client);
        credit.setDecision(decision);

        if (decision.equals(Decision.ACCORD_IMMEDIAT)) {
            Double montant = calculerMontantOctroye(client);
            credit.setMontantOctroye(montant);

            creditDAO.updateCredit(credit);
        } else if (decision.equals(Decision.ETUDE_MANUELLE)) {
            // etude manuelle
        } else {
            // ne pas modifier
        }
    }

    public void deleteCredit(String id) {
        creditDAO.deleteCredit(id);
    }

    public List<Credit> getAllCredits() {
        return creditDAO.getAll();
    }

    public List<Credit> getCreditsByClient(String id) {
        return creditDAO.getAll().stream()
                .filter(c -> c.getClient() != null && c.getClient().getId().equals(id))
                .collect(Collectors.toList());
    }

    public Optional<Credit> getLatestCreditByClient(String clientId) {
        return getCreditsByClient(clientId).stream()
                .sorted(Comparator.comparing(Credit::getDateCredit).reversed())
                .findFirst();
    }

    private Decision determineDecision(Person client) {
        if (client.getScore() >= 80) return Decision.ACCORD_IMMEDIAT;
        if (client.getCreatedAt().isBefore(LocalDateTime.now().minusYears(2))){
            if (client.getScore() >= 60 && client.getScore() < 80) return Decision.ETUDE_MANUELLE;
            else return Decision.REFUS_AUTOMATIQUE;

        } else {
            if (client.getScore() >= 50 && client.getScore() < 80) return Decision.ETUDE_MANUELLE;
            else return Decision.REFUS_AUTOMATIQUE;
        }
    }

    private Double calculerMontantOctroye(Person client) {
        Double base = 0.0;

        if (client instanceof Employe) {
            Employe employe = (Employe) client;
            base = employe.getSalaire();

            if (client.getScore() >= 80) return base * 10;
            if (client.getScore() >= 70 && client.getCreatedAt().isBefore(LocalDateTime.now().minusYears(2))) return base * 4;
            if (client.getScore() >= 60) return base * 7;
        } else if (client instanceof Professionnel) {
            Professionnel p = (Professionnel) client;
            base = p.getRevenu();

            if (client.getScore() >= 80) return base * 10;
            if (client.getScore() >= 70 && client.getCreatedAt().isBefore(LocalDateTime.now().minusYears(2))) return base * 4;
            if (client.getScore() >= 60) return base * 7;
        }
        return 0.0;
    }

}
