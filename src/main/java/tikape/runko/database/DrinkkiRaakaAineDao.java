package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.DrinkkiRaakaAine;
import tikape.runko.domain.RaakaAine;

public class DrinkkiRaakaAineDao implements Dao<DrinkkiRaakaAine, Integer> {

    private Database database;

    public DrinkkiRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public DrinkkiRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DrinkkiRaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String drinkkiId = rs.getString("drinkkiId");
        String raakaAineId = rs.getString("RaakaAineId");
        Integer lukumaara = rs.getInt("Maara");
        Integer jarjestys = rs.getInt("Jarjestys");
        String ohje = rs.getString("ohje");

        DrinkkiRaakaAine o = new DrinkkiRaakaAine(id, drinkkiId, raakaAineId, lukumaara, jarjestys, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<DrinkkiRaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DrinkkiRaakaAineet");

        ResultSet rs = stmt.executeQuery();
        List<DrinkkiRaakaAine> DrinkkiRaakaAineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            String drinkkiId = rs.getString("drinkkiId");
            String raakaAineId = rs.getString("raakaAineId");
            Integer lukumaara = rs.getInt("lukumaara");
            Integer jarjestys = rs.getInt("jarjestys");
            String ohje = rs.getString("ohje");

            DrinkkiRaakaAineet.add(new DrinkkiRaakaAine(id, drinkkiId, raakaAineId, lukumaara, jarjestys, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return DrinkkiRaakaAineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        connection.close();

    }

    // Tallentaa tai päivittää Drinkin. Jos drinkillä ei ole asetettuna 
    // pääavainta, drinkki tallennetaan tietokantaan. Jos pääavain on asetettu, 
    // vanhan drinkin tiedot tulee päivittää
    @Override
    public DrinkkiRaakaAine saveOrUpdate(DrinkkiRaakaAine drinkkiRaakaAine) throws SQLException {
        if (drinkkiRaakaAine.getId() == -1) {
            return save(drinkkiRaakaAine);
        } else {
            return update(drinkkiRaakaAine);

        }
    }

    private DrinkkiRaakaAine save(DrinkkiRaakaAine object) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO DrinkkiRaakaAine"
                + " (id, drinkkiId, raakaAineId, lukumaara, jarjestys, ohje)"
                + " VALUES (?, ?, ?, ?, ?, ?)");
//      

        stmt.setString(2, object.getDrinkkiId());
        stmt.setString(3, object.getRaakaAineId());

        stmt.setInt(4, object.getJarjestys());
        stmt.setInt(5, object.getLukumaara());
        stmt.setString(6, object.getOhje());

        stmt.executeUpdate();
        stmt.close();

        /*       stmt = conn.prepareStatement("SELECT * FROM RaakaAine"
                + " WHERE id = ? AND nimi = ?");
        stmt.setInt(1, raakaAine.getId());
        stmt.setString(2, raakaAine.getNimi());

        ResultSet rs = stmt.executeQuery();
        rs.next(); // vain 1 tulos

        RaakaAine d = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return d; */
        return this.findOne(object.getId());

    }

    private DrinkkiRaakaAine update(DrinkkiRaakaAine object) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE DrinkkiRaakaAine SET"
                + " (id, drinkkiId, raakaAineId, lukumaara, jarjestys, ohje)"
                + " VALUES (?, ?, ?, ?, ?, ?)");
//      

        stmt.setString(2, object.getDrinkkiId());
        stmt.setString(3, object.getRaakaAineId());

        stmt.setInt(4, object.getJarjestys());
        stmt.setInt(5, object.getLukumaara());
        stmt.setString(6, object.getOhje());

        stmt.executeUpdate();
        stmt.close();

        return this.findOne(object.getId());
    }
}
