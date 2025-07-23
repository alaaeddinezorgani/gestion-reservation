package backend;
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 * 
 */
public class Admin extends Gestionnaire {

    public Admin(Connection conn, String nom, String prenom, String email, String mot_de_passe) throws SQLException {
    	String selectMaxIdSQL = "SELECT MAX(id_utilisateur) FROM Utilisateur";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectMaxIdSQL)) {
            if (rs.next()) {
                super.setIdUtilisateur(rs.getInt(1) + 1); // Increment the max id by 1
            } else {
                super.setIdUtilisateur(1); // Default to 1 if no records exist
            }
        }
        super.setNom(nom);
        super.setPrenom(prenom);
        super.setEmail(email);
        super.setMotDePasse(mot_de_passe);
        super.setRole("admin");
    	String insertSQL = "INSERT INTO Utilisateur(id_utilisateur, nom, prenom, email, mot_de_passe, role) VALUES(?,?,?,?,?,?)";
    	PreparedStatement stmt = conn.prepareStatement(insertSQL);
    	stmt.setInt(1, this.getIdUtilisateur());
    	stmt.setString(2, this.getNom());
    	stmt.setString(3, this.getPrenom());
    	stmt.setString(4, this.getEmail());
    	stmt.setString(5, this.getMotDePasse());
    	stmt.setString(6, this.getRole());
    	stmt.executeUpdate();
    }

   /*
    public void creeCompte(Connection conn, int id_utilisateur, String nom, String prenom, String email, String mot_de_passe, String role) throws SQLException {
    	String insertSQL = "INSERT INTO Utilisateur(id_utilisateur, nom, prenom, email, mot_de_passe, role) VALUES(?,?,?,?,?,?)";
    	PreparedStatement stmt = conn.prepareStatement(insertSQL);
    	stmt.setInt(1, id_utilisateur);
    	stmt.setString(2, nom);
    	stmt.setString(3, prenom);
    	stmt.setString(4, email);
    	stmt.setString(5, mot_de_passe);
    	stmt.setString(6, role);
    	stmt.executeUpdate();
    }
    */
    


    public void gererPermission(Connection conn, int id_utilisateur , String newRole) throws SQLException {
        String updateSQL = "UPDATE Utilisateur SET role = ? WHERE id_utilisateur = ?";
        PreparedStatement stmt = conn.prepareStatement(updateSQL);
        stmt.setString(1, newRole);
        stmt.setInt(2, id_utilisateur);
        stmt.executeUpdate();        
    }
   
/*
public void gererPermission(Connection conn, Gestionnaire g , String newRole) throws SQLException {
	String updateSQL = "UPDATE Utilisateur SET role = ? WHERE id_utilisateur = ?";
    PreparedStatement stmt = conn.prepareStatement(updateSQL);
    stmt.setString(1, newRole);
    stmt.setInt(2, g.getIdUtilisateur());
    stmt.executeUpdate();        
}
*/
    
    public static List<Gestionnaire> getAllUtilisateurs(Connection conn) throws SQLException {
        List<Gestionnaire> utilisateurs = new ArrayList<>();
        String selectSQL = "SELECT * FROM Utilisateur";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                int id_utilisateur = rs.getInt("id_utilisateur");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String mot_de_passe = rs.getString("mot_de_passe");
                String role = rs.getString("role");
                
                // Create a new Client object for each row in the result set
                Gestionnaire utilisateur = new Gestionnaire(id_utilisateur, nom, prenom, email, mot_de_passe, role);
                utilisateurs.add(utilisateur);
            }
        }

        return utilisateurs;
    }
    
    public static void deleteUtilisateur(Connection conn, int id_utilisateur )throws SQLException {
    	String request = "DELETE FROM Utilisateur WHERE id_utilisateur = ?";
    	PreparedStatement stmt = conn.prepareStatement(request);
    	stmt.setInt(1, id_utilisateur);
    	stmt.execute();
    	
    	
    }
}
