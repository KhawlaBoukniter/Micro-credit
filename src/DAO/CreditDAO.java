package DAO;

import Enums.Decision;
import Models.Credit;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditDAO {
    private Connection con;

    public CreditDAO() {
        this.con = Database.getConnection();
    }

    public void addCredit(Credit credit, Integer clientId) {
        String sql = "INSERT INTO credit (client_id, date_credit, montant_demande, montant_octroye, taux_interet, duree_mois, type_credit, decision) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, clientId);
            p.setDate(2, Date.valueOf(credit.getDateCredit()));
            p.setDouble(3, credit.getMontantDemande());
            p.setDouble(4, credit.getMontantOctroye());
            p.setDouble(5, credit.getTauxInteret());
            p.setInt(6, credit.getDureeMois());
            p.setString(7, credit.getTypeCredit());
            p.setString(8, credit.getDecision().name());

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCredit(Credit credit, String clientType, Integer clientId) {
        String sql = "UPDATE credit SET date_credit=?, montant_demande=?, montant_octroye=?, taux_interet=?, duree_mois=?, type_credit=?, decision=? " +
                "WHERE client_id=?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setDate(1, Date.valueOf(credit.getDateCredit()));
            p.setDouble(2, credit.getMontantDemande());
            p.setDouble(3, credit.getMontantOctroye());
            p.setDouble(4, credit.getTauxInteret());
            p.setInt(5, credit.getDureeMois());
            p.setString(6, credit.getTypeCredit());
            p.setString(7, credit.getDecision().name());
            p.setInt(8, clientId);

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCredit(String clientType, Integer clientId) {
        String sql = "DELETE FROM credit WHERE client_id=?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, clientType);
            p.setInt(2, clientId);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Credit> getAll() {
        String sql = "SELECT * FROM credit";
        List<Credit> credits = new ArrayList<>();

        try (PreparedStatement p = con.prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Credit credit = new Credit(
                        rs.getDate("date_credit").toLocalDate(),
                        rs.getDouble("montant_demande"),
                        rs.getDouble("taux_interet"),
                        rs.getInt("duree_mois"),
                        rs.getString("type_credit"),
                        rs.getDouble("montant_octroye"),
                        Decision.valueOf(rs.getString("decision"))
                );
                credits.add(credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return credits;
    }

    public Credit getByClient(String clientType, Integer clientId) {
        String sql = "SELECT * FROM credit WHERE client_id=?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, clientId);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return new Credit(
                        rs.getDate("date_credit").toLocalDate(),
                        rs.getDouble("montant_demande"),
                        rs.getDouble("taux_interet"),
                        rs.getInt("duree_mois"),
                        rs.getString("type_credit"),
                        rs.getDouble("montant_octroye"),
                        Decision.valueOf(rs.getString("decision"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
