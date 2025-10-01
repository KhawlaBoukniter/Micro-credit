package Models;

import Enums.SituationFamiliale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Person {
    private String id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String ville;
    private Integer nombreEnfants;
    private Boolean investissement;
    private Boolean placement;
    private SituationFamiliale situationFamiliale;
    private LocalDateTime createdAt;
    private Integer score;
    private Integer age;

    public Person() {}

    public Person(String nom, String prenom, LocalDate dateNaissance, String ville, Integer nombreEnfants, Boolean investissement, Boolean placement, SituationFamiliale situationFamiliale, LocalDateTime createdAt, Integer score, Integer age) {
        this.id = UUID.randomUUID().toString().split("-")[0];
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.ville = ville;
        this.nombreEnfants = nombreEnfants;
        this.investissement = investissement;
        this.placement = placement;
        this.situationFamiliale = situationFamiliale;
        this.createdAt = createdAt;
        this.score = score;
        this.age = age;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(Integer nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public Boolean getInvestissement() {
        return investissement;
    }

    public void setInvestissement(Boolean investissement) {
        this.investissement = investissement;
    }

    public Boolean getPlacement() {
        return placement;
    }

    public void setPlacement(Boolean placement) {
        this.placement = placement;
    }

    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", ville='" + ville + '\'' +
                ", nombreEnfants=" + nombreEnfants +
                ", investissement=" + investissement +
                ", placement=" + placement +
                ", situationFamiliale='" + situationFamiliale + '\'' +
                ", createdAt=" + createdAt +
                ", score=" + score +
                ", age=" + age +
                '}';
    }
}
