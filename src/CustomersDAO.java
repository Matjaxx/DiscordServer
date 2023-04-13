import java.sql.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class CustomersDAO {

    private boolean verifConnection = false;
    public boolean getVerifConnection() {
        return verifConnection;
    }
    public void ServerJBDCConnection(List<String> getListCustomer) throws ClassNotFoundException {
        Connection conn = null;
        Statement st =null;
        ResultSet rs =null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://messagerie.database.windows.net:1433;database=messagerie;user=laure@messagerie;password=EuaacEuaac2;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
            String utilisateur = "laure";
            String motDePasse = "EuaacEuaac2";
            conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion réussite à la base de données");


            if (Objects.equals(getListCustomer.get(0), "createAccount")) {
                addCustomer(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "connectionAccount")) {
                connectionCustomer(conn,st,rs,getListCustomer);
            }



        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données :" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du driver JDBC:" + e.getMessage());
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

    public void connectionCustomer(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();

        String sql3 = "SELECT * FROM CUSTOMER WHERE PASSWORD = '" + getListCustomer.get(2) + "' AND USERNAME = '" + getListCustomer.get(1) + "'";
        rs = st.executeQuery(sql3);

        while (rs.next()) {
            verifConnection = true;

        }
    }

        public void addCustomer(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        DatabaseMetaData dbmd = conn.getMetaData();
        rs = dbmd.getTables(null, null, "CUSTOMER", null);
        if (rs.next()) {
            System.out.println("La table CUSTOMER existe dans la base de données.");
            String sql3 = "INSERT INTO CUSTOMER (ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PERMISSION, Last_Connection) " +
                    "VALUES ('" + getListCustomer.get(1) + "', '" + getListCustomer.get(2) + "', '" + getListCustomer.get(3) + "', '" +
                    getListCustomer.get(4) + "', '" + getListCustomer.get(5) + "', '" + getListCustomer.get(6) + "', '" +
                    getListCustomer.get(7) + "', GETDATE())";
            st.executeUpdate(sql3);
            displayCustomerInfo(conn, st, rs);

        } else {
            System.out.println("La table CUSTOMER n'existe pas dans la base de données.");
            String sql = "CREATE TABLE CUSTOMER " +
                    "(ID INT NOT NULL PRIMARY KEY, " +
                    " USERNAME VARCHAR(50), " +
                    " FIRST_NAME VARCHAR(50), " +
                    " LAST_NAME VARCHAR(50), " +
                    " EMAIL VARCHAR(50), " +
                    " PASSWORD VARCHAR(50), " +
                    " PERMISSION VARCHAR(50), " +
                    " Last_Connection GETDATE())";
            st.executeUpdate(sql);
            System.out.println("Table CUSTOMER created successfully...");

            String sql3 = "INSERT INTO CUSTOMER (ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PERMISSION, Last_Connection) " +
                    "VALUES ('" + getListCustomer.get(1) + "', '" + getListCustomer.get(2) + "', '" + getListCustomer.get(3) + "', '" +
                    getListCustomer.get(4) + "', '" + getListCustomer.get(5) + "', '" + getListCustomer.get(6) + "', '" +
                    getListCustomer.get(7) + "', GETDATE())";
            st.executeUpdate(sql3);
            displayCustomerInfo(conn,st,rs);
        }
    }




    public void displayCustomerInfo(Connection conn,Statement st,ResultSet rs)throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM CUSTOMER"; // Remplacez 1 par l'ID de l'utilisateur que vous avez inséré
        rs = st.executeQuery(sql);
        if (rs.next()) {
            int id = rs.getInt("ID");
            String username = rs.getString("USERNAME");
            String firstName = rs.getString("FIRST_NAME");
            String lastName = rs.getString("LAST_NAME");
            String email = rs.getString("EMAIL");
            String password = rs.getString("PASSWORD");
            String permission = rs.getString("PERMISSION");
            Timestamp lastConnectionTime = rs.getTimestamp("Last_Connection");
            // Faites quelque chose avec les valeurs récupérées, par exemple les afficher
            System.out.println("ID : " + id);
            System.out.println("Username : " + username);
            System.out.println("First name : " + firstName);
            System.out.println("Last name : " + lastName);
            System.out.println("Email : " + email);
            System.out.println("Password : " + password);
            System.out.println("Permission : " + permission);
            System.out.println("Last connection time : " + lastConnectionTime);
        }
    }
    public void removeCustomer(CustomersMVC customer) {
        // TODO
    }
    public void updateCustomer(CustomersMVC customer) {
        // TODO
    }

}
