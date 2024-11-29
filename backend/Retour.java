
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Retour {

    Scanner scanner = new Scanner(System.in);

    private int id_retour;

    private int montant;

    private java.sql.Date date_retour;

    private String etat_retour = "En maintenance";

    private int frais_supplementaires = 0;

    Retour(int id_retour, int montant, java.sql.Date date_retour, String etat_retour, int frais_supplementaires) {
        this.id_retour = id_retour;
        this.montant = montant;
        this.date_retour = date_retour;
        this.etat_retour = etat_retour;
        this.frais_supplementaires = frais_supplementaires;
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

    public Date getDateRetour() {

        return date_retour;
    }

    public void setDateRetour(java.sql.Date newDate_retour) {
        date_retour = newDate_retour;
    }

    public int getFraisSupplementaires() {

        return frais_supplementaires;
    }

    public void setFraisSupplementaires(int newFrais_supplementaires) {
        frais_supplementaires = frais_supplementaires + newFrais_supplementaires;
    }

    public void setFraisSupplementairesNull() {
        frais_supplementaires = 0;
    }

    public void setEtatDisponible() {
        etat_retour = "Disponible";

    }

    public String getEtat() {

        return etat_retour;
    }

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
}

