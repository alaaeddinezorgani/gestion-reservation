
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Paiement {

    Scanner scanner = new Scanner(System.in);

    private int id_paiement;

    private int montant;

    private Date date_paiment;

    Paiement(int id_paiement, int montant, Date date_paiment) {
        this.id_paiement = id_paiement;
        this.montant = montant;
        this.date_paiment = date_paiment;
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

    public Date getDatePaiement() {

        return date_paiment;
    }

    public void setDatePaiement(Date newDate_paiment) {
        date_paiment = newDate_paiment;
    }

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

}

