package Models;

import Enums.StatusPaiement;

import java.time.LocalDate;

public class Echeance {
    private Integer id;
    private LocalDate dateEcheance;
    private Double mensualite;
    private LocalDate datePaiement;
    private StatusPaiement statusPaiement;
    private Credit credit;

    public Echeance() {}

    public Echeance(LocalDate dateEcheance, Double mensualite, LocalDate datePaiement, StatusPaiement statusPaiement) {
        this.dateEcheance = dateEcheance;
        this.mensualite = mensualite;
        this.datePaiement = datePaiement;
        this.statusPaiement = statusPaiement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public Double getMensualite() {
        return mensualite;
    }

    public void setMensualite(Double mensualite) {
        this.mensualite = mensualite;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public StatusPaiement getStatusPaiement() {
        return statusPaiement;
    }

    public void setStatusPaiement(StatusPaiement statusPaiement) {
        this.statusPaiement = statusPaiement;
    }

    @Override
    public String toString() {
        return "Echeance{" +
                "dateEcheance=" + dateEcheance +
                ", mensualite=" + mensualite +
                ", datePaiement=" + datePaiement +
                ", statusPaiement=" + statusPaiement +
                '}';
    }
}
