package Models;

import Enums.SituationFamiliale;
import Enums.TypeContrat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Professionnel extends Person {
    private Double revenu;
    private String immatriculationFiscale;
    private String secteurActivite;
    private String activite;
    private TypeContrat statut_professionnel;

    public Professionnel(Double revenu, String immatriculationFiscale, String secteurActivite, String activite, TypeContrat statut_professionnel) {
        this.revenu = revenu;
        this.immatriculationFiscale = immatriculationFiscale;
        this.secteurActivite = secteurActivite;
        this.activite = activite;
        this.statut_professionnel = statut_professionnel;
    }

    public Professionnel(String nom, String prenom, LocalDate dateNaissance, String ville, Integer nombreEnfants, Boolean investissement, Boolean placement, SituationFamiliale situationFamiliale, LocalDateTime createdAt, Integer score, Integer age, Double revenu, String immatriculationFiscale, String secteurActivite, String activite, TypeContrat statut_professionnel) {
        super(nom, prenom, dateNaissance, ville, nombreEnfants, investissement, placement, situationFamiliale, createdAt, score, age);
        this.revenu = revenu;
        this.immatriculationFiscale = immatriculationFiscale;
        this.secteurActivite = secteurActivite;
        this.activite = activite;
        this.statut_professionnel = statut_professionnel;
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

    public TypeContrat getStatut_professionnel() {
        return statut_professionnel;
    }

    public void setStatut_professionnel(TypeContrat statut_professionnel) {
        this.statut_professionnel = statut_professionnel;
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
