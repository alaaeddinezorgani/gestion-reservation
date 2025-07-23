package backend;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 
 */
public class Gestionnaire extends Personne {

    private int id_utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    private String mot_de_passe;
    
    public Gestionnaire() {}
  public Gestionnaire(Connection conn, String nom, String prenom, String email, String mot_de_passe) throws SQLException  {
	  String selectMaxIdSQL = "SELECT MAX(id_utilisateur) FROM Utilisateur";
      try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectMaxIdSQL)) {
          if (rs.next()) {
              this.setIdUtilisateur(rs.getInt(1) + 1); // Increment the max id by 1
          } else {
              this.setIdUtilisateur(1); // Default to 1 if no records exist
          }
      }
      this.setNom(nom);
      this.setPrenom(prenom);
      this.setEmail(email);
      this.setMotDePasse(mot_de_passe);
      this.setRole("gestionnaire");
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
  
    public Gestionnaire(int id_utilisateur, String nom, String prenom, String email, String mot_de_passe,
		String role) {
	this.id_utilisateur = id_utilisateur;
	this.nom = nom;
	this.prenom = prenom;
	this.email = email;
	this.mot_de_passe = mot_de_passe;
	this.role = role;
}
	public Gestionnaire(String nom2, String prenom2, String email2, String mot_de_passe2) {
		this.nom = nom2;
		this.prenom = prenom2;
		this.email = email2;
		this.mot_de_passe = mot_de_passe2;
	}
	public int getIdUtilisateur() {
        return id_utilisateur;
    }
    public void setIdUtilisateur(int newId_utilisateur) {
        this.id_utilisateur = newId_utilisateur;
    }
    public String getRole() {
        return this.role;
    }

    public void setRole(String newRole) {
        this.role = newRole;
    }

    public String getMotDePasse() {
        return this.mot_de_passe;
    }

    public void setMotDePasse(String newMot_de_passe) {
        this.mot_de_passe = newMot_de_passe;
    }
    public String getEmail() {
    	return this.email;
    }
    public void setEmail(String newEmail) {
    	this.email = newEmail;
    }
    public void setNom(String nom) {
    	this.nom = nom;
    }
    public String getNom() {
    	return this.nom;
    }
    public void setPrenom(String prenom) {
    	this.prenom = prenom;
    }
    public String getPrenom() {
    	return this.prenom;
    }
    
    public static Gestionnaire getGestionnaire(Connection conn, int id_utilisateur) throws SQLException {
    	String selectSQL = "SELECT * FROM Utilisateur WHERE id_utilisateur =?";
        PreparedStatement stmt = conn.prepareStatement(selectSQL);
        stmt.setInt(1, id_utilisateur);
        ResultSet rs = stmt.executeQuery();
           String nom = rs.getString("nom");
           String prenom = rs.getString("prenom");
           String mot_de_passe = rs.getString("mot_de_passe");
           String email = rs.getString("email");
           Gestionnaire gestionnaire = new Gestionnaire(nom, prenom, email, mot_de_passe);
           
    return gestionnaire;

    }
    
    public static void modifierGestionnaire(Connection conn, int id_utilisateur, String nom,String prenom,String email,String mot_de_passe,String role)throws SQLException {
        String updateSQL = "UPDATE Utilisateur SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, role = ? WHERE id_utilisateur = ?";
        PreparedStatement stmt = conn.prepareStatement(updateSQL);
        stmt.setString(1, nom);
        stmt.setString(2, prenom);
        stmt.setString(3, email + " ");
        stmt.setString(4, mot_de_passe);
        stmt.setString(5, role);
        stmt.setInt(6, id_utilisateur);
        stmt.executeUpdate();
    }
    
    public static ArrayList<Gestionnaire> searchCompte(Connection conn, String name) {
        ArrayList<Gestionnaire> gestionnaires = new ArrayList<>();
        String query = "SELECT * FROM Utilisateur WHERE nom LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameter in the query (e.g., name with wildcard for partial match)
            stmt.setString(1, "%" + name + "%");

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                // Iterate through the result set and populate the list
                while (rs.next()) {
                    // Assuming Comptes is a class with appropriate constructor or setters
                    Gestionnaire gestionnaire = new Gestionnaire();
                    gestionnaire.setIdUtilisateur(rs.getInt("id_utilisateur"));
                    gestionnaire.setNom(rs.getString("nom")); // Replace with actual column name
                    gestionnaire.setPrenom(rs.getString("prenom")); // Replace with actual column name
                    gestionnaire.setEmail(rs.getString("email"));
                    gestionnaire.setMotDePasse(rs.getString("mot_de_passe"));
                    gestionnaire.setRole(rs.getString("role"));
                    
                    gestionnaires.add(gestionnaire);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return gestionnaires;
    }

}
