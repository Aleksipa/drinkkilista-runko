/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;

public class DrinkkiDao implements Dao<Drinkki, Integer> {

    private Database database;

    public DrinkkiDao(Database database) {
        this.database = database;
    }

    @Override
    public Drinkki findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Opiskelija WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Drinkki o = new Drinkki(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Drinkki> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Opiskelija");

        ResultSet rs = stmt.executeQuery();
        List<Drinkki> opiskelijat = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            opiskelijat.add(new Drinkki(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return opiskelijat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
    Connection connection = database.getConnection();
    PreparedStatement stmt = connection.prepareStatement("DELETE FROM Opiskelija WHERE id = ?");
    
        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    
    }
    // Tallentaa tai päivittää Drinkin. Jos drinkillä ei ole asetettuna 
    // pääavainta, drinkki tallennetaan tietokantaan. Jos pääavain on asetettu, 
    // vanhan drinkin tiedot tulee päivittää
    @Override
    public Drinkki saveOrUpdate(Drinkki drinkki) throws SQLException {
        if (drinkki.getId() == null) {
            return save(drinkki);
        } else {
            return update(drinkki);
        }
    }
    private Drinkki save(Drinkki drinkki) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Drinkki"
                + " (id, nimi)"
                + " VALUES (?, ?)");
        stmt.setInt(1, drinkki.getId());
        stmt.setString(2, drinkki.getNimi());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Drinkki"
                + " WHERE nimi = ? AND puhelinnumero = ?");
        stmt.setInt(1, drinkki.getId());
        stmt.setString(2, drinkki.getNimi());

        ResultSet rs = stmt.executeQuery();
        rs.next(); // vain 1 tulos

        Drinkki d = new Drinkki(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return d;
    }

    private Drinkki update(Drinkki drinkki) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Drinkki SET"
                + "nimi = ? WHERE id = ?");
        stmt.setString(1, drinkki.getNimi());
        stmt.setInt(2, drinkki.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();

        return drinkki;
    }

}
