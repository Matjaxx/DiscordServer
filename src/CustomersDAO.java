import java.io.PrintWriter;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CustomersDAO {

    private String AnswerServer;

     private String speakingTo = "";
    public String Conv(){
        return  speakingTo;
    }


    public String getAnswerServer() {
        return AnswerServer;
    }

    private boolean verifConnection = false;

    private boolean banned = false;
    //creer un getter pour banned
    public boolean getBanned() {
        return banned;
    }
    private boolean verifFriend = false;
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
            else if (Objects.equals(getListCustomer.get(0), "requestFriendship")) {
                addFriend(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "requestlist")){
                FriendRequests(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "friendlist")){
                FriendUpdate(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "denyfriendrequest")){
                FriendRequestsDenier(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "conversation")){
                Conversation(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "GetConv")){
                getFriendsFromMessages(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "Convs")){
                System.out.println("MLMLMLML2");
                Convs(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "SendM")){
                AddMessage(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "SendMessageGraph")){
                AddMessageGraph(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "acceptfriendrequest")){
                FriendRequestAccepter(conn,st,rs,getListCustomer);
                String trade = getListCustomer.get(1);
                FriendRequestsDenier(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "banUser")){
                banUserRequest(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "freeUser")){
                freeUserRequest(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "beOnline")){
                beOnlineRequest(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "beDisconnect")){
                beDisconnectRequest(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "beAway")){
                beAwayRequest(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "seeMyFriendsOnline")){
                seeMyFriendsOnlineDAO(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "seeEveryLog")){
                seeEveryLog(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "countLog")){
                countLog(conn,st,rs);
            }
            else if (Objects.equals(getListCustomer.get(0), "getUSERLVL")){
                getUSERLVL(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "seeStat")){
                seeStat(conn,st,rs);
            }
            else if (Objects.equals(getListCustomer.get(0), "seeMyFriendsOnlineDAO2")){
                seeMyFriendsOnlineDAO2(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "viewlistBan")){
                viewlistBan(conn,st,rs);
            }
            else if (Objects.equals(getListCustomer.get(0), "viewlistFree")){
                viewlistFree(conn,st,rs);
            }
            else if (Objects.equals(getListCustomer.get(0), "modo")){
                modoUserRequest(conn,st,rs,getListCustomer);
            }
            else if (Objects.equals(getListCustomer.get(0), "admin")){
                adminUserRequest(conn,st,rs,getListCustomer);
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
            if (rs.getString("PERMISSION").equals("BAN"))
                banned = true;
            else if (rs.getString("PERMISSION").equals("Free"))
            verifConnection = true;
        }
        String sql4 = "SELECT * FROM CUSTOMER WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        rs = st.executeQuery(sql4);
        while (rs.next()) {
            if (!banned) {
                AnswerServer = "connected" + " " + rs.getString("USERNAME") + " " + rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME") + " " + rs.getString("EMAIL") + " " + rs.getString("PERMISSION") + " " + rs.getString("PASSWORD")+ " " + rs.getString("USERLVL");
            }
            else {
                AnswerServer = "banned";
            }
        }
        System.out.println("ONLINE");

        sql3 = "UPDATE Customer SET STATE = 'ONLINE' WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        sql3 = "UPDATE Customer SET Last_Connection = GETDATE() WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"beOnlineRequest",ID);

        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }
        ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"connectionCustomer",ID);
    }

    public void addFriend(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {

        st = conn.createStatement();
        String sql2 = "SELECT * FROM CUSTOMER WHERE USERNAME = '" + getListCustomer.get(2) + "'";
        rs = st.executeQuery(sql2);
        while (rs.next()) {
            verifFriend = true;
        }
        DatabaseMetaData dbmd = conn.getMetaData();
        rs = dbmd.getTables(null, null, "FRIENDREQUESTS", null);
        if (rs.next() && verifFriend) {
            if (verifFriend) {
                System.out.println("La table FRIENDREQUEST existe dans la base de données.");
                String sql3 = "INSERT INTO FRIENDREQUESTS (USERNAMEFRIENDREQUEST, USERNAMEFRIENDREQUESTED) " +
                        "VALUES ('" + getListCustomer.get(1) + "', '" + getListCustomer.get(2) + "')";
                st.executeUpdate(sql3);
                System.out.println("friend request has been sent");
                verifFriend = false;
            }
            else {
                System.out.println("friend doesn't exist");
            }


        } else {
            System.out.println("La table FRIENDREQUEST n'existe pas dans la base de données.");
            String sql = "CREATE TABLE FRIENDREQUESTS " +
                    "(USERNAMEFRIENDREQUEST VARCHAR(50), " +
                    " USERNAMEFRIENDREQUESTED VARCHAR(50))";
            st.executeUpdate(sql);
            System.out.println("Table FRIENDREQUEST created successfully...");
            if (verifFriend) {
                String sql3 = "INSERT INTO FRIENDREQUESTS (USERNAMEFRIENDREQUEST, USERNAMEFRIENDREQUESTED) " +
                        "VALUES ('" + getListCustomer.get(1) + "', '" + getListCustomer.get(2) + "')";
                st.executeUpdate(sql3);
                verifFriend = false;
            }
            else {
                System.out.println("friend doesn't exist");
            }
        }
        String sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }
        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"addFriend " + getListCustomer.get(2),ID);
    }
    public void FriendRequests(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        AnswerServer ="friendRequest" + " " ;
        String sql3 = "SELECT * FROM FRIENDREQUESTS WHERE USERNAMEFRIENDREQUESTED = '" + getListCustomer.get(1) + "'";
        rs = st.executeQuery(sql3);
        while (rs.next()) {
            AnswerServer = AnswerServer + rs.getString("USERNAMEFRIENDREQUEST")+ " ";
        }
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }
        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"FriendRequests",ID);
    }
    public void FriendUpdate(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        //creer un list de string
        List<String> friends = new ArrayList<String>();

        AnswerServer ="friendListUpdate" + " " ;
        String sql3 = "SELECT * FROM FRIENDLIST WHERE FRIEND1 = '" + getListCustomer.get(1) + "'";
        rs = st.executeQuery(sql3);
        while (rs.next()) {
            AnswerServer = AnswerServer + rs.getString("FRIEND2") + " ";
        }
        String sql4 = "SELECT * FROM FRIENDLIST WHERE FRIEND2 = '" + getListCustomer.get(1) + "'";
        rs = st.executeQuery(sql4);
        while (rs.next()) {
            AnswerServer = AnswerServer + rs.getString("FRIEND1") + " ";
        }
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"FriendUpdate",ID);
    }
    public void Conversation(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();

        speakingTo = getListCustomer.get(2);
        AnswerServer ="Conversation" + " " ;

        String sql = "SELECT * FROM MESSAGES WHERE (USERNAME = '" + getListCustomer.get(1) + "' AND USERNAME2 = '" + getListCustomer.get(2) + "') OR (USERNAME = '" + getListCustomer.get(2) + "' AND USERNAME2 = '" + getListCustomer.get(1) + "') ORDER BY DATEMESSAGE ASC;";
        rs = st.executeQuery(sql);
        while (rs.next()) {
            String sender = rs.getString("USERNAME");
            String recipient = rs.getString("USERNAME2");
            String content = rs.getString("CONTENT");
            String date = rs.getString("TIMES");
            System.out.println(date + " - " + sender + " to " + recipient + ": " + content);
            AnswerServer = AnswerServer + sender + " " + recipient + " " + date + " " + content + " ";
        }
    }

    public void Convs(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        System.out.println("MLMLMLML");
        speakingTo = getListCustomer.get(2);
        AnswerServer ="Convs" + " " ;

        String sql = "SELECT * FROM MESSAGES WHERE (USERNAME = '" + getListCustomer.get(1) + "' AND USERNAME2 = '" + getListCustomer.get(2) + "') OR (USERNAME = '" + getListCustomer.get(2) + "' AND USERNAME2 = '" + getListCustomer.get(1) + "') ORDER BY DATEMESSAGE ASC;";
        rs = st.executeQuery(sql);
        while (rs.next()) {
            String sender = rs.getString("USERNAME");
            String recipient = rs.getString("USERNAME2");
            String content = rs.getString("CONTENT");
            String date = rs.getString("TIMES");
            System.out.println(date + " - " + sender + " to " + recipient + ": " + content);
            AnswerServer = AnswerServer + sender + " " + recipient + " " + date + " " + content + " ";
        }
    }

    public void AddMessage(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        System.out.println("fritatos is here");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        for (int i = 0; i < getListCustomer.size(); i++) {
            System.out.println(getListCustomer.get(i) + " " + i);
        }
        if (getListCustomer.size() == 4) {
            String sql3 = "INSERT INTO MESSAGES (USERNAME, USERNAME2, CONTENT, TIMES, ORDRE) " +
                    "VALUES ('" + getListCustomer.get(2) + "','" + getListCustomer.get(1) + "','" + getListCustomer.get(3) + "','" + formattedDate + "',(SELECT COALESCE(MAX(ORDRE), 0) + 1 FROM MESSAGES))";

            st.executeUpdate(sql3);
        }
        else{
            String sql3 = "INSERT INTO MESSAGES (USERNAME, USERNAME2, CONTENT, TIMES, ORDRE) " +
                    "VALUES ('" + getListCustomer.get(1) + "','" + speakingTo + "','" + getListCustomer.get(2) + "','" + formattedDate + "',(SELECT COALESCE(MAX(ORDRE), 0) + 1 FROM MESSAGES))";

            st.executeUpdate(sql3);
        }
        System.out.println("fritatos is here2");
        AnswerServer ="Conversation" + " " + getListCustomer.get(1) + " " ;
        String sql = "SELECT * FROM MESSAGES WHERE (USERNAME = '" + getListCustomer.get(1) + "' AND USERNAME2 = '"+speakingTo+"') OR (USERNAME = '"+speakingTo+"' AND USERNAME2 = '" + getListCustomer.get(1) + "') ORDER BY ORDRE ASC;";
        rs = st.executeQuery(sql);
        while (rs.next()) {
            String sender = rs.getString("USERNAME");
            String recipient = rs.getString("USERNAME2");
            String content = rs.getString("CONTENT");
            String date = rs.getString("TIMES");
            System.out.println(date + " - " + sender + " to " + recipient + ": " + content);
            AnswerServer = AnswerServer + sender + " " + recipient + " " + date + " " + content + " ";
            System.out.println(AnswerServer+"le serveur envoie ca");
        }
        sql = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"AddMessage " + getListCustomer.get(2),ID);

    }
    public void AddMessageGraph(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        System.out.println("fritatos is here");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());
        for (int i = 0; i < getListCustomer.size(); i++) {
            System.out.println(getListCustomer.get(i) + " " + i);
        }
        if (getListCustomer.size() == 4) {
            String sql3 = "INSERT INTO MESSAGES (USERNAME, USERNAME2, CONTENT, TIMES, ORDRE) " +
                    "VALUES ('" + getListCustomer.get(2) + "','" + getListCustomer.get(1) + "','" + getListCustomer.get(3) + "','" + formattedDate + "',(SELECT COALESCE(MAX(ORDRE), 0) + 1 FROM MESSAGES))";

            st.executeUpdate(sql3);
        }
        else{
            String sql3 = "INSERT INTO MESSAGES (USERNAME, USERNAME2, CONTENT, TIMES, ORDRE) " +
                    "VALUES ('" + getListCustomer.get(1) + "','" + speakingTo + "','" + getListCustomer.get(2) + "','" + formattedDate + "',(SELECT COALESCE(MAX(ORDRE), 0) + 1 FROM MESSAGES))";

            st.executeUpdate(sql3);
        }
        System.out.println("fritatos is here2");
        AnswerServer ="Message Sent";

        String sql = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"AddMessage " + getListCustomer.get(2),ID);

    }
    public void FriendRequestsDenier(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();

        String sql3 = "DELETE FROM FRIENDREQUESTS WHERE USERNAMEFRIENDREQUEST = '" + getListCustomer.get(1) + "' AND USERNAMEFRIENDREQUESTED = '" + getListCustomer.get(2) + "'";
        st.executeUpdate(sql3);
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"FriendRequestsDenier " + getListCustomer.get(2),ID);
    }

    public void FriendRequestAccepter(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        System.out.println(getListCustomer.get(1));
        System.out.println(getListCustomer.get(2));
        String sql3 = "INSERT INTO FRIENDLIST (FRIEND1, FRIEND2) " +
                "VALUES ('" + getListCustomer.get(1) + "', '" + getListCustomer.get(2) + "')";
        st.executeUpdate(sql3);
        String a= getListCustomer.get(1);
        String b= getListCustomer.get(2);
        AnswerServer ="friendListUpdate" + a + " " + b;
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"FriendRequestAccepter " + getListCustomer.get(2),ID);
    }


    public void banUserRequest(Connection conn, Statement st,ResultSet rs, List<String> getListCustomer)throws ClassNotFoundException, SQLException{
        st = conn.createStatement();
        String s = "SELECT USERLVL FROM Customer WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(s);

        while (rs.next()){
            String admin = rs.getString("USERLVL");
            if (admin.equals("ADMIN") || admin.equals("MODO")){
                String sql3 = "UPDATE Customer SET PERMISSION = 'BAN' WHERE USERNAME = '" + getListCustomer.get(2) + "'";
                st.executeUpdate(sql3);
                sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(2) + "'";
                String IDString = "";
                rs = st.executeQuery(sql3);
                while (rs.next()){
                    IDString = rs.getString("ID");
                }

                int ID = Integer.parseInt(IDString);
                newLogDAO(conn,st,rs,"banUserRequest",ID);
                AnswerServer = "You are admin";
            }
            else {AnswerServer = "You are not admin";}
        }
    }




    public void freeUserRequest(Connection conn, Statement st,ResultSet rs, List<String> getListCustomer)throws ClassNotFoundException, SQLException{
        st = conn.createStatement();
        String s = "SELECT USERLVL FROM Customer WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(s);

        while (rs.next()){
            String admin = rs.getString("USERLVL");
            if (admin.equals("ADMIN") || admin.equals("MODO")){
                String sql3 = "UPDATE Customer SET PERMISSION = 'FREE' WHERE USERNAME = '" + getListCustomer.get(2) + "'";
                st.executeUpdate(sql3);
                sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(2) + "'";
                String IDString = "";
                rs = st.executeQuery(sql3);
                while (rs.next()){
                    IDString = rs.getString("ID");
                }

                int ID = Integer.parseInt(IDString);
                newLogDAO(conn,st,rs,"freeUserRequest",ID);
                System.out.println("You are admin");
            }
            else {System.out.println("You are not admin");}
        }
    }
    public void adminUserRequest(Connection conn, Statement st,ResultSet rs, List<String> getListCustomer)throws ClassNotFoundException, SQLException{
        st = conn.createStatement();
        String s = "SELECT USERLVL FROM Customer WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(s);

        while (rs.next()){
            String admin = rs.getString("USERLVL");
            if (admin.equals("ADMIN")){
                String sql3 = "UPDATE Customer SET USERLVL = 'ADMIN' WHERE USERNAME = '" + getListCustomer.get(2) + "'";
                st.executeUpdate(sql3);
                sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(2) + "'";
                String IDString = "";
                rs = st.executeQuery(sql3);
                while (rs.next()){
                    IDString = rs.getString("ID");
                }

                int ID = Integer.parseInt(IDString);
                newLogDAO(conn,st,rs,"adminUserRequest",ID);
                AnswerServer = "You are admin";
            }
            else {AnswerServer = "You are not admin";}
        }
    }
    public void modoUserRequest(Connection conn, Statement st,ResultSet rs, List<String> getListCustomer)throws ClassNotFoundException, SQLException{
        st = conn.createStatement();
        String s = "SELECT USERLVL FROM Customer WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(s);

        while (rs.next()){
            String admin = rs.getString("USERLVL");
            if (admin.equals("ADMIN")){
                String sql3 = "UPDATE Customer SET USERLVL = 'MODO' WHERE USERNAME = '" + getListCustomer.get(2) + "'";
                st.executeUpdate(sql3);
                sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(2) + "'";
                String IDString = "";
                rs = st.executeQuery(sql3);
                while (rs.next()){
                    IDString = rs.getString("ID");
                }

                int ID = Integer.parseInt(IDString);
                newLogDAO(conn,st,rs,"modoUserRequest",ID);
                AnswerServer = "You are admin";
            }
            else {AnswerServer = "You are not admin";}
        }
    }
    public void beOnlineRequest(Connection conn, Statement st,ResultSet rs, List<String> getListCustomer)throws ClassNotFoundException, SQLException{
        st = conn.createStatement();
        String sql3 = "UPDATE Customer SET STATE = 'ONLINE' WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        sql3 = "UPDATE Customer SET Last_Connection = GETDATE() WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"beOnlineRequest",ID);
    }



    public void beDisconnectRequest(Connection conn, Statement st,ResultSet rs, List<String> getListCustomer)throws ClassNotFoundException, SQLException{
        st = conn.createStatement();
        String sql3 = "UPDATE Customer SET STATE = 'DISCONNECT' WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        sql3 = "UPDATE Customer SET Last_Connection = GETDATE() WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        System.out.println("OFFLINE");
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"beDisconnectRequest",ID);
    }


    public void beAwayRequest(Connection conn, Statement st,ResultSet rs, List<String> getListCustomer)throws ClassNotFoundException, SQLException{
        st = conn.createStatement();
        String sql3 = "UPDATE Customer SET STATE = 'AWAY' WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        sql3 = "UPDATE Customer SET Last_Connection = GETDATE() WHERE USERNAME = '" + getListCustomer.get(1) + "'";
        st.executeUpdate(sql3);
        System.out.println("OFFLINE");
        sql3 = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql3);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"beAwayRequest",ID);
    }

    public void addCustomer(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws ClassNotFoundException, SQLException {
        st = conn.createStatement();
        DatabaseMetaData dbmd = conn.getMetaData();
        rs = dbmd.getTables(null, null, "CUSTOMER", null);
        if (rs.next()) {
            System.out.println("La table CUSTOMER existe dans la base de données.");
            String sql3 = "INSERT INTO CUSTOMER (ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PERMISSION, Last_Connection,USERLVL) " +
                    "VALUES ('" + getListCustomer.get(1) + "', '" + getListCustomer.get(2) + "', '" + getListCustomer.get(3) + "', '" +
                    getListCustomer.get(4) + "', '" + getListCustomer.get(5) + "', '" + getListCustomer.get(6) + "', '" +
                    getListCustomer.get(7) + "', GETDATE(), '"+ getListCustomer.get(8) +"')";
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

            String sql3 = "INSERT INTO CUSTOMER (ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PERMISSION, Last_Connection,USERLVL) " +
                    "VALUES ('" + getListCustomer.get(1) + "', '" + getListCustomer.get(2) + "', '" + getListCustomer.get(3) + "', '" +
                    getListCustomer.get(4) + "', '" + getListCustomer.get(5) + "', '" + getListCustomer.get(6) + "', '" +
                    getListCustomer.get(7) + "', GETDATE(), + getListCustomer.get(8) +)";
            st.executeUpdate(sql3);
            displayCustomerInfo(conn,st,rs);

            sql = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
            String IDString = "";
            rs = st.executeQuery(sql);
            while (rs.next()){
                IDString = rs.getString("ID");
            }

            int ID = Integer.parseInt(IDString);
            newLogDAO(conn,st,rs,"addCustomer",ID);
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


    public void seeMyFriendsOnlineDAO(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer)throws  SQLException{
        st = conn.createStatement();

        String sql;
        String result = "";
        for (int i = 2; i < getListCustomer.size(); i++) {
            sql = "SELECT  Last_Connection FROM Customer WHERE USERNAME = '" + getListCustomer.get(i) + "'";
            rs = st.executeQuery(sql);
            while (rs.next()){
                String Last_Connection = rs.getString("Last_Connection");
                System.out.println(getListCustomer.get(i) + " : " + Last_Connection);
                result += getListCustomer.get(i) + ":" + Last_Connection + " ";
            }
        }
        AnswerServer = result;
        sql = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"seeMyFriendsOnlineDAO",ID);
    }

    public void seeMyFriendsOnlineDAO2(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer)throws  SQLException{
        st = conn.createStatement();
        AnswerServer = "";
        String sql;
        String result = "";
            sql = "SELECT  * FROM Customer WHERE USERNAME = '" + getListCustomer.get(2) + "'";
            rs = st.executeQuery(sql);
            while (rs.next()){
                String Last_Connection = rs.getString("Last_Connection");
                String STATE = rs.getString("STATE");
                if(STATE.contains("ONLINE")){
                    System.out.println(getListCustomer.get(2) + " : " + STATE);
                    result += STATE ;
                }
                if(STATE.contains("DISCONNECT")){
                    System.out.println(getListCustomer.get(2) + " : " + STATE);
                    result += STATE + ":" + Last_Connection ;
                }

            }

        AnswerServer = result;
        sql = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"seeMyFriendsOnlineDAO2",ID);
    }




    public List<String> seeMyFriendsOnlineDAOGraphique(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer)throws  SQLException{
        st = conn.createStatement();
        List<String> seeMyFriendsOnline = new ArrayList<>();
        String sql;
        for (int i = 2; i < getListCustomer.size(); i++) {
            sql = "SELECT  Last_Connection FROM Customer WHERE USERNAME = '" + getListCustomer.get(i) + "'";
            rs = st.executeQuery(sql);
            while (rs.next()){
                String Last_Connection = rs.getString("Last_Connection");
                seeMyFriendsOnline.add(getListCustomer.get(i) + ":" + Last_Connection + " ");
            }
        }
        sql = "SELECT ID FROM Customer WHERE USERNAME = '"+ getListCustomer.get(1) + "'";
        String IDString = "";
        rs = st.executeQuery(sql);
        while (rs.next()){
            IDString = rs.getString("ID");
        }

        int ID = Integer.parseInt(IDString);
        newLogDAO(conn,st,rs,"seeMyFriendsOnlineDAO",ID);
        return seeMyFriendsOnline;
    }
    public void newLogDAO(Connection conn, Statement st, ResultSet rs, String nameFonction,int UserID)throws  SQLException{
        st = conn.createStatement();

        int newID = 0;
        String sql = "SELECT ID FROM LOGS";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String ID = rs.getString("ID");
            newID = Integer.parseInt(ID);
        }
        newID++;

        System.out.println(newID);
        sql = " INSERT INTO LOGS (ID, USER_ID, TYPELOG, TIMELOG) VALUES ('" + newID + "', '" + UserID + "','" + nameFonction + "', GETDATE())";
        st.executeUpdate(sql);
    }



    public void seeEveryLog(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer)throws SQLException{
        st = conn.createStatement();

        String s = "SELECT USERLVL FROM Customer WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(s);

        while (rs.next()){
            String admin = rs.getString("USERLVL");
            if (admin.equals("ADMIN")){
                String sql = "SELECT * FROM LOGS";
                rs = st.executeQuery(sql);
                String result ="";
                while (rs.next()){
                    String ID = rs.getString("ID");
                    String USER_ID = rs.getString("USER_ID");
                    String TYPELOG = rs.getString("TYPELOG");
                    String TIMELOG = rs.getString("TIMELOG");

                    result += "ID:" + ID + ";USER_ID:" + USER_ID+ ";TYPELOG:" + TYPELOG+ ";TIMELOG:"+ TIMELOG + " ";
                }
                AnswerServer = result;
            }
            else {AnswerServer = "You are not admin";}
        }

    }


    public List<String> seeEveryLogGraphique(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer)throws SQLException{
        st = conn.createStatement();
        List<String> listLog = new ArrayList<>();
        String s = "SELECT USERLVL FROM Customer WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(s);

        while (rs.next()){
            String admin = rs.getString("USERLVL");
            if (admin.equals("ADMIN")){
                String sql = "SELECT * FROM LOGS";
                rs = st.executeQuery(sql);
                while (rs.next()){
                    String ID = rs.getString("ID");
                    String USER_ID = rs.getString("USER_ID");
                    String TYPELOG = rs.getString("TYPELOG");
                    String TIMELOG = rs.getString("TIMELOG");

                    listLog.add("ID:" + ID + ";USER_ID:" + USER_ID+ ";TYPELOG:" + TYPELOG+ ";TIMELOG:"+ TIMELOG + " ");
                }
            }
            else {
                listLog.add("You are not admin");
                }
        }
        return listLog;

    }

    public List<Integer> countLog(Connection conn, Statement st, ResultSet rs)throws SQLException{
        st = conn.createStatement();
        String sql = "SELECT * FROM LOGS";
        rs = st.executeQuery(sql);
        List<Integer> nbLog = new ArrayList<>();
        int connectionCustomer = 0;
        int AddMessage = 0;
        int banUserRequest = 0;
        int freeUserRequest = 0;
        int seeMyFriendsOnlineDAO = 0;
        int FriendUpdate = 0;
        int beDisconnectRequest = 0;
        int beOnlineRequest = 0;
        int beAwayRequest = 0;
        int modoUserRequest = 0;
        int adminUserRequest = 0;


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
                case "beDisconnectRequest":
                    beDisconnectRequest++;
                    break;
                case "beOnlineRequest":
                    beOnlineRequest++;
                    break;
                case "beAwayRequest":
                    freeUserRequest++;
                    break;
                case "modoUserRequest":
                    modoUserRequest++;
                    break;
                case "adminUserRequest":
                    adminUserRequest++;
                    break;
            }
        }
        nbLog.add(connectionCustomer);
        nbLog.add(AddMessage);
        nbLog.add(FriendUpdate);
        nbLog.add(seeMyFriendsOnlineDAO);
        nbLog.add(banUserRequest);
        nbLog.add(freeUserRequest);
        nbLog.add(beDisconnectRequest);
        nbLog.add(beOnlineRequest);
        nbLog.add(beAwayRequest);
        nbLog.add(modoUserRequest);
        nbLog.add(adminUserRequest);

        AnswerServer = "";

        for (int i = 0; i < nbLog.size(); i++) {
            System.out.println(nbLog.get(i));
            AnswerServer += nbLog.get(i) + " ";
        }

        return nbLog;
    }


    public String getFriendsFromMessages(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer)throws SQLException{
        st = conn.createStatement();

        String sql = "SELECT USERNAME FROM MESSAGES WHERE USERNAME2 = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(sql);
        List<String> friendsConv = new ArrayList<>();
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            if (!friendsConv.contains(USERNAME)){
                friendsConv.add(USERNAME);
            }
        }
        sql = "SELECT USERNAME2 FROM MESSAGES WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME2 = rs.getString("USERNAME2");
            if (!friendsConv.contains(USERNAME2)){
                friendsConv.add(USERNAME2);
            }
        }
        for (int i = 0; i < friendsConv.size(); i++) {
            System.out.println(friendsConv.get(i));
        }
        AnswerServer = "getConvs" + " ";
        System.out.println("fritatos is hereee");
        for (int i = 0; i < friendsConv.size(); i++) {
            AnswerServer = AnswerServer + friendsConv.get(i) + " ";
        }
        return AnswerServer;
    }

    public List<String> listBan(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listBan = new ArrayList<>();
        listBan.add("BAN");
        String sql = "SELECT USERNAME FROM Customer WHERE PERMISSION = 'BAN'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listBan.add(USERNAME);
        }
        return listBan;
    }

    public void viewlistBan(Connection conn, Statement st, ResultSet rs) throws SQLException{
        List<String> listBan = new ArrayList<>();
        listBan = listBan(conn,st,rs);
        AnswerServer = "";
        for (String ban:listBan) {
            AnswerServer += ban + " ";
        }
    }

    public List<String> listFree(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listBan = new ArrayList<>();
        listBan.add("FREE");
        String sql = "SELECT USERNAME FROM Customer WHERE PERMISSION = 'FREE'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listBan.add(USERNAME);
        }
        return listBan;
    }

    public void viewlistFree(Connection conn, Statement st, ResultSet rs) throws SQLException{
        List<String> listFree = new ArrayList<>();
        listFree = listFree(conn,st,rs);
        AnswerServer = "";
        for (String free:listFree) {
            AnswerServer += free + " ";
        }
    }

    public List<String> listOnline(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listOnline = new ArrayList<>();
        listOnline.add("ONLINE");
        String sql = "SELECT USERNAME FROM Customer WHERE STATE = 'ONLINE'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listOnline.add(USERNAME);
        }
        return listOnline;
    }

    public List<String> listDisconnect(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listDisconnect = new ArrayList<>();
        listDisconnect.add("DISCONNECT");
        String sql = "SELECT USERNAME FROM Customer WHERE STATE = 'DISCONNECT'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listDisconnect.add(USERNAME);
        }
        return listDisconnect;
    }

    public List<String> listAway(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listAway = new ArrayList<>();
        listAway.add("AWAY");
        String sql = "SELECT USERNAME FROM Customer WHERE STATE = 'AWAY'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listAway.add(USERNAME);
        }
        return listAway;
    }

    public List<String> listUser(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listUser = new ArrayList<>();
        listUser.add("USER");
        String sql = "SELECT USERNAME FROM Customer WHERE USERLVL = 'USER'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listUser.add(USERNAME);
        }
        return listUser;
    }


    public List<String> listModo(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listModo = new ArrayList<>();
        listModo.add("MODO");
        String sql = "SELECT USERNAME FROM Customer WHERE USERLVL = 'MODO'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listModo.add(USERNAME);
        }
        return listModo;
    }

    public List<String> listAdmin(Connection conn, Statement st, ResultSet rs) throws SQLException{
        st = conn.createStatement();
        List<String> listAdmin = new ArrayList<>();
        listAdmin.add("ADMIN");
        String sql = "SELECT USERNAME FROM Customer WHERE USERLVL = 'ADMIN'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERNAME = rs.getString("USERNAME");
            listAdmin.add(USERNAME);
        }
        return listAdmin;
    }

    public void getUSERLVL(Connection conn, Statement st, ResultSet rs, List<String> getListCustomer) throws SQLException{
        st = conn.createStatement();
        String sql = "SELECT USERLVL FROM Customer WHERE USERNAME = '" + getListCustomer.get(1)+ "'";
        rs = st.executeQuery(sql);
        while (rs.next()){
            String USERLVL = rs.getString("USERLVL");
            AnswerServer = USERLVL;
        }
    }

    public void seeStat(Connection conn, Statement st, ResultSet rs)throws SQLException{
        List<String> ADMIN = new ArrayList<>();
        List<String> MODO = new ArrayList<>();
        List<String> USER = new ArrayList<>();
        List<String> ONLINE = new ArrayList<>();
        List<String> DISCONNECT = new ArrayList<>();
        List<String> AWAY = new ArrayList<>();
        List<String> BAN = new ArrayList<>();

        ADMIN = listAdmin(conn,st,rs);
        MODO = listModo(conn,st,rs);
        USER = listUser(conn,st,rs);
        ONLINE = listOnline(conn,st,rs);
        DISCONNECT = listDisconnect(conn,st,rs);
        AWAY = listAway(conn,st,rs);
        BAN = listBan(conn,st,rs);

        for (String o: ADMIN) {
            AnswerServer += o + " ";
        }
        for (String o: MODO) {
            AnswerServer += o + " ";
        }
        for (String o: USER) {
            AnswerServer += o + " ";
        }
        for (String o: ONLINE) {
            AnswerServer += o + " ";
        }
        for (String o: DISCONNECT) {
            AnswerServer += o + " ";
        }
        for (String o: AWAY) {
            AnswerServer += o + " ";
        }
        for (String o: BAN) {
            AnswerServer += o + " ";
        }

    }
}
