package DAO;

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
            String sql = "INSERT INTO professionnels (nom, prenom, date_naissance, ville, nombre_enfants, investissement, placement, situation_familiale, created_at, score, revenue, immatriculation_fiscale, secteur_activite, activite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement p = con.prepareStatement(sql)) {
                p.setString(1, professionnel.getNom());
                p.setString(2, professionnel.getPrenom());
                p.setDate(3, Date.valueOf(professionnel.getDateNaissance()));
                p.setString(4, professionnel.getVille());
                p.setInt(5, professionnel.getNombreEnfants());
                p.setBoolean(6, professionnel.getInvestissement());
                p.setBoolean(7, professionnel.getPlacement());
                p.setString(8, professionnel.getSituationFamiliale());
                p.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                p.setInt(10, professionnel.getScore());
                p.setDouble(11, professionnel.getRevenu());
                p.setString(12, professionnel.getImmatriculationFiscale());
                p.setString(13, professionnel.getSecteurActivite());
                p.setString(14, professionnel.getActivite());
                
                p.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void updateProfessionnel(Professionnel professionnel) {
        String sql = "UPDATE professionnels SET nom=?, prenom=?, date_naissance=?, ville=?, nombre_enfants=?, investissement=?, placement=?, situation_familiale=?, created_at=?, score=?, revenu=?, immatriculation_fiscale=?, secteur_activite=?, activite=? WHERE id=?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, professionnel.getNom());
            stmt.setString(2, professionnel.getPrenom());
            stmt.setDate(3, Date.valueOf(professionnel.getDateNaissance()));
            stmt.setString(4, professionnel.getVille());
            stmt.setInt(5, professionnel.getNombreEnfants());
            stmt.setBoolean(6, professionnel.getInvestissement());
            stmt.setBoolean(7, professionnel.getPlacement());
            stmt.setString(8, professionnel.getSituationFamiliale());
            stmt.setTimestamp(9, Timestamp.valueOf(professionnel.getCreatedAt()));
            stmt.setInt(10, professionnel.getScore());
            stmt.setDouble(11, professionnel.getRevenu());
            stmt.setString(12, professionnel.getImmatriculationFiscale());
            stmt.setString(13, professionnel.getSecteurActivite());
            stmt.setString(14, professionnel.getActivite());
            stmt.setInt(15, professionnel.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void deleteProfessionnel(Integer id) {
            String sql = "DELETE FROM professionnels WHERE id = ?";

            try (PreparedStatement p = con.prepareStatement(sql)){
                p.setInt(1, id);
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
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getDate("date_naissance").toLocalDate(),
                            rs.getString("ville"),
                            rs.getInt("nombre_enfants"),
                            rs.getBoolean("investissement"),
                            rs.getBoolean("placement"),
                            rs.getString("situation_familiale"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getInt("score"),
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
