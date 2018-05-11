/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import planetsim.domain.Planet;
import planetsim.domain.PlanetSystem;
import planetsim.domain.Vector;

/**
 *
 * @author anttkukk
 */
public class PlanetSystemTest {
    private PlanetSystem system;
    private ArrayList<Planet> planets;
    public PlanetSystemTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Planet one = new Planet("one", 0, 0, 100.0, 100.0, 100);
        Planet two = new Planet("two", 100.0, 100.0, 0, 0, 100.0);
        planets = new ArrayList<>();
        planets.add(one);
        planets.add(two);
        system = new PlanetSystem(planets);
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
    public void updateIsWorking() {
        Vector oldPos = system.getPlanets().get(0).getPos();
        system.update(100);
        Vector newPos = system.getPlanets().get(0).getPos();
        assertTrue(newPos != oldPos);
        
    }
    
    @Test
    public void getFurthestIsWorking() {
        assertEquals("two", system.getFurthest().getName());
    }
    @Test
    public void setPlanetsIsWorking() {
        ArrayList<Planet> newPlanets = new ArrayList<>();
        system.setPlanets(newPlanets);
        assertTrue(system.getPlanets().isEmpty());
    }
    @Test
    public void getFurthestReturnsNullIfThereAreNoPlanets() {
        ArrayList<Planet> empty = new ArrayList<>();
        system.setPlanets(empty);
        assertTrue(system.getFurthest() == null);
    }
}
