package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;
    private static final String DB_URL = "jdbc:sqlite:/home/alaaeddine/ws/eclipse/Eag/src/backend/gestion-reservation.db";
    private static final Object LOCK = new Object();
    private static boolean initialized = false;

    public static void init() throws SQLException {
            conn = DriverManager.getConnection(DB_URL);
            initialized = true;
    }

    public static Connection getConnection() throws SQLException{
        if (!initialized)
            init();
        return conn;
    }

    public static void shutdown() throws SQLException {
        if (initialized)
            conn.close();
    }
}
