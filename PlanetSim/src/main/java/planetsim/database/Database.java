/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.database;

/**
 *
 * @author anttkukk
 */
import java.net.URI;
import java.sql.*;
import java.util.*;

public class Database {

    private Connection connection;
    private String databaseAddress;

    public Database(String address) throws Exception {
        this.connection = DriverManager.getConnection(address);
        this.databaseAddress = address;

        init();
    }

    public <T> ArrayList<T> queryAndCollect(String query, Collector<T> col, Object... params) throws SQLException {
        ArrayList<T> rivit = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            rivit.add(col.collect(rs));
        }

        rs.close();
        stmt.close();
        return rivit;
    }

    public int update(String updateQuery, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        stmt.close();

        return changes;
    }

    private void init() {
        List<String> sentences = null;
        sentences = sqliteCommands();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String sentence : sentences) {
                System.out.println("Running command >> " + sentence);
                st.executeUpdate(sentence);
            }

        } catch (Throwable t) {
            // If database excists, nothing will be done
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> sqliteCommands() {
        ArrayList<String> list = new ArrayList<>();

        // initializing database
        list.add("PRAGMA foreign_keys=ON;");
        list.add("CREATE TABLE System (id integer PRIMARY KEY, name varchar(20) UNIQUE);");
        list.add("INSERT INTO System VALUES(1,'default');");
        list.add("CREATE TABLE Planet (id integer Primary KEY, name varchar(30) UNIQUE, posx real, posy real, velx real, vely real, mass real, color varchar(15), size integer);");
        list.add("INSERT INTO Planet VALUES(1,'Sun',0.0,0.0,0.0,0.0,1.989e+30,'0xffff00ff',3);");
        list.add("INSERT INTO Planet VALUES(2,'Earth',152100000000.0,0.0,0.0,29290.0,5.97237e+24,'0x0000ffff',3);");
        list.add("INSERT INTO Planet VALUES(3,'Mars', 2.279392E11, 0, 0, 24077, 6.4171E23, '0xff6347ff', 3);");
        list.add("INSERT INTO Planet VALUES(4, 'Moon', 1.52484399E11, 0, 0, 30312, 7.342E22, '0x808080ff', 2);");
        list.add("INSERT INTO Planet VALUES(5, 'Venus', 1.08208E11, 0, 0, 35020, 4.8675E24, '0xdaa520ff', 3);");
        list.add("INSERT INTO Planet VALUES(6, 'Mercury', 5.790905E10, 0, 0, 47362, 3.3011E23, '0x808080ff', 3);");
        list.add("INSERT INTO Planet VALUES(7, 'Jupiter', 7.78412E11, 0, 0, 13070, 1.899E27, '0xffa07aff', 3);");
        list.add("INSERT INTO Planet VALUES(8, '67P/Churyumov–Gerasimenko', 1.8598E11, 0, 0, 34220, 0, 'r', 2);");
        list.add("INSERT INTO Planet VALUES(9, 'Unknown Comet', 0, 2.99E10, -89353, 0, 0, 'r', 2);");
        list.add("INSERT INTO Planet VALUES(10, 'Uranus', 2.8709722E12, 0, 0, 6835.2, 8.686E25, '0x87ceebff', 3);");
        list.add("INSERT INTO Planet VALUES(11, 'Neptune', 4.4982529E12, 0, 0, 5430, 1.0243E26, '0x0000ffff', 3);");
        list.add("CREATE TABLE Systemplanet (system integer NOT NULL, planet integer NOT NULL, FOREIGN KEY(system) REFERENCES System(id), FOREIGN KEY(planet) REFERENCES Planet(id));");
        list.add("INSERT INTO Systemplanet VALUES(1,1);");
        list.add("INSERT INTO Systemplanet VALUES(1,2);");
        list.add("INSERT INTO Systemplanet VALUES(1,3);");
        list.add("INSERT INTO Systemplanet VALUES(1,4);");
        list.add("INSERT INTO Systemplanet VALUES(1,5);");
        list.add("INSERT INTO Systemplanet VALUES(1,6);");
        list.add("INSERT INTO Systemplanet VALUES(1,7);");
        list.add("INSERT INTO Systemplanet VALUES(1,8);");
        list.add("INSERT INTO Systemplanet VALUES(1,9);");
        list.add("INSERT INTO Systemplanet VALUES(2,1);");
        list.add("INSERT INTO Systemplanet VALUES(2,2);");
        list.add("INSERT INTO Systemplanet VALUES(2,3);");
        list.add("INSERT INTO Systemplanet VALUES(2,5);");
        list.add("INSERT INTO Systemplanet VALUES(2,6);");
        list.add("INSERT INTO Systemplanet VALUES(2,7);");
        list.add("INSERT INTO Systemplanet VALUES(2,10);");
        list.add("INSERT INTO Systemplanet VALUES(2,11);");

        return list;
    }
}
