import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Server {

    public void ServerConnection() throws ClassNotFoundException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://messagerie.database.windows.net:1433;database=messagerie;user=laure@messagerie;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            String utilisateur = "laure";
            String motDePasse = "EuaacEuaac2";
            conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            st = conn.createStatement();
            System.out.println("Connexion réussie à la base de données.");

            String sql10 = "SELECT * FROM Customer";
            rs = st.executeQuery(sql10);

            while (rs.next()){
                String USERNAME = rs.getString("USERNAME");
                String Last_Connection = rs.getString("Last_Connection");

                System.out.println(USERNAME + " to " + Last_Connection);
            }


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(new Date());
            System.out.println("Date formatée : " + formattedDate);

            // Insert a message into the MESSAGES table
            String sql3 = "INSERT INTO MESSAGES (USERNAME, USERNAME2, CONTENT,TIMES) " +
                    "VALUES ('JEREMIE', 'MATHIS', 'test?','" + formattedDate + "')";
            st.executeUpdate(sql3);
            System.out.println("Message inséré dans la table MESSAGES.");

            // Retrieve all messages from the MESSAGES table and print them
            String sql = "SELECT USERNAME, USERNAME2, CONTENT,TIMES FROM MESSAGES";
            rs = st.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                System.out.println(columnName);
            }
            while (rs.next()) {
                String sender = rs.getString("USERNAME");
                String recipient = rs.getString("USERNAME2");
                String content = rs.getString("CONTENT");
                String date = rs.getString("TIMES");
                System.out.println(sender + " to " + recipient + ": " + content + " (" + date + ")");
            }

            String sql8 = "DELETE FROM FRIENDLIST";
            st.executeUpdate(sql8);
            System.out.println("Tous les messages ont été supprimés de la table MESSAGES.");

            // Retrieve all messages from the MESSAGES table and print them
            String sql6 = "SELECT FRIEND1, FRIEND2 FROM FRIENDLIST";
            rs = st.executeQuery(sql6);

            
            while (rs.next()) {
                String sender = rs.getString("FRIEND1");
                String recipient = rs.getString("FRIEND2");
                System.out.println(sender + " with " + recipient + " are friends.");
            }

            // Retrieve all friend requests from the FRIENDREQUESTS table and print them
            /*String sql1 = "SELECT * FROM FRIENDREQUESTS";
            rs = st.executeQuery(sql1);
            while (rs.next()) {
                String friend1 = rs.getString("FRIEND1");
                String friend2 = rs.getString("FRIEND2");
                System.out.println(friend1 + " and " + friend2 + " are friends.");
            }*/

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
