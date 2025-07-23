package backend;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Client extends Personne {

    private static int count;
	public int id_client;
	private String nom, prenom, email;
    private int telephone;
    private int numero_permis;
    public Client() {
    }
    public Client(Connection conn, String nom, String prenom, int telephone, String email, int numero_permis) throws SQLException {
    	 String selectMaxIdSQL = "SELECT MAX(id_client) FROM Client";
         try {
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectMaxIdSQL);
             if (rs.next()) {
                 this.id_client = rs.getInt(1) + 1; // Increment the max id by 1
             } else {
                 this.id_client = 1; // Default to 1 if no clients exist
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.numero_permis = numero_permis;

        String insertSQL = "INSERT INTO Client (id_client, nom, prenom, telephone, email, numero_permis) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setInt(1, this.id_client);
            stmt.setString(2, this.nom);
            stmt.setString(3, this.prenom);
            stmt.setInt(4, this.telephone);
            stmt.setString(5, this.email);
            stmt.setInt(6, this.numero_permis);
            stmt.executeUpdate();
        }
    }
    public Client(int id_client, String nom, String prenom, int telephone, String email, int numero_permis) {
		this.id_client = id_client;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.numero_permis = numero_permis;
	}
    public int getIdClient() {
        return this.id_client;
    }
    public void setIdClient(int newId_client) {
        this.id_client = newId_client;
    }
    public int getTelephone() {
        return this.telephone;
    }

    public void setTelephone(int newTelephone) {
        this.telephone = newTelephone;
    }

    public int getNumeroPermis() {
       return this.numero_permis;
    }

    public void setNumeroPermis(int newNumero_permis) {
        this.numero_permis = newNumero_permis;
    }
    
    public String getNom() {
    	return this.nom;
    }
    public void setNom(String nom) {
    	this.nom = nom;
    }
    
    public String getPrenom() {
    	return this.prenom;
    }
    public void setPrenom(String prenom) {
    	this.prenom = prenom;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getEmail() {
    	return this.email;
    }
    
    public Client rechercherClient(Connection conn, int id_client) throws SQLException {
        String selectSQL = "SELECT * FROM Client WHERE id_client = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, id_client);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    int telephone = rs.getInt("telephone");
                    String email = rs.getString("email");
                    int numero_permis = rs.getInt("numero_permis");
                    return new Client(id_client, nom, prenom, telephone, email, numero_permis);
                } else {
                    throw new SQLException("Client not found for ID: " + id_client);
                }
            }
        }
    }

//    public void enregistrerClient(Connection conn) throws SQLException {
//        new Client(conn, this.id_client, this.nom, this.prenom, this.telephone, this.email, this.numero_permis);
//    }

    public static void modifierClient(Connection conn, int id_client, String newNom, String newPrenom, int newTelephone, String newEmail, int newNumeroPermis) throws SQLException {
        String updateSQL = "UPDATE Client SET nom = ?, prenom = ?, telephone = ?, email = ?, numero_permis = ? WHERE id_client = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1, newNom);
            stmt.setString(2, newPrenom);
            stmt.setInt(3, newTelephone);
            stmt.setString(4, newEmail);
            stmt.setInt(5, newNumeroPermis);
            stmt.setInt(6, id_client);
            stmt.executeUpdate();
        }
    }

    public String consulterEtat(Connection conn) throws SQLException {
        String selectSQL = "SELECT status FROM Client WHERE id_client = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, this.id_client);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                } else {
                    throw new SQLException("Client status not found for ID: " + id_client);
                }
            }
        }
    }

    public void miseAJourEtat(Connection conn, String nouveauEtat) throws SQLException {
        String updateSQL = "UPDATE Client SET status = ? WHERE id_client = ?";
        try (PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1, nouveauEtat);
            stmt.setInt(2, this.id_client);
            stmt.executeUpdate();
        }
    }
    
    public static ArrayList<Client> getAllClients(Connection conn) throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        String selectSQL = "SELECT * FROM Client";
        
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                int id_client = rs.getInt("id_client");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int telephone = rs.getInt("telephone");
                String email = rs.getString("email");
                int numero_permis = rs.getInt("numero_permis");
                
                // Create a new Client object for each row in the result set
                Client client = new Client(id_client, nom, prenom, telephone, email, numero_permis);
                clients.add(client);
            }
        }

        return clients;
    }
    
    public static Client getClient(Connection conn, int id_client) throws SQLException {
    	String selectSQL = "SELECT * FROM Client WHERE id_client =?";
        PreparedStatement stmt = conn.prepareStatement(selectSQL);
        stmt.setInt(1, id_client);
        ResultSet rs = stmt.executeQuery();
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        int telephone = rs.getInt("telephone");
        String email = rs.getString("email");
        int numero_permis = rs.getInt("numero_permis");
        Client client = new Client(id_client, nom, prenom, telephone, email, numero_permis);
        return client;
        
    }
    
    public static ArrayList<Client> rechercheClients(Connection conn, String name) throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        String selectSQL = "SELECT * FROM Client WHERE nom LIKE ?";
        
        try(PreparedStatement pst = conn.prepareStatement(selectSQL)){
        pst.setString(1, "%" + name + "%");
        
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int id_client = rs.getInt("id_client");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int telephone = rs.getInt("telephone");
                String email = rs.getString("email");
                int numero_permis = rs.getInt("numero_permis");
                
                // Create a new Client object for each row in the result set
                Client client = new Client(id_client, nom, prenom, telephone, email, numero_permis);
                clients.add(client);
            }
        }
        }catch(SQLException ex) {
        	ex.printStackTrace();
        }
        return clients;
    }
    
    public static void deleteClient(Connection conn, int id_client )throws SQLException {
    	String request = "DELETE FROM Client WHERE id_client = ?";
    	PreparedStatement stmt = conn.prepareStatement(request);
    	stmt.setInt(1, id_client);
    	stmt.execute();
    	
    	
    }

}
