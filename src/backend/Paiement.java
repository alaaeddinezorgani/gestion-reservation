package backend;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class Paiement {

    private int id_paiement;
    private int montant;
    private java.sql.Date date_paiment;
    private Reservation reservation;
    private String mode_de_paiment;
    private int frais_supplimantaires;

    Paiement(int id_paiement, int montant, java.sql.Date date_paiment) {
        this.id_paiement = id_paiement;
        this.montant = montant;
        this.date_paiment = date_paiment;
   
    }
    
    Paiement(Connection conn, int id_reservation, int montant, java.sql.Date date_paiment, String mode_paiement) throws SQLException{
    	String selectMaxIdSQL = "SELECT MAX(id_paiement) FROM Paiement";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectMaxIdSQL)) {
            if (rs.next()) {
                this.setIdPaiement(rs.getInt(1) + 1);; // Increment the max id by 1
            } else {
                this.setIdPaiement(1); // Default to 1 if no records exist
            }
        }
        this.setMontant(montant);
        this.setDatePaiement(date_paiment);
        this.setModePaiment(mode_paiement);
        this.setReservation(Reservation.getReservation(conn, id_reservation));
        String insertSQL = "INSERT INTO Paiement(id_paiement, id_reservation, montant, date_paiement, mode_paiement) VALUES(?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(insertSQL);
        stmt.setInt(1, this.getIdPaiement());
        stmt.setInt(2, this.reservation.getIdReservation());
        stmt.setInt(3, this.getMontant());
        stmt.setDate(4, this.getDatePaiement());
        stmt.setString(5, this.getModePaiement());
        stmt.executeUpdate();
        
        
    }

    public int getIdPaiement() {
        return id_paiement;
    }

    public void setIdPaiement(int newId_paiement) {
        id_paiement = newId_paiement;
    }

    public int getMontant() {

        return montant;
    }

    public void setMontant(int newMontant) {
        montant = newMontant;
    }

    public java.sql.Date getDatePaiement() {
        return date_paiment;
    }

    public void setDatePaiement(java.sql.Date newDate_paiment) {
        date_paiment = newDate_paiment;
    }

    public String getModePaiement() {
    	return this.mode_de_paiment;
    }
    
    public void setModePaiment(String newModePaiement) {
    	this.mode_de_paiment = newModePaiement;
    }
    
    public void setReservation(Reservation reservation) {
    	this.reservation = reservation;
    }
    
    public Reservation getReservation() {
    	return this.reservation;
    }
    
    /*
    public void effectuerPaiement(int argentDonné) {
        if (argentDonné > this.montant) {
            int x = argentDonné - this.montant;
            System.out.println("Paiement du montant effectué, voici l'argent a rendre:" + x);
        }
        if (argentDonné < this.montant) {
            int x = this.montant - argentDonné;
            System.out.println("il manque encore" + x);
            System.out.println("Donne l'argent ajouté par le client:");
            int y = Integer.parseInt(scanner.nextLine());
            argentDonné = argentDonné + y;
            effectuerPaiement(argentDonné);
        }
        if (argentDonné == this.montant) {
            System.out.println("Paiement du montant effectué");
        }
    }

    */
    /*
    public void effectuerPaiementMaintenance(int argentDonné, Retour retour) {
        if (argentDonné > retour.getFraisSupplementaires()) {
            int x = argentDonné - retour.getFraisSupplementaires();
            System.out.println("Paiement de la maintenance effectué, voici l'argent a rendre:" + x);
        }
        if (argentDonné < retour.getFraisSupplementaires()) {
            int x = retour.getFraisSupplementaires() - argentDonné;
            System.out.println("il manque encore" + x);
            System.out.println("Donne l'argent ajouté par le client:");
            int y = Integer.parseInt(scanner.nextLine());
            argentDonné = argentDonné + y;
            effectuerPaiement(argentDonné);
        }
        if (argentDonné == retour.getFraisSupplementaires()) {
            System.out.println("Paiement de la maintenance effectué");
        }
    }
    */

    /*
    public void saveToDatabase(Connection conn, int id_paiement, int montant, Date date_paiment) throws SQLException {// good

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date_paiment); // Convert Date to string in 'YYYY-MM-DD' format

        String creatSQL = "INSERT INTO Retour(id_paiement, montant, date_paiment) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(creatSQL);
        stmt.setInt(1, id_paiement);
        stmt.setInt(2, montant);
        stmt.setString(3, formattedDate);
        stmt.executeUpdate();
        System.out.println("Données insérées avec succès !");
    }
*/
}