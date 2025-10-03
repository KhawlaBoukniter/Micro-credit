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

    public EmployeService(EmployeDAO employeDAO, ScoringService scoringService) {
        this.employeDAO = employeDAO;
        this.scoringService = scoringService;
    }

    public void addEmploye(Employe e) {
        Integer score = scoringService.calculerScore(e);
        e.setScore(score);
        employeDAO.addEmploye(e);
    }

    public void updateEmploye(Employe e) {
        e.setScore(scoringService.calculerScore(e));
        employeDAO.updateEmploye(e);
    }

    public void deleteEmploye(String id) {
        employeDAO.deleteEmploye(id);
    }

    public List<Employe> getAllEmployes() {
        return employeDAO.getAll();
    }

    public Employe findEmployeById(String id) {
        return employeDAO.getAll().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().get();
    }

    public List<Employe> getSortedEmployes() {
        return employeDAO.getAll().stream()
                .sorted(Comparator.comparing(Employe::getScore).reversed()
                        .thenComparing(Comparator.comparing(Employe::getSalaire).reversed())
                        .thenComparing(Comparator.comparing(Employe::getAnciennete).reversed()))
                .collect(Collectors.toList());
    }


}
