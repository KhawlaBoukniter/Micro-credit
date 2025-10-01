package DAO;

import Enums.SituationFamiliale;
import Models.Professionnel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProfessionnelDAO {
    private Connection con;

    public ProfessionnelDAO() {
        this.con = Database.getConnection();
    }

        public void addProfessionnel(Professionnel professionnel) {
            String sql = "INSERT INTO professionnels (id, nom, prenom, date_naissance, ville, nombre_enfants, investissement, placement, situation_familiale, created_at, score, age, revenue, immatriculation_fiscale, secteur_activite, activite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement p = con.prepareStatement(sql)) {
                p.setString(1, professionnel.getId());
                p.setString(2, professionnel.getNom());
                p.setString(3, professionnel.getPrenom());
                p.setDate(4, Date.valueOf(professionnel.getDateNaissance()));
                p.setString(5, professionnel.getVille());
                p.setInt(6, professionnel.getNombreEnfants());
                p.setBoolean(7, professionnel.getInvestissement());
                p.setBoolean(8, professionnel.getPlacement());
                p.setString(9, professionnel.getSituationFamiliale().name());
                p.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                p.setInt(11, professionnel.getScore());
                p.setInt(12, professionnel.getAge());
                p.setDouble(13, professionnel.getRevenu());
                p.setString(14, professionnel.getImmatriculationFiscale());
                p.setString(15, professionnel.getSecteurActivite());
                p.setString(16, professionnel.getActivite());
                
                p.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void updateProfessionnel(Professionnel professionnel) {
        String sql = "UPDATE professionnels SET nom=?, prenom=?, date_naissance=?, ville=?, nombre_enfants=?, investissement=?, placement=?, situation_familiale=?, created_at=?, score=?, age=?, revenu=?, immatriculation_fiscale=?, secteur_activite=?, activite=? WHERE id=?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, professionnel.getNom());
            stmt.setString(2, professionnel.getPrenom());
            stmt.setDate(3, Date.valueOf(professionnel.getDateNaissance()));
            stmt.setString(4, professionnel.getVille());
            stmt.setInt(5, professionnel.getNombreEnfants());
            stmt.setBoolean(6, professionnel.getInvestissement());
            stmt.setBoolean(7, professionnel.getPlacement());
            stmt.setString(8, professionnel.getSituationFamiliale().name());
            stmt.setTimestamp(9, Timestamp.valueOf(professionnel.getCreatedAt()));
            stmt.setInt(10, professionnel.getScore());
            stmt.setInt(11, professionnel.getAge());
            stmt.setDouble(12, professionnel.getRevenu());
            stmt.setString(13, professionnel.getImmatriculationFiscale());
            stmt.setString(14, professionnel.getSecteurActivite());
            stmt.setString(15, professionnel.getActivite());
            stmt.setString(16, professionnel.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void deleteProfessionnel(String id) {
            String sql = "DELETE FROM professionnels WHERE id = ?";

            try (PreparedStatement p = con.prepareStatement(sql)){
                p.setString(1, id);
                p.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<Professionnel> getAll() {
            String sql = "SELECT * FROM professionnels";
            List<Professionnel> professionnels = new ArrayList<>();

            try (PreparedStatement p = con.prepareStatement(sql)) {
                ResultSet rs = p.executeQuery();
                while (rs.next()) {
                    Professionnel professionnel = new Professionnel(
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getString("ville"),
                            rs.getInt("nombre_enfants"),
                            rs.getBoolean("investissement"),
                            rs.getBoolean("placement"),
                            SituationFamiliale.valueOf(rs.getString("situation_familiale")),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getInt("score"),
                            rs.getInt("age"),
                            rs.getDouble("revenu"),
                            rs.getString("immatriculation_fiscale"),
                            rs.getString("secteur_activite"),
                            rs.getString("activite")
                    );
                    professionnels.add(professionnel);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return professionnels;
        }

}
