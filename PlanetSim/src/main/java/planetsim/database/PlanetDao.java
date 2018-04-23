/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import planetsim.domain.Planet;

/**
 *
 * @author anttkukk
 */
public class PlanetDao implements Dao<Planet, Integer> {

    private Database db;

    public PlanetDao(Database db) {
        this.db = db;
    }

    @Override
    public Planet findOne(Integer key) throws SQLException {
        ArrayList<Planet> planet = this.db.queryAndCollect("SELECT * FROM Planet WHERE id = ?", new PlanetCollector(), key);
        if (planet.isEmpty()) {
            return null;
        }
        return planet.get(0);
    }

    @Override
    public void save(Planet p) throws SQLException {
        this.db.update("INSERT INTO Planet(name, posx, posy, velx, vely, mass, color, size)", p.getName(), p.getPos().getX(), p.getPos().getY(), p.getVel().getX(), p.getVel().getY(), p.getMass(), p.getColor().toString(), p.getDrawSize());

    }

    @Override
    public ArrayList<Planet> findAll() throws SQLException {
        ArrayList<Planet> planets = this.db.queryAndCollect("SELECT * FROM Planet", new PlanetCollector());
        return planets;
    }

    public ArrayList<Planet> findAllFromSystem(Integer key) throws SQLException {
        ArrayList<Planet> planets = this.db.queryAndCollect("SELECT p.* FROM System s, Planet p, Systemplanet sp WHERE s.id = sp.system AND sp.planet = p.id AND s.id = ?", new PlanetCollector(), key);
        return planets;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        this.db.update("DELETE FROM Planet WHERE id = ?", key);
    }

    public Integer countSystems() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as systems FROM System;");
        ResultSet result = stmt.executeQuery();
        Integer i = 0;
        while (result.next()) {
            i = Integer.parseInt(result.getString("systems"));
        }
        conn.close();
        return i;
    }

}
