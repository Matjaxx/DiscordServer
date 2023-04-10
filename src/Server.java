import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Server {


    public void ServerConnection() throws ClassNotFoundException {
        Connection conn = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://messagerie.database.windows.net:1433;database=messagerie;user=laure@messagerie;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            String utilisateur = "laure";
            String motDePasse = "EuaacEuaac2";
            conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("connexion réussite de la base de donné");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion a la base de données :" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du changement du driver JDBC:" + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion :" + e.getMessage());
            }

        }
    }
}
