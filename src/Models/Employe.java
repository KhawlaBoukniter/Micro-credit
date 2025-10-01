package Models;

import Enums.Secteur;
import Enums.TypeContrat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employe extends Person {
    private Double salaire;
    private Integer anciennete;
    private String poste;
    private TypeContrat typeContrat;
    private Secteur secteur;

    public Employe() {}

    public Employe(Double salaire, Integer anciennete, String poste, TypeContrat typeContrat, Secteur secteur) {
        this.salaire = salaire;
        this.anciennete = anciennete;
        this.poste = poste;
        this.typeContrat = typeContrat;
        this.secteur = secteur;
    }

    public Employe(String nom, String prenom, LocalDate dateNaissance, String ville, Integer nombreEnfants, Boolean investissement, Boolean placement, String situationFamiliale, LocalDateTime createdAt, Integer score, Double salaire, Integer anciennete, String poste, TypeContrat typeContrat, Secteur secteur) {
        super(nom, prenom, dateNaissance, ville, nombreEnfants, investissement, placement, situationFamiliale, createdAt, score);
        this.salaire = salaire;
        this.anciennete = anciennete;
        this.poste = poste;
        this.typeContrat = typeContrat;
        this.secteur = secteur;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Integer getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(Integer anciennete) {
        this.anciennete = anciennete;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + this.getId() +
                ", nom='" + this.getNom() + '\'' +
                ", prenom='" + this.getPrenom() + '\'' +
                ", salaire=" + salaire +
                ", anciennete=" + anciennete +
                ", poste='" + poste + '\'' +
                ", typeContrat='" + typeContrat + '\'' +
                ", secteur='" + secteur + '\'' +
                '}';
    }
}
