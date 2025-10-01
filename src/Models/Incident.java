package Models;

import Enums.StatusPaiement;

import java.time.LocalDate;
import java.util.UUID;

public class Incident {
    private String id;
    private LocalDate dateIncident;
    private Echeance echeance;
    private Integer score;
    private StatusPaiement typeIncident;

    public Incident() {}

    public Incident(LocalDate dateIncident, Echeance echeance, Integer score, StatusPaiement typeIncident) {
        this.id = UUID.randomUUID().toString().split("-")[0];
        this.dateIncident = dateIncident;
        this.echeance = echeance;
        this.score = score;
        this.typeIncident = typeIncident;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateIncident() {
        return dateIncident;
    }

    public void setDateIncident(LocalDate dateIncident) {
        this.dateIncident = dateIncident;
    }

    public Echeance getEcheance() {
        return echeance;
    }

    public void setEcheance(Echeance echeance) {
        this.echeance = echeance;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public StatusPaiement getTypeIncident() {
        return typeIncident;
    }

    public void setTypeIncident(StatusPaiement typeIncident) {
        this.typeIncident = typeIncident;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "dateIncident=" + dateIncident +
                ", echeance=" + echeance +
                ", score=" + score +
                ", typeIncident=" + typeIncident +
                '}';
    }
}
