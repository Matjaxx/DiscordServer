import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
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



            String sql3 = "UPDATE Customer SET PERMISSION = 'BAN' WHERE USERNAME = 'MATHIS'";
            st.executeUpdate(sql3);
            String sql = "SELECT * FROM LOGS";
            rs = st.executeQuery(sql);

            int connectionCustomer = 0;
            int AddMessage = 0;
            int banUserRequest = 0;
            int freeUserRequest = 0;
            int seeMyFriendsOnlineDAO = 0;
            int FriendUpdate = 0;

            while (rs.next()){
                String TYPELOG = rs.getString("TYPELOG");
                switch (TYPELOG){
                    case "connectionCustomer":
                        connectionCustomer++;
                        break;
                    case "AddMessage":
                        AddMessage++;
                        break;
                    case "FriendUpdate":
                        FriendUpdate++;
                        break;
                    case "seeMyFriendsOnlineDAO":
                        seeMyFriendsOnlineDAO++;
                        break;
                    case "banUserRequest":
                        banUserRequest++;
                        break;
                    case "freeUserRequest":
                        freeUserRequest++;
                        break;
                }
            }
            System.out.println(connectionCustomer);
            System.out.println(AddMessage);
            System.out.println(FriendUpdate);
            System.out.println(seeMyFriendsOnlineDAO);
            System.out.println(banUserRequest);
            System.out.println(freeUserRequest);

            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Connection Customer", connectionCustomer);
            dataset.setValue("Add Message", AddMessage);
            dataset.setValue("Ban User Request", banUserRequest);
            dataset.setValue("Free User Request", freeUserRequest);
            dataset.setValue("See My Friends Online DAO", seeMyFriendsOnlineDAO);
            dataset.setValue("Friend Update", FriendUpdate);

            // Create the chart
            JFreeChart chart = ChartFactory.createPieChart("Requests", dataset, true, true, false);

            // Customize the chart
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionOutlinesVisible(false);
            plot.setCircular(false);

            // Display the chart
            ChartFrame frame = new ChartFrame("Requests", chart);
            frame.setVisible(true);
            frame.setSize(500, 500);

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
