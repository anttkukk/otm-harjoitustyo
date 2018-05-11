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
        this.db.update("DELETE FROM Planet WHERE name = ? AND id > 12", key);
    }

    /**
     * Counts number of systems in the database
     *
     * @return Returns number of systems as an Integer
     * @throws SQLException if SQL query fails
     */
    public Integer countSystems() throws SQLException {
        Integer i = this.db.queryInteger("SELECT COUNT(*) as systems FROM System;", "systems");
        return i;
    }

    /**
     * Counts all planets in the database
     *
     * @return Integer value of all planets in database
     * @throws SQLException if SQL query fails
     */
    public Integer countPlanets() throws SQLException {
        Integer i = this.db.queryInteger("SELECT COUNT(*) as planets FROM Planet;", "planets");

        return i;
    }

    /**
     * Gets the names of all systems in the database
     *
     * @return ArrayList of all the names of the systems in the database
     * @throws SQLException if SQL query fails
     */
    public ArrayList<String> getSystems() throws SQLException {
        ArrayList<String> systems = this.db.queryStringList("SELECT name FROM system ORDER BY id;", "name");
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

        ArrayList<String> list = this.db.queryStringList("SELECT name FROM system WHERE id = ?;", "name", key);
        return list.get(0);
    }

    /**
     * Finds names of all the planets in the database
     *
     * @return ArrayList of planet names
     * @throws SQLException if SQL query fails
     */
    public ArrayList<String> getAllPlanetNames() throws SQLException {
        ArrayList<String> names = this.db.queryStringList("SELECT name FROM planet ORDER BY id;", "name");
        return names;
    }

    /**
     * Adds a new system to database
     *
     * @param name Name of the system
     * @throws SQLException if SQL query fails
     */
    public void addSystem(String name) throws SQLException {
        this.db.update("INSERT INTO System(name) VALUES (?);", name);
    }

    /**
     * Deletes a system from database
     *
     * @param name Name of the system to be deleted
     * @param system Id of the system to be deleted
     * @throws SQLException If SQL query fails
     */
    public void deleteSystem(String name, Integer system) throws SQLException {
        this.db.update("DELETE FROM System WHERE name = ?;", name);
        this.db.update("DELETE FROM Systemplanet WHERE system = ?;", system);
    }

    /**
     * Finds id of a system with name
     *
     * @param name The name of the system
     * @return id of the system as an integer
     * @throws SQLException if SQL query fails
     */
    public Integer getSystemId(String name) throws SQLException {
        Integer id = this.db.queryInteger("SELECT id FROM System WHERE name = ?;", "id", name);
        return id;
    }

    /**
     * Gets the highest id from system table
     *
     * @return the highest id as an integer
     * @throws SQLException If SQL query fails
     */
    public Integer getHighestSystemId() throws SQLException {
        Integer id = this.db.queryInteger("SELECT id FROM System ORDER BY id DESC LIMIT 1;", "id");
        return id;
    }

    /**
     * Finds the id of a planet as an integer value with the planet name
     *
     * @param name The name of the planet
     * @return The id of a planet as an integer
     * @throws SQLException if SQL query fails
     */
    private Integer getPlanetId(String name) throws SQLException {
        Integer id = this.db.queryInteger("SELECT id FROM Planet WHERE name = ?;", "id", name);
        return id;
    }

    /**
     * Saves a planet to a system by saving planet and system id's to
     * systemplanet table
     *
     * @param planet Name of the planet to be saved
     * @throws SQLException if SQL query fails
     */
    public void savePlanetToSystem(String planet) throws SQLException {
        Integer planetId = getPlanetId(planet);
        Integer systemId = getHighestSystemId();
        this.db.update("INSERT INTO Systemplanet VALUES (?,?);", systemId, planetId);
    }

    /**
     * Finds all planets from database that are not in the system
     *
     * @param system Finds planets outside a system with this id
     * @return ArrayList of planets outside the system
     * @throws SQLException if SQL query fails
     */
    public ArrayList<String> findAllPlanetsOutsideSystem(int system) throws SQLException {
        ArrayList<String> planets = this.db.queryStringList("SELECT p.name AS name FROM Planet p WHERE p.id NOT IN (SELECT planet FROM Systemplanet WHERE system = ?) ORDER BY id;", "name", system);
        return planets;
    }

}
