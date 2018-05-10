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
        this.db.update("INSERT INTO Planet(name, posx, posy, velx, vely, mass, color, size) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", p.getName(), p.getPos().getX(), p.getPos().getY(), p.getVel().getX(), p.getVel().getY(), p.getMass(), p.getColor().toString(), p.getDrawSize());

    }

    @Override
    public ArrayList<Planet> findAll() throws SQLException {
        ArrayList<Planet> planets = this.db.queryAndCollect("SELECT * FROM Planet", new PlanetCollector());
        return planets;
    }

    /**
     * Finds all from specific system with integer key
     *
     * @param key Used to identify system with its id
     * @return Returns ArrayList of all the planets in the system
     * @throws SQLException if SQL query fails
     */
    public ArrayList<Planet> findAllFromSystem(Integer key) throws SQLException {
        ArrayList<Planet> planets = this.db.queryAndCollect("SELECT p.* FROM System s, Planet p, Systemplanet sp WHERE s.id = sp.system AND sp.planet = p.id AND s.id = ?", new PlanetCollector(), key);
        return planets;
    }

    @Override
    public void delete(String key) throws SQLException {
        this.db.update("DELETE FROM Planet WHERE name = ?", key);
    }

    /**
     * Counts number of systems in the database
     *
     * @return Returns number of systems as an Integer
     * @throws SQLException if SQL query fails
     */
    public Integer countSystems() throws SQLException {
        ResultSet result = this.db.queryWithoutCollector("SELECT COUNT(*) as systems FROM System;");
        Integer i = 0;
        while (result.next()) {
            i = Integer.parseInt(result.getString("systems"));
        }
        result.close();
        return i;
    }

    /**
     * Counts all planets in the database
     *
     * @return Integer value of all planets in database
     * @throws SQLException if SQL query fails
     */
    public Integer countPlanets() throws SQLException {
        ResultSet result = this.db.queryWithoutCollector("SELECT COUNT(*) as planets FROM Planet;");
        Integer i = 0;
        while (result.next()) {
            i = Integer.parseInt(result.getString("planets"));
        }
        result.close();
        return i;
    }

    /**
     * Gets the names of all systems in the database
     *
     * @return ArrayList of all the names of the systems in the database
     * @throws SQLException if SQL query fails
     */
    public ArrayList<String> getSystems() throws SQLException {
        ArrayList<String> systems = new ArrayList<>();
        ResultSet result = this.db.queryWithoutCollector("SELECT name FROM system ORDER BY id;");
        while (result.next()) {
            systems.add(result.getString("name"));
        }
        result.close();
        return systems;
    }

    /**
     * Gets the name of a system with id key
     *
     * @param key Key used to identify the system which is searched
     * @return String of the name of the system
     * @throws SQLException if SQL query fails
     */
    public String getSystemName(int key) throws SQLException {

        ResultSet result = this.db.queryWithoutCollector("SELECT name FROM system WHERE id = ?;", key);
        String name = result.getString("name");
        result.close();
        return name;
    }

    /**
     * Finds names of all the planets in the database
     *
     * @return ArrayList of planet names
     * @throws SQLException if SQL query fails
     */
    public ArrayList<String> getAllPlanetNames() throws SQLException {
        ArrayList<String> names = new ArrayList<>();
        ResultSet result = this.db.queryWithoutCollector("SELECT name FROM planet ORDER BY id");
        while (result.next()) {
            names.add(result.getString("name"));
        }
        result.close();
        return names;
    }

    /**
     * Adds a new system to database
     *
     * @param name Name of the system
     * @throws SQLException if SQL query fails
     */
    public void addSystem(String name) throws SQLException {
        this.db.update("INSERT INTO System(name) VALUES (?)", name);
    }
    
    /**
     * Deletes a system from database
     * @param name Name of the planet to be deleted
     * @throws SQLException 
     */
    public void deleteSystem(String name) throws SQLException {
        this.db.update("DELETE FROM System WHERE name = ?", name);
    }

}
