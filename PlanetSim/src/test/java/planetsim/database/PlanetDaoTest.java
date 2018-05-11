/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.database;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetsim.domain.Planet;

/**
 *
 * @author anttkukk
 */
public class PlanetDaoTest {

    private Database db;
    private PlanetDao planetDao;

    public PlanetDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        db = new Database("jdbc:sqlite:testDatabase.db");
        planetDao = new PlanetDao(db);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void findAllFromSystemIsWorking() throws SQLException {
        ArrayList<Planet> planets = planetDao.findAllFromSystem(1);
        assertTrue(planets.size() == 9);
    }

    @Test
    public void findOneIsWorking() throws SQLException {
        Planet p = planetDao.findOne(1);
        assertEquals(p.getName(), "Sun");
    }

    @Test
    public void findOneWontFindIfThereIsNoSuchPlanet() throws SQLException {
        Planet p = planetDao.findOne(0);
        assertNull(p);
    }

    @Test
    public void findAllIsWorking() throws SQLException {
        ArrayList<Planet> planets = planetDao.findAll();
        assertTrue(planets.size() > 0);
    }

    @Test
    public void countSystemsIsWorking() throws SQLException {
        Integer i = planetDao.countSystems();
        assertTrue(i > 0);
    }

    @Test
    public void countPlanetsIsWorking() throws SQLException {
        Integer i = planetDao.countPlanets();
        assertTrue(i > 0);
    }

    @Test
    public void addingAndDeletingPlanetsWorks() throws SQLException {
        Integer original = planetDao.countPlanets();
        Planet planet = new Planet("test123", 0, 0, 0, 0, 0);
        planetDao.save(planet);
        Integer added = planetDao.countPlanets();
        planetDao.delete("test123");
        Integer end = planetDao.countPlanets();
        assertTrue(original.equals(end) && original < added);
    }

    @Test
    public void getSystemsIsWorking() throws SQLException {
        ArrayList<String> systems = planetDao.getSystems();
        String firstName = systems.get(0);
        assertTrue(systems.size() >= 2 && firstName.equals("Inner planets"));
    }

    @Test
    public void getSystemNameIsWorking() throws SQLException {
        String name = planetDao.getSystemName(1);
        assertEquals(name, "Inner planets");

    }
    
    @Test
    public void findAlloutsideSystemIsWorking() throws SQLException {
        ArrayList<String> planets = planetDao.findAllPlanetsOutsideSystem(1);
        assertTrue(planets.get(0).equals("Uranus"));
    }
    @Test
    public void getAllPlanetNamesIsWorking() throws SQLException {
        ArrayList<String> planets = planetDao.getAllPlanetNames();
        assertTrue(planets.get(0).equals("Sun") && planets.size() >= 12);
    }
    @Test
    public void getHighestSystemIdIsWorking() throws SQLException {
        int high = planetDao.getHighestSystemId();
        int highest = high;
        for(String i : planetDao.getSystems()){
            int id = planetDao.getSystemId(i);
            if(id > high){
                highest = id;
            }
        }
        assertTrue(highest == high);
    }
    @Test
    public void getSystemIdIsWorking() throws SQLException {
        Integer id = planetDao.getSystemId("Inner planets");
        assertTrue(id == 1);
    }
    @Test
    public void addingAndDeletingSystemsWorks() throws SQLException {
        Integer original = planetDao.countSystems();
        planetDao.addSystem("test");
        Integer added = planetDao.countSystems();
        int id = planetDao.getSystemId("test");
        planetDao.deleteSystem("test", id);
        Integer end = planetDao.countSystems();
        assertTrue(original.equals(end) && original < added);
    }
    
}
