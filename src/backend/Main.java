package backend;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
public class Main {

	public static void main(String[] args) {
		try {
			ConnectionManager.init();
			Connection conn = ConnectionManager.getConnection();
			System.out.println("Database connection established");
			
			//create vehcule
			Vehicule v = new Vehicule(conn, "ultr", "md", 2024, "typemk", "carburantnk", 1000);
			//create clientt
			Client c = new Client(conn, "ji", "precli", 5034, "ltoie@hef5y3a.there", 5738);
			//create reservation - makes vehicule status "louee"
			Reservation res = new Reservation(conn, c, v, LocalDate.of(2021, 12, 10), LocalDate.of(2022, 12, 12));
			//make return by creating return object
			//makes automatic paiement and logs it to database
			//makes vehicule status maintenance or disponible depending on etat retour
			//changes reservation status from 'en cours' to 'terminee' 
			Retour retour = new Retour(conn, 36, LocalDate.of(2022, 12, 12),"maintenance", "monnaie");
			//Admin adm = new Admin(conn, "tes", "k", "mail@mail.com", "heyhihello");
			
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
		
	}

}
