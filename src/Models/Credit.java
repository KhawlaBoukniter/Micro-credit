package Models;

import Enums.Decision;

import java.time.LocalDate;

public class Credit {
    private Integer id;
    private LocalDate dateCredit;
    private Double montantDemande;
    private Double tauxInteret;
    private Integer dureeMois;
    private String typeCredit;
    private Double montantOctroye;
    private Decision decision;
    private Person client;

    public Credit() {}

    public Credit(LocalDate dateCredit, Double montantDemande, Double tauxInteret, Integer dureeMois, String typeCredit, Double montantOctroye, Decision decision, Person client) {
        this.dateCredit = dateCredit;
        this.montantDemande = montantDemande;
        this.tauxInteret = tauxInteret;
        this.dureeMois = dureeMois;
        this.typeCredit = typeCredit;
        this.montantOctroye = montantOctroye;
        this.decision = decision;
        this.client = client;
    }

    public LocalDate getDateCredit() {
        return dateCredit;
    }

    public void setDateCredit(LocalDate dateCredit) {
        this.dateCredit = dateCredit;
    }

    public Double getMontantDemande() {
        return montantDemande;
    }

    public void setMontantDemande(Double montantDemande) {
        this.montantDemande = montantDemande;
    }

    public Double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(Double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public Integer getDureeMois() {
        return dureeMois;
    }

    public void setDureeMois(Integer dureeMois) {
        this.dureeMois = dureeMois;
    }

    public String getTypeCredit() {
        return typeCredit;
    }

    public void setTypeCredit(String typeCredit) {
        this.typeCredit = typeCredit;
    }

    public Double getMontantOctroye() {
        return montantOctroye;
    }

    public void setMontantOctroye(Double montantOctroye) {
        this.montantOctroye = montantOctroye;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", client=" + (client != null ? client.getNom() + " " + client.getPrenom() : "null") +
                "dateCredit=" + dateCredit +
                ", montantDemande=" + montantDemande +
                ", montantOctroye=" + montantOctroye +
                ", tauxInteret=" + tauxInteret +
                ", dureeMois=" + dureeMois +
                ", typeCredit='" + typeCredit + '\'' +
                ", decision=" + decision +
                '}';
    }
}
