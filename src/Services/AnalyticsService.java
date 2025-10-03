package Services;

import DAO.EcheanceDAO;
import DAO.EmployeDAO;
import DAO.IncidentDAO;
import Enums.Decision;
import Enums.SituationFamiliale;
import Enums.TypeContrat;
import Models.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsService {
    private EmployeService employeService;
    private ProfessionnelService professionnelService;
    private IncidentService incidentService;
    private CreditService creditService;

    public AnalyticsService(EmployeService employeService, ProfessionnelService professionnelService, IncidentService incidentService, CreditService creditService) {
        this.employeService = employeService;
        this.professionnelService = professionnelService;
        this.incidentService = incidentService;
        this.creditService = creditService;
    }

    public List<Person> clientsEligiblesImmobilier() {
        List<Person> allClients = new ArrayList<>();
        allClients.addAll(employeService.getAllEmployes());
        allClients.addAll(professionnelService.getAllProfessionnels());

        return allClients.stream()
                .filter(c -> c.getAge() >= 25 && c.getAge() <= 50)
                .filter(c -> c.getScore() != null && c.getScore() > 70)
                .filter(c -> c.getSituationFamiliale().equals(SituationFamiliale.MARIE))
                .filter(c -> {
                    if (c instanceof Employe) {
                        TypeContrat typeContrat = ((Employe) c).getTypeContrat();
                        return typeContrat == TypeContrat.CDI_PUBLIC || typeContrat == TypeContrat.CDI_PRIVE_GRANDE_ENTREPRISE || typeContrat == TypeContrat.CDI_PRIVE_PME;
                    } else if (c instanceof Professionnel) {
                        TypeContrat typeContrat = ((Professionnel) c).getStatut_professionnel();
                        return typeContrat == TypeContrat.PROFESSION_LIBERALE_STABLE || typeContrat == TypeContrat.AUTO_ENTREPRENEUR;
                    } return false;
                })
                .filter(c -> {
                    if (c instanceof Employe) return ((Employe) c).getSalaire() > 4000;
                    if (c instanceof Professionnel) return ((Professionnel) c).getRevenu() > 4000;
                    return false;
                })
                .collect(Collectors.toList());
    }

    public List<Person> getRiskClients() {
        List<Person> allClients = new ArrayList<>();
        allClients.addAll(employeService.getAllEmployes());
        allClients.addAll(professionnelService.getAllProfessionnels());

        return allClients.stream()
                .filter(c -> c.getScore() != null && c.getScore() < 60)
                .filter(c -> !incidentService.getRecentIncidents(c).isEmpty())
                .sorted(Comparator.comparing(Person::getScore)).limit(10)
                .collect(Collectors.toList());
    }

    public List<Person> getClientsSorted() {
        List<Person> allClients = new ArrayList<>();
        allClients.addAll(employeService.getAllEmployes());
        allClients.addAll(professionnelService.getAllProfessionnels());

        return allClients.stream()
                .sorted(Comparator.comparing(Person::getScore).reversed()
                        .thenComparing(c -> {
                            if (c instanceof Employe) return ((Employe) c).getSalaire();
                            if (c instanceof Professionnel) return ((Professionnel) c).getRevenu();
                            return 0.0;
                        }, Comparator.reverseOrder())
                        .thenComparing(Person::getCreatedAt, Comparator.reverseOrder())
                )
                .collect(Collectors.toList());
    }

    public Map<TypeContrat, Map<String, Double>> getClientsByEmploi() {
        List<Person> allClients = new ArrayList<>();
        allClients.addAll(employeService.getAllEmployes());
        allClients.addAll(professionnelService.getAllProfessionnels());

        Map<TypeContrat, List<Person>> allClientsGrouped = allClients.stream()
                .filter(c -> c instanceof Employe || c instanceof Professionnel)
                .collect(Collectors.groupingBy(c -> {
                    if (c instanceof Employe) return ((Employe) c).getTypeContrat();
                    else return ((Professionnel) c).getStatut_professionnel();
                }));

        Map<TypeContrat, Map<String, Double>> stats = new HashMap<>();
        allClientsGrouped.forEach((typeContrat, clients) -> {
            Integer nbrClients = clients.size();
            Double scoreMoyen = clients.stream().mapToInt(Person::getScore).average().orElse(0);
            Double revenusMoyen = clients.stream().mapToDouble(c -> {
                if (c instanceof Employe) return ((Employe) c).getSalaire();
                if (c instanceof Professionnel) return ((Professionnel) c).getRevenu();
                return 0;
            }).average().orElse(0);

            Long nbrCreditsAcc = clients.stream()
                    .flatMap(c -> creditService.getCreditsByClient(c.getId()).stream())
                    .filter(cr -> cr.getDecision().equals(Decision.ACCORD_IMMEDIAT))
                    .count();

            Double taux = nbrClients > 0 ? (nbrCreditsAcc * 100.0 / nbrClients) : 0;

            Map<String, Double> subStats = new HashMap<>();
            subStats.put("nbrClients", (double) nbrClients);
            subStats.put("scoreMoyen", scoreMoyen);
            subStats.put("revenusMoyens", revenusMoyen);
            subStats.put("taux", taux);

            stats.put(typeContrat, subStats);
        });

        return stats;
    }
}
