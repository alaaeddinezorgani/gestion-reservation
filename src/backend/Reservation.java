package backend;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Reservation {
	public Client client;
	public Vehicule vehicule;
    //private static int count;
    public int id_reservation;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private int montant_total;
    private String statut;

    public Reservation(Connection conn, Client c, Vehicule v, LocalDate dateD, LocalDate dateF) throws SQLException {
    	String selectMaxIdSQL = "SELECT MAX(id_reservation) FROM Reservation";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectMaxIdSQL);
            if (rs.next()) {
                this.id_reservation = rs.getInt(1) + 1; // Increment the max id by 1
            } else {
                this.id_reservation = 1; // Default to 1 if no records exist
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        this.setDateDebut(dateD);
        this.setDateFin(dateF);
        LocalDate dateDeb = date_debut;
		LocalDate dateFin = date_fin;
		this.setMontantTotal(((int) ChronoUnit.DAYS.between(dateDeb, dateFin)) * v.getPrixLocationJour());
		this.setStatut("en cours");
		Vehicule.modifierVehicule(conn, v.getIdVehicle(), v.getMarque(), v.getModele(), v.getAnnee(), v.getType(), v.getCarburant(), v.getPrixLocationJour(), "louee");
    	String insertSQL = "INSERT INTO Reservation(id_reservation, id_vehicule, id_client, date_debut, date_fin, montant_total, statut) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(insertSQL);
        stmt.setInt(1, this.getIdReservation());
        stmt.setInt(3, c.id_client);
        stmt.setInt(2, v.id_vehicle);
        stmt.setString(4, dateDeb.toString());
        stmt.setString(5, dateFin.toString());
        stmt.setInt(6, this.getMontantTotal());
        stmt.setString(7, this.getStatut());
        stmt.executeUpdate();
    }
    
    //surchacharged consstructor to return informatin, used by method consulter
    public Reservation(Connection conn, int id_reservation, int id_client, int id_vehicule, LocalDate dateD, LocalDate dateF, int montant_total, String stat) throws SQLException {
    	this.id_reservation = id_reservation;
    	this.client = Client.getClient(conn, id_client);
    	this.vehicule = Vehicule.getVehicule(conn, id_vehicule);
    	this.date_debut = dateD;
    	this.date_fin = dateF;
    	this.montant_total = montant_total;
    	this.statut = stat;
    }
    public int getIdReservation() {
        return this.id_reservation;
    }


    public void setIdReservation(int newId_reservation) {
        this.id_reservation = newId_reservation;
    }

  
    public LocalDate getDateDebut() {
        return this.date_debut;
    }

  
    public void setDateDebut(LocalDate newDate_debut) {
       this.date_debut = newDate_debut;
    }

    public LocalDate getDateFin() {
        return this.date_fin;
    }

    public void setDateFin(LocalDate newDate_fin) {
        this.date_fin = newDate_fin;
    }

    public int getMontantTotal() {
        return this.montant_total;
    }

    public void setMontantTotal(int newMontant_total) {
        this.montant_total = newMontant_total;
    }
    public String getStatut() {
    	return this.statut;
    }
    
    public void setStatut(String statut) {
    	this.statut = statut;
    }
 
    /*using constructor is probably better*/
    /*
    public void creerReservation(Connection conn, Client c, Vehicule v, java.sql.Date dateD, java.sql.Date dateF, int montant_total, String stat) throws SQLException {
    	new Reservation(conn, c, v, dateD, dateF, montant_total, stat);
    
    }
*/
  
    public Reservation consulterReservation(Connection conn, int id_reservation) throws SQLException {
        String selectSQL = "SELECT * FROM Reservation WHERE id_reservation = ?";
        PreparedStatement stmt = conn.prepareStatement(selectSQL);
        stmt.setInt(1, id_reservation);
        ResultSet rs = stmt.executeQuery();
        int id_client = rs.getInt("id_client");
        int id_vehicule = rs.getInt("id_vehicule");
        LocalDate dateD = LocalDate.parse(rs.getString("date_debut"));
        LocalDate dateF = LocalDate.parse(rs.getString("date_fin"));
        int montant_total = rs.getInt("montant_total");
        String stat = rs.getString("statut");
        return new Reservation(conn, id_reservation, id_client, id_vehicule, dateD, dateF, montant_total, stat);
    }

    public static void modifierReservation(Connection conn, int id_reservation, Client newC, Vehicule newV, LocalDate newDateD, LocalDate newDateF, int new_montant_total, String newStat) throws SQLException {
        String updateSQL = "UPDATE Reservation SET id_client = ?, id_vehicule = ?, date_debut = ?, date_fin = ?, montant_total = ?, statut = ? WHERE id_reservation = ?";
        PreparedStatement stmt = conn.prepareStatement(updateSQL);
        stmt.setInt(1, newC.getIdClient());
        stmt.setInt(2, newV.getIdVehicle());
        stmt.setString(3, newDateD.toString());
        stmt.setString(4, newDateF.toString());
        stmt.setInt(5, new_montant_total);
        stmt.setString(6, newStat);
        stmt.setInt(7, id_reservation);
        stmt.executeUpdate();
    }

    public static void annulerReservation(Connection conn, Reservation res) throws SQLException {
        String deleteSQL = "DELETE FROM Reservation WHERE id_reservation = ?";
        PreparedStatement stmt = conn.prepareStatement(deleteSQL);
        stmt.setInt(1, res.getIdReservation());
        stmt.executeUpdate();
    }


    public static ArrayList<Reservation> getHistorique(Connection conn) throws SQLException {
    	ArrayList<Reservation> historique = new ArrayList<>();
        String selectSQL = "SELECT * FROM Reservation WHERE ORDER BY date_debut";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(selectSQL);
        while (rs.next()) {
            int id_reservation = rs.getInt("id_reservation");
            int id_client = rs.getInt("id_client");
            int id_vehicule = rs.getInt("id_vehicule");
            LocalDate dateDebut = LocalDate.parse(rs.getString("date_debut"));
            LocalDate dateFin = LocalDate.parse(rs.getString("date_fin"));
            int montantTotal = rs.getInt("montant_total");
            String statut = rs.getString("statut");
            Client client = new Client();
            client.setIdClient(id_client);
            Vehicule vehicule = new Vehicule();
            vehicule.setIdVehicle(id_vehicule);
            Reservation reservation = new Reservation(conn, id_reservation, id_client, id_vehicule, dateDebut, dateFin, montantTotal, statut);
            historique.add(reservation);
        }
    return historique;
    }
    
    public static Reservation getReservation(Connection conn, int id_reservation) throws SQLException {
    	String selectSQL = "SELECT * FROM Reservation WHERE id_reservation =?";
        PreparedStatement stmt = conn.prepareStatement(selectSQL);
        stmt.setInt(1, id_reservation);
        ResultSet rs = stmt.executeQuery();
        Reservation reservation;
        if (rs.next()) {
            int id_client = rs.getInt("id_client");
            int id_vehicule = rs.getInt("id_vehicule");
            LocalDate dateDebut = LocalDate.parse(rs.getString("date_debut"));
            LocalDate dateFin = LocalDate.parse(rs.getString("date_fin"));
            int montantTotal = rs.getInt("montant_total");
            String statut = rs.getString("statut");
            reservation = new Reservation(conn, id_reservation, id_client, id_vehicule, dateDebut, dateFin, montantTotal, statut);
        } else
            throw new SQLException("Reservation non-existant");
    return reservation;

}
    public String toString() {
    	return "id_reservation: " + this.id_reservation + "\nid_client: "+ this.client.getIdClient() + "\nid_vehicule: " + this.vehicule.getIdVehicle();
    }

}
