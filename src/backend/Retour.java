package backend;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Retour {

    private int id_retour;
    private int montant;
    private LocalDate date_retour;
    private String etat_retour = "En maintenance";
    private int frais_supplementaires = 0;
    private Reservation reservation;

    Retour(int id_retour, int montant, LocalDate date_retour, String etat_retour, int frais_supplementaires) {
        this.id_retour = id_retour;
        this.montant = montant;
        this.date_retour = date_retour;
        this.etat_retour = etat_retour;
        this.frais_supplementaires = frais_supplementaires;
    }
    
    public Retour(Connection conn, int id_reservation, LocalDate date_retour, String etat_retour, String mode_paiement) throws SQLException {
    	String selectMaxIdSQL = "SELECT MAX(id_retour) FROM Retour";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectMaxIdSQL)) {
            if (rs.next()) {
                this.setIdRetour(rs.getInt(1) + 1);; // Increment the max id by 1
            } else {
                this.setIdRetour(1); // Default to 1 if no records exist
            }
        }
        this.reservation = Reservation.getReservation(conn, id_reservation);
		this.setDateRetour(date_retour);
		//println's used for debugging, might need them again later
		//System.out.println("DateDebut (type): " + reservation.getDateDebut().getClass().getName());
		//System.out.println("DateDebut (value): " + reservation.getDateDebut());
		//System.out.println("DateRetour (type): " + this.getDateRetour().getClass().getName());
		//System.out.println("DateRetour (value): " + this.getDateRetour());
		LocalDate dateD = reservation.getDateDebut();
		LocalDate dateF = this.getDateRetour();
		this.setMontant(((int) ChronoUnit.DAYS.between(dateD, dateF)) * reservation.vehicule.getPrixLocationJour());
		//System.out.println("Start Date: " + dateD);
		//System.out.println("Return Date: " + dateF);
		//System.out.println("Daily Price: " + reservation.vehicule.getPrixLocationJour());

        this.setEtatRetour(etat_retour);
        if(this.getEtatRetour().equals("maintenance")) {
        	//if damaged - pays extra 35% of total price
        	this.setFraisSupplementaires((montant * 35)/100);
        	this.setMontant(this.getMontant() + this.getFraisSupplementaires());
        }
       Paiement paiement = new Paiement(conn, reservation.getIdReservation(), this.getMontant(), this.getDateRetour(), mode_paiement);
       Vehicule.modifierVehicule(conn, this.reservation.vehicule.getIdVehicle(), this.reservation.vehicule.getMarque(), this.reservation.vehicule.getModele(), this.reservation.vehicule.getAnnee(), this.reservation.vehicule.getType(), this.reservation.vehicule.getCarburant(), this.reservation.vehicule.getPrixLocationJour(), etat_retour);
       Reservation.modifierReservation(conn, reservation.getIdReservation(), reservation.client, reservation.vehicule, reservation.getDateDebut(), date_retour, this.getMontant(), "terminee");
       String insertSQL = "INSERT INTO Retour(id_retour, id_reservation, date_retour, etat_retour, frais_supplementaires) VALUES(?,?,?,?,?)";
       PreparedStatement stmt = conn.prepareStatement(insertSQL);
       stmt.setInt(1, this.getIdRetour());
       stmt.setInt(2, this.reservation.getIdReservation());
       stmt.setString(3, this.getDateRetour().toString());
       stmt.setString(4, this.getEtatRetour());
       stmt.setInt(5, this.getFraisSupplementaires());
       stmt.executeUpdate();
    
    }

    public int getIdRetour() {
        return id_retour;
    }

    public void setIdRetour(int newId_retour) {
        id_retour = newId_retour;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int newMontant) {
        montant = newMontant;
    }

    public LocalDate getDateRetour() {
        return date_retour;
    }

    public void setDateRetour(LocalDate newDate_retour) {
        date_retour = newDate_retour;
    }

    public int getFraisSupplementaires() {
        return frais_supplementaires;
    }

    public void setFraisSupplementaires(int newFrais_supplementaires) {
        frais_supplementaires = frais_supplementaires + newFrais_supplementaires;
    }

    /*
    public void setFraisSupplementairesNull() {
        frais_supplementaires = 0;
    }
    */

    public void setEtatRetour(String etat_retour) {
        this.etat_retour = etat_retour;

    }

    public String getEtatRetour() {
        return etat_retour;
    }
/*
    public void processusRetour() {

        System.out.println("Ajoute le nouveau frais:");
        int x = Integer.parseInt(scanner.nextLine());

        setFraisSupplementaires(x);

        System.out.println("Est qu'il y a encore des dommages et des frais supplémentaires, Réponds par Oui/Non :");
        String reponse = scanner.nextLine();

        if (reponse.equals("Oui")) {
            processusRetour();
        }

    }

    public void saveToDatabase(Connection conn, java.sql.Date date_retour, int id_retour, int id_reservation,
            String etat_retour, int frais_supplementaires) throws SQLException {// good

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date_retour); // Convert Date to string in 'YYYY-MM-DD' format

        String creatSQL = "INSERT INTO Retour(id_retour, id_reservation, date_retour, etat_retour, frais_supplementaires) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(creatSQL);
        stmt.setInt(1, id_retour);
        stmt.setInt(2, id_reservation);
        stmt.setString(3, formattedDate); // Remplacez par une Date si nécessaire
        stmt.setString(4, etat_retour);
        stmt.setInt(5, frais_supplementaires);
        stmt.executeUpdate();
        System.out.println("Données insérées avec succès !");
    }
    */
}