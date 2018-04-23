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
        db = new Database("jdbc:sqlite:database.db");
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
}
