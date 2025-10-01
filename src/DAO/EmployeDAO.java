package DAO;

import Enums.Secteur;
import Enums.TypeContrat;
import Models.Employe;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAO {
    private Connection con;

    public EmployeDAO() {
        this.con = Database.getConnection();
    }

    public void addEmploye(Employe employe) {
        String sql = "INSERT INTO employes (nom, prenom, date_naissance, ville, nombre_enfants, investissement, placement, situation_familiale, created_at, score, salaire, anciennete, poste, type_contrat, secteur) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, employe.getNom());
            p.setString(2, employe.getPrenom());
            p.setDate(3, Date.valueOf(employe.getDateNaissance()));
            p.setString(4, employe.getVille());
            p.setInt(5, employe.getNombreEnfants());
            p.setBoolean(6, employe.getInvestissement());
            p.setBoolean(7, employe.getPlacement());
            p.setString(8, employe.getSituationFamiliale());
            p.setTimestamp(9,  Timestamp.valueOf(LocalDateTime.now()));
            p.setInt(10, employe.getScore());
            p.setDouble(11, employe.getSalaire());
            p.setInt(12, employe.getAnciennete());
            p.setString(13, employe.getPoste());
            p.setObject(14, employe.getTypeContrat());
            p.setObject(15, employe.getSecteur());

            p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmploye(Employe employe) {
        String sql = "UPDATE employes SET nom=?, prenom=?, date_naissance=?, ville=?, nombre_enfants=?, investissement=?, placement=?, situation_familiale=?, created_at=?, score=?, salaire=?, anciennete=?, poste=?, type_contrat=?, secteur=? WHERE id=?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, employe.getNom());
            stmt.setString(2, employe.getPrenom());
            stmt.setDate(3, Date.valueOf(employe.getDateNaissance()));
            stmt.setString(4, employe.getVille());
            stmt.setInt(5, employe.getNombreEnfants());
            stmt.setBoolean(6, employe.getInvestissement());
            stmt.setBoolean(7, employe.getPlacement());
            stmt.setString(8, employe.getSituationFamiliale());
            stmt.setTimestamp(9, Timestamp.valueOf(employe.getCreatedAt()));
            stmt.setInt(10, employe.getScore());
            stmt.setDouble(11, employe.getSalaire());
            stmt.setInt(12, employe.getAnciennete());
            stmt.setString(13, employe.getPoste());
            stmt.setString(14, employe.getTypeContrat().name());
            stmt.setString(15, employe.getSecteur().name());
            stmt.setString(16, employe.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmploye(String id) {
        String sql = "DELETE FROM employes WHERE id = ?";

        try (PreparedStatement p = con.prepareStatement(sql)){
            p.setString(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Employe> getAll() {
        String sql = "SELECT * FROM employes";
        List<Employe> employes = new ArrayList<>();

        try (PreparedStatement p = con.prepareStatement(sql)) {
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Employe employe = new Employe(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getDate("date_naissance").toLocalDate(),
                    rs.getString("ville"),
                    rs.getInt("nombre_enfants"),
                    rs.getBoolean("investissement"),
                    rs.getBoolean("placement"),
                    rs.getString("situation_familiale"), rs.getTimestamp("created_at").toLocalDateTime(), rs.getInt("score"),
                    rs.getDouble("salaire"),
                    rs.getInt("anciennete"),
                    rs.getString("poste"),
                    TypeContrat.valueOf(rs.getString("type_contrat")),
                    Secteur.valueOf(rs.getString("secteur"))
                );
                employe.setId(rs.getString("id"));
                employes.add(employe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employes;
    }
}
