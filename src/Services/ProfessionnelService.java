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

    public ProfessionnelService(ProfessionnelDAO professionnelDAO, ScoringService scoringService) {
        this.professionnelDAO = professionnelDAO;
        this.scoringService = scoringService;
    }

    public void addProfessionnel(Professionnel p) {
        p.setScore(scoringService.calculerScore(p));
        professionnelDAO.addProfessionnel(p);
    }

    public void updateProfessionnel(Professionnel p) {
        p.setScore(scoringService.calculerScore(p));
        professionnelDAO.updateProfessionnel(p);
    }

    public void deleteProfessionnel(String id) {
        professionnelDAO.deleteProfessionnel(id);
    }

    public List<Professionnel> getAllProfessionnels() {
        return professionnelDAO.getAll();
    }

    public Professionnel findProfessionnelById(String id) {
        return professionnelDAO.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Professionnel> getSortedProfessionnels() {
        return professionnelDAO.getAll().stream()
                .sorted(Comparator.comparing(Professionnel::getScore).reversed()
                        .thenComparing(Comparator.comparing(Professionnel::getRevenu).reversed()))
                .collect(Collectors.toList());
    }
}
