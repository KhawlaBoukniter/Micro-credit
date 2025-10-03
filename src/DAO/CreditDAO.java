package DAO;

import Enums.Decision;
import Enums.Secteur;
import Enums.SituationFamiliale;
import Enums.TypeContrat;
import Models.Credit;
import Models.Employe;
import Models.Person;
import Models.Professionnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditDAO {
    private Connection con;

    public CreditDAO() {
        this.con = Database.getConnection();
    }

    public void addCredit(Credit credit, String clientId) {
        String sql = "INSERT INTO credits (id, client_id, date_credit, montant_demande, montant_octroye, taux_interet, duree_mois, type_credit, decision) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, credit.getId());
            p.setString(2, clientId);
            p.setDate(3, Date.valueOf(credit.getDateCredit()));
            p.setDouble(4, credit.getMontantDemande());
            p.setDouble(5, credit.getMontantOctroye());
            p.setDouble(6, credit.getTauxInteret());
            p.setInt(7, credit.getDureeMois());
            p.setString(8, credit.getTypeCredit());
            p.setString(9, credit.getDecision().name());

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCredit(Credit credit) {
        String sql = "UPDATE credits SET date_credit=?, montant_demande=?, montant_octroye=?, taux_interet=?, duree_mois=?, type_credit=?, decision=? " +
                "WHERE id=?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setDate(1, Date.valueOf(credit.getDateCredit()));
            p.setDouble(2, credit.getMontantDemande());
            p.setDouble(3, credit.getMontantOctroye());
            p.setDouble(4, credit.getTauxInteret());
            p.setInt(5, credit.getDureeMois());
            p.setString(6, credit.getTypeCredit());
            p.setString(7, credit.getDecision().name());
            p.setString(8, credit.getId());

            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCredit(String creditId) {
        String sql = "DELETE FROM credits WHERE id=?";
        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, creditId);
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Credit> getAll() {
        String sql = "SELECT * FROM credits";
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
                credit.setId(rs.getString("id"));

                credit.setClient(hydrateClient(rs.getString("client_id")));
                credits.add(credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return credits;
    }

    private Person hydrateClient(String clientId) {
        try  {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM employes WHERE id = ?");
            ps.setString(1, clientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employe e = new Employe(
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
                        rs.getDouble("salaire"),
                        rs.getInt("anciennete"),
                        rs.getString("poste"),
                        TypeContrat.valueOf(rs.getString("type_contrat")),
                        Secteur.valueOf(rs.getString("secteur").toUpperCase())
                );
                e.setId(rs.getString("id"));
                return e;
            }

            ps = con.prepareStatement("SELECT * FROM professionnels WHERE id = ?");
            ps.setString(1, clientId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Professionnel p = new Professionnel(
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
                        rs.getString("activite"),
                        TypeContrat.valueOf(rs.getString("statut_professionnel"))
                );
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Credit> getByClient(String clientId) {
        String sql = "SELECT * FROM credits WHERE client_id=?";
        List<Credit> credits = new ArrayList<>();

        try (PreparedStatement p = con.prepareStatement(sql)) {
            p.setString(1, clientId);
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
                 credit.setId(rs.getString("id"));
                 credit.setClient(hydrateClient(clientId));
                 credits.add(credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return credits;
    }

    public Credit getById(String id) {
        String sql = "SELECT * FROM credits WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Credit credit = new Credit(
                        rs.getDate("date_credit").toLocalDate(),
                        rs.getDouble("montant_demande"),
                        rs.getDouble("taux_interet"),
                        rs.getInt("duree_mois"),
                        rs.getString("type_credit"),
                        rs.getDouble("montant_octroye"),
                        Decision.valueOf(rs.getString("decision"))
                );
                credit.setId(id);
                credit.setClient(hydrateClient(rs.getString("client_id")));
                return credit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
