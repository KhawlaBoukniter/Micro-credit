package DAO;

import Enums.StatusPaiement;
import Models.Echeance;
import Models.Incident;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncidentDAO {
    private Connection con;
    private EcheanceDAO echeanceDAO;

    public IncidentDAO() {
        this.con = Database.getConnection();
        this.echeanceDAO = new EcheanceDAO();
    }

    public void addIncident(Incident incident) {
        String sql = "INSERT INTO incident (date_incident, score, echeance_id, type_incident) VALUES (?, ?, ?, ?)";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setDate(1, Date.valueOf(incident.getDateIncident()));
            p.setInt(2, incident.getScore());
            p.setInt(3, incident.getEcheance().getId());
            p.setString(4, incident.getTypeIncident().name());

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIncident(Incident incident) {
        String sql = "UPDATE incident SET date_incident=?, score=?, echeance_id=?, type_incident=? WHERE id=?";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setDate(1, Date.valueOf(incident.getDateIncident()));
            p.setInt(2, incident.getScore());
            p.setInt(3, incident.getEcheance().getId());
            p.setString(4, incident.getTypeIncident().name());
            p.setInt(6, incident.getEcheance().getId());

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIncident(Integer id) {
        String sql = "DELETE FROM incident WHERE id=?";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Incident> getAll() {
        String sql = "SELECT * FROM incident";
        List<Incident> incidents = new ArrayList<>();

        try (PreparedStatement p = con.prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Echeance echeance = echeanceDAO.getById(rs.getInt("echeance_id"));

                Incident incident = new Incident(
                        rs.getDate("date_incident").toLocalDate(),
                        echeance,
                        rs.getInt("score"),
                        StatusPaiement.valueOf(rs.getString("type_incident"))
                );

                incidents.add(incident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incidents;
    }

    public Incident getById(Integer id) {
        String sql = "SELECT * FROM incident WHERE id=?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                Echeance echeance = echeanceDAO.getById(rs.getInt("echeance_id"));
                return new Incident(
                        rs.getDate("date_incident").toLocalDate(),
                        echeance,
                        rs.getInt("score"),
                        StatusPaiement.valueOf(rs.getString("type_incident"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
