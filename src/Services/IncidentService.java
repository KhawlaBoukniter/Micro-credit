package Services;

import DAO.IncidentDAO;
import Models.Incident;
import Models.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class IncidentService {

        private IncidentDAO incidentDAO;

        public IncidentService() {
            this.incidentDAO = new IncidentDAO();
        }

        public void addIncident(Incident incident) {
            incidentDAO.addIncident(incident);
        }

        public void deleteIncident(String id) {
            incidentDAO.deleteIncident(id);
        }

        public List<Incident> getAllIncidents() {
            return incidentDAO.getAll();
        }

        public List<Incident> getIncidentsByClient(Person client) {
            return incidentDAO.getAll().stream()
                    .filter(i -> i.getEcheance().getCredit().getClient().equals(client))
                    .collect(Collectors.toList());
        }

        public List<Incident> getRecentIncidents(Person client) {
            LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
            return incidentDAO.getAll().stream()
                    .filter(i -> i.getDateIncident().isAfter(sixMonthsAgo))
                    .collect(Collectors.toList());
        }

        public List<Incident> getTopClientsAtRisk() {
            return incidentDAO.getAll().stream()
                    .sorted((i1, i2) -> i2.getScore().compareTo(i1.getScore()))
                    .limit(10)
                    .collect(Collectors.toList());
        }


}
