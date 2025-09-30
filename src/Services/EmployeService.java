package Services;

import DAO.EmployeDAO;
import Models.Employe;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeService {

    private EmployeDAO employeDAO;
    private ScoringService scoringService;

    public EmployeService() {
        this.employeDAO = new EmployeDAO();
        this.scoringService = new ScoringService();
    }

    public void addEmploye(Employe e) {
        e.setScore(scoringService.calculScore(e));
        employeDAO.addEmploye(e);
    }

    public void updateEmploye(Employe e) {
        e.setScore(scoringService.calculScore(e));
        employeDAO.updateEmploye(e);
    }

    public void deleteEmploye(Integer id) {
        employeDAO.deleteEmploye(id);
    }

    public List<Employe> getAllEmployes() {
        return employeDAO.getAll();
    }

    public Optional<Employe> findEmployeById(Integer id) {
        return employeDAO.getAll().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<Employe> getSortedEmployes() {
        return employeDAO.getAll().stream()
                .sorted(Comparator.comparing(Employe::getScore).reversed()
                        .thenComparing(Comparator.comparing(Employe::getSalaire).reversed())
                        .thenComparing(Comparator.comparing(Employe::getAnciennete).reversed()))
                .collect(Collectors.toList());
    }

}
