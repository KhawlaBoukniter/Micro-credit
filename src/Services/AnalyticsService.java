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

    public AnalyticsService() {
        this.employeService = new EmployeService();
        this.professionnelService = new ProfessionnelService();
        this.incidentService = new IncidentService();
        this.creditService = new CreditService();
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

}
