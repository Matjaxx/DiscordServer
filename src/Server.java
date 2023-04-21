import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
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



            String sql = "SELECT USERNAME FROM MESSAGES WHERE USERNAME2 = 'JEREMIE'";
            rs = st.executeQuery(sql);
            List<String> friendsConv = new ArrayList<>();
            while (rs.next()){
                String USERNAME = rs.getString("USERNAME");
                if (!friendsConv.contains(USERNAME)){
                    friendsConv.add(USERNAME);
                }
            }
            sql = "SELECT USERNAME2 FROM MESSAGES WHERE USERNAME = 'JEREMIE'";
            rs = st.executeQuery(sql);
            while (rs.next()){
                String USERNAME2 = rs.getString("USERNAME2");
                if (!friendsConv.contains(USERNAME2)){
                    friendsConv.add(USERNAME2);
                }
            }
            System.out.println(friendsConv);

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
