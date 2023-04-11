import java.sql.*;


public class Server {


    public void ServerConnection() throws ClassNotFoundException {
        Connection conn = null;
        Statement st =null;
        ResultSet rs =null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://messagerie.database.windows.net:1433;database=messagerie;user=laure@messagerie;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            String utilisateur = "laure";
            String motDePasse = "EuaacEuaac2";
            conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("connexion réussite de la base de donné");
            String sql = "SELECT * FROM CUSTOMER";

            // Créer l'objet Statement
            st = conn.createStatement();

            // Exécuter la requête SQL
            rs = st.executeQuery(sql);

            // Parcourir les résultats et afficher les caractéristiques de chaque client
            while (rs.next()) {
                int id = rs.getInt("ID");
                String username = rs.getString("USERNAME");
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                String email = rs.getString("EMAIL");
                String password = rs.getString("PASSWORD");
                String permission = rs.getString("PERMISSION");
                Timestamp lastConnection = rs.getTimestamp("Last_Connection");

                System.out.println("ID : " + id);
                System.out.println("USERNAME : " + username);
                System.out.println("FIRST_NAME : " + firstName);
                System.out.println("LAST_NAME : " + lastName);
                System.out.println("EMAIL : " + email);
                System.out.println("PASSWORD : " + password);
                System.out.println("PERMISSION : " + permission);
                System.out.println("Last_Connection : " + lastConnection);
                System.out.println("------------------------------------------");
            }
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
