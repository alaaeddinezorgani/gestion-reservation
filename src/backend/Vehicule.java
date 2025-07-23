package backend;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Vehicule {


    public int id_vehicle;
    private String marque;
    private String modele;
    private int annee;
    private String type;
    private String carburant;
    private int prix_location_jour;
    private String etat;

    public Vehicule() {
    }
    
    
    public Vehicule(Connection conn,String marque,String modele,int annee,String type,String carburant,int prix_location_jour)throws SQLException {
    	String selectMaxIdSQL = "SELECT MAX(id_vehicule) FROM Vehicule";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectMaxIdSQL)) {
            if (rs.next()) {
                this.setIdVehicle(rs.getInt(1) + 1);; // Increment the max id by 1
            } else {
                this.setIdVehicle(1); // Default to 1 if no records exist
            }
        }
        this.setMarque(marque);
        this.setModele(modele);
        this.setAnnee(annee);
        this.setCarburant(carburant);
        this.setEtat("disponible");
        this.setPrixLocationJour(prix_location_jour);
        this.setType(type);
        String insertSQL = "INSERT INTO Vehicule(id_vehicule,marque,modele,annee,type,carburant,prix_location_jour,etat) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(insertSQL);
        stmt.setInt(1,this.getIdVehicle());
        stmt.setString(2,this.getMarque());
        stmt.setString(3,this.getModele());
        stmt.setInt(4,this.getAnnee());
        stmt.setString(5,this.getType());
        stmt.setString(6,this.getCarburant());
        stmt.setInt(7,this.getPrixLocationJour());
        stmt.setString(8,this.getEtat());  
        stmt.executeUpdate();   
        }
    
    public Vehicule(int id_vehicle,String marque,String modele,int annee,
                    String type,String carburant,int prix_location_jour,String etat) {
        this.id_vehicle = id_vehicle;
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.type = type;
        this.carburant = carburant;
        this.prix_location_jour = prix_location_jour;
        this.etat = etat;
    }

  
    public int getIdVehicle() {
        return this.id_vehicle;
    }

    public void setIdVehicle(int newId_vehicle) {
        this.id_vehicle = newId_vehicle;
    }

    public String getMarque() {
        return this.marque;
    }

    public void setMarque(String newMarque) {
        this.marque = newMarque;
    }

    public String getModele() {
        return this.modele;
    }
    
    public void setModele(String newModele) {
        this.modele = newModele;
    }

    public int getAnnee() {
        return this.annee;
    }

    public void setAnnee(int newAnnee) {
        this.annee = newAnnee;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public String getCarburant() {
        return this.carburant;
    }

    public void setCarburant(String newCarburant) {
        this.carburant = newCarburant;
    }


    public int getPrixLocationJour() {
        return this.prix_location_jour;
    }

    public void setPrixLocationJour(int newPrix_location_jour) {
        this.prix_location_jour = newPrix_location_jour;
    }
    public String getEtat(){
        return this.etat;
    }
    public void setEtat(String newEtat){
        this.etat = newEtat;
    }

    public static void supprimerVehicle(Connection conn, int id_vehicule) throws SQLException {
        String deleteSQL = "DELETE FROM Vehicule WHERE id_vehicule =?";
        PreparedStatement stmt = conn.prepareStatement(deleteSQL);
        stmt.setInt(1, id_vehicule);
        stmt.executeUpdate();
    }


    public static ArrayList<Vehicule> rechercherVehicle(Connection conn, String filtr,String x) throws SQLException {
        List<Vehicule> vehicules = new ArrayList<>();
        String getSQL = "SELECT * FROM Vehicule WHERE " + filtr + "=?";
        PreparedStatement stmt = conn.prepareStatement(getSQL);
        stmt.setString(1, x);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            /*
             * System.out.println("id_vehicule: " + rs.getString("id_vehicule") +
             * ", marque: " + rs.getString("marque") +
             * ", modele: " + rs.getString("modele") +
             * ", anne: " + rs.getInt("annee") +
             * ", type: " + rs.getString("type") +
             * ", carburant: " + rs.getString("carburant") +
             * ", prix_location_jour: " + rs.getString("prix_location_jour") +
             * ", etat: " + rs.getString("etat")+"\n");
             */
            Vehicule v = new Vehicule();
            v.setIdVehicle(rs.getInt("id_vehicule"));
            v.setMarque(rs.getString("marque"));
            v.setModele(rs.getString("modele"));            
            v.setAnnee(rs.getInt("annee"));
            v.setType(rs.getString("type"));
            v.setCarburant(rs.getString("carburant"));
            v.setPrixLocationJour(rs.getInt("prix_location_jour"));
            v.setEtat(rs.getString("etat"));     
            vehicules.add(v);
        }
        Vehicule[] v = vehicules.toArray(new Vehicule[0]);
        ArrayList<Vehicule> res = new ArrayList<>();
        for (Vehicule veh : v)
            res.add(veh);
        return res;

    }

  
    public static ArrayList<Vehicule> afficherVehicule(Connection conn) throws SQLException {
    	ArrayList<Vehicule> vehicules = new ArrayList<>();
        String selectSQL = "SELECT * FROM Vehicule";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);
        while (rs.next()) {
            int id_vehicule = rs.getInt("id_vehicule");
            String marque = rs.getString("marque");
            String modele = rs.getString("modele");
            int annee = rs.getInt("annee");
           String type = rs.getString("type");
            String carburant = rs.getString("carburant");
            int prix_location_jour = rs.getInt("prix_location_jour");
            String etat = rs.getString("etat");
            Vehicule vehicule = new Vehicule(id_vehicule, marque, modele, annee, type, carburant, prix_location_jour, etat);
            vehicules.add(vehicule);
        }
    return vehicules;
    }
    
    
    /*
    public void ajouterVehicle(Connection conn,int id_vehicle,String marque,String modele,int annee,String type,String carburant,int prix_location_jour,String etat)throws SQLException {
    	String selectMaxIdSQL = "SELECT MAX(id_vehicule) FROM Vehicule";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectMaxIdSQL)) {
            if (rs.next()) {
                this.setIdVehicle(rs.getInt(1) + 1);; // Increment the max id by 1
            } else {
                this.setIdVehicle(1); // Default to 1 if no records exist
            }
        }
        this.setMarque(marque);
        this.setModele(modele);
        this.setAnnee(annee);
        this.setCarburant(carburant);
        this.setEtat(etat);
        this.setPrixLocationJour(prix_location_jour);
        this.setType(type);
        String insertSQL = "INSERT INTO Vehicule(id_vehicule,marque,modele,annee,type,carburant,prix_location_jour,etat) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(insertSQL);
        stmt.setInt(1,this.getIdVehicle());
        stmt.setString(2,this.getMarque());
        stmt.setString(3,this.getModele());
        stmt.setInt(4,this.getAnnee());
        stmt.setString(5,this.getType());
        stmt.setString(6,this.getCarburant());
        stmt.setInt(7,this.getPrixLocationJour());
        stmt.setString(8,this.getEtat());  
        stmt.executeUpdate();      
    }
*/
/*
    public void modifierVehicle(Connection conn,String attr,int id_vehicle,String newAttr)throws SQLException {
        String updateSQL = "UPDATE Vehicule SET "+attr+" = ? WHERE id_vehicule = ?";
        PreparedStatement stmt = conn.prepareStatement(updateSQL);
        if(newAttr.matches("[a-zA-Z\\s]+")){
            stmt.setString(1,newAttr);
        }
        else{
            int number = Integer.parseInt(newAttr);
            stmt.setInt(1, number);
        }
        stmt.setInt(2,id_vehicle);
        stmt.executeUpdate();
    }
*/
    
    public static void modifierVehicule(Connection conn, int id_vehicule, String marque,String modele,int annee,String type,String carburant,int prix_location_jour,String etat)throws SQLException {
        String updateSQL = "UPDATE Vehicule SET marque = ?, modele = ?, annee = ?, type = ?, carburant = ?, prix_location_jour = ?, etat = ? WHERE id_vehicule = ?";
        PreparedStatement stmt = conn.prepareStatement(updateSQL);
        stmt.setString(1, marque);
        stmt.setString(2, modele);
        stmt.setInt(3, annee);
        stmt.setString(4, type);
        stmt.setString(5, carburant);
        stmt.setInt(6, prix_location_jour);
        stmt.setString(7, etat);
        stmt.setInt(8, id_vehicule);
        stmt.executeUpdate();
    }
    
    public static Vehicule getVehicule(Connection conn, int id_vehicule) throws SQLException {
    	String selectSQL = "SELECT * FROM Vehicule WHERE id_vehicule =?";
        PreparedStatement stmt = conn.prepareStatement(selectSQL);
        stmt.setInt(1, id_vehicule);
        ResultSet rs = stmt.executeQuery();
           String marque = rs.getString("marque");
           String modele = rs.getString("modele");
           int annee = rs.getInt("annee");
           String type = rs.getString("type");
           String carburant = rs.getString("carburant");
           int prix_location_jour = rs.getInt("prix_location_jour");
           String etat = rs.getString("etat");
           Vehicule vehicule = new Vehicule(id_vehicule, marque, modele, annee, type, carburant, prix_location_jour, etat);
           
    return vehicule;

    }
    
    public static void deleteVehicule(Connection conn, int id_vehicule )throws SQLException {
    	String request = "DELETE FROM Vehicule WHERE id_vehicule = ?";
    	PreparedStatement stmt = conn.prepareStatement(request);
    	stmt.setInt(1, id_vehicule);
    	stmt.execute();
    	
    	
    }
}
