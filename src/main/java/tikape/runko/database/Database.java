package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

  /*    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    } */ 
    public Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Drinkki (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO Drinkki (nimi) VALUES ('Gintonic');");
        lista.add("INSERT INTO Drinkki (nimi) VALUES ('Whiskey sour');");
        lista.add("INSERT INTO Drinkki (nimi) VALUES ('Ville vallaton');");
        lista.add("CREATE TABLE RaakaAine (id integer PRIMARY KEY, nimi varchar(255));");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Appelsiinimehu');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Mandariinimehu');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Maito');");

        return lista;
    }
}
