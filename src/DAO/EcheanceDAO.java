package DAO;

import Models.Echeance;
import Models.Credit;
import Enums.StatusPaiement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EcheanceDAO {
    private Connection con;

    public EcheanceDAO() {
        this.con = Database.getConnection();
    }

    public void addEcheance(Echeance echeance) {
        String sql = "INSERT INTO echeances (credit_id, date_echeance, mensualite, date_paiement, status_paiement) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, echeance.getCredit().getId());
            p.setDate(2, Date.valueOf(echeance.getDateEcheance()));
            p.setDouble(3, echeance.getMensualite());
            p.setDate(4, Date.valueOf(echeance.getDatePaiement()));
            p.setString(6, echeance.getStatusPaiement().name());

            p.executeUpdate();

            ResultSet rs = p.getGeneratedKeys();
            if (rs.next()) {
                echeance.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEcheance(Echeance echeance) {
        String sql = "UPDATE echeances SET credit_id=?, date_echeance=?, mensualite=?, date_paiement=?, status_paiement=? WHERE id=?";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, echeance.getCredit().getId());
            p.setDate(2, Date.valueOf(echeance.getDateEcheance()));
            p.setDouble(3, echeance.getMensualite());
            p.setDate(4, Date.valueOf(echeance.getDatePaiement()));
            p.setString(5, echeance.getStatusPaiement().name());
            p.setInt(6, echeance.getId());

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEcheance(Integer id) {
        String sql = "DELETE FROM echeances WHERE id=?";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Echeance> getAll() {
        String sql = "SELECT * FROM echeances";
        List<Echeance> echeances = new ArrayList<>();

        try (PreparedStatement p = con.prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            CreditDAO creditDAO = new CreditDAO();
            while (rs.next()) {
                Credit credit = creditDAO.getById(rs.getInt("credit_id"));
                Echeance e = new Echeance(
                        rs.getDate("date_echeance").toLocalDate(),
                        rs.getDouble("mensualite"),
                        rs.getDate("date_paiement").toLocalDate(),
                        StatusPaiement.valueOf(rs.getString("status_paiement"))
                );
                e.setId(rs.getInt("id"));
                e.setCredit(credit);
                echeances.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return echeances;
    }

    public Echeance getById(Integer id) {
        String sql = "SELECT * FROM echeances WHERE id=?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                CreditDAO creditDAO = new CreditDAO();
                Credit credit = creditDAO.getById(rs.getInt("credit_id"));
                Echeance e = new Echeance(
                        rs.getDate("date_echeance").toLocalDate(),
                        rs.getDouble("mensualite"),
                        rs.getDate("date_paiement") != null ? rs.getDate("date_paiement").toLocalDate() : null,
                        StatusPaiement.valueOf(rs.getString("status_paiement"))
                );
                e.setId(rs.getInt("id"));
                e.setCredit(credit);
                return e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
