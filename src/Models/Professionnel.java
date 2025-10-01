package Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Professionnel extends Person {
    private Double revenu;
    private String immatriculationFiscale;
    private String secteurActivite;
    private String activite;

    public Professionnel(Double revenu, String immatriculationFiscale, String secteurActivite, String activite) {
        this.revenu = revenu;
        this.immatriculationFiscale = immatriculationFiscale;
        this.secteurActivite = secteurActivite;
        this.activite = activite;
    }

    public Professionnel(String nom, String prenom, LocalDate dateNaissance, String ville, Integer nombreEnfants, Boolean investissement, Boolean placement, String situationFamiliale, LocalDateTime createdAt, Integer score, Double revenu, String immatriculationFiscale, String secteurActivite, String activite) {
        super(nom, prenom, dateNaissance, ville, nombreEnfants, investissement, placement, situationFamiliale, createdAt, score);
        this.revenu = revenu;
        this.immatriculationFiscale = immatriculationFiscale;
        this.secteurActivite = secteurActivite;
        this.activite = activite;
    }

    public Double getRevenu() {
        return revenu;
    }

    public void setRevenu(Double revenu) {
        this.revenu = revenu;
    }

    public String getImmatriculationFiscale() {
        return immatriculationFiscale;
    }

    public void setImmatriculationFiscale(String immatriculationFiscale) {
        this.immatriculationFiscale = immatriculationFiscale;
    }

    public String getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    @Override
    public String toString() {
        return "Professionnel{" +
                "id=" + this.getId() +
                "revenu=" + revenu +
                ", immatriculationFiscale='" + immatriculationFiscale + '\'' +
                ", secteurActivite='" + secteurActivite + '\'' +
                ", activite='" + activite + '\'' +
                '}';
    }
}
