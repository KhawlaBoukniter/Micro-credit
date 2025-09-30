package Services;

import DAO.ProfessionnelDAO;
import Models.Professionnel;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProfessionnelService {

    private ProfessionnelDAO professionnelDAO;
    private ScoringService scoringService;

    public ProfessionnelService() {
        this.professionnelDAO = new ProfessionnelDAO();
        this.scoringService = new ScoringService();
    }

    public void addProfessionnel(Professionnel p) {
        p.setScore(scoringService.calculScore(p));
        professionnelDAO.addProfessionnel(p);
    }

    public void updateProfessionnel(Professionnel p) {
        p.setScore(scoringService.calculScore(p));
        professionnelDAO.updateProfessionnel(p);
    }

    public void deleteProfessionnel(Integer id) {
        professionnelDAO.deleteProfessionnel(id);
    }

    public List<Professionnel> getAllProfessionnels() {
        return professionnelDAO.getAll();
    }

    public Optional<Professionnel> findProfessionnelById(Integer id) {
        return professionnelDAO.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public List<Professionnel> getSortedProfessionnels() {
        return professionnelDAO.getAll().stream()
                .sorted(Comparator.comparing(Professionnel::getScore).reversed()
                        .thenComparing(Comparator.comparing(Professionnel::getRevenu).reversed()))
                .collect(Collectors.toList());
    }
}
