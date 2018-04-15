/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import planetsim.domain.Planet;
import planetsim.domain.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anttkukk
 */
public class PlanetTest {

    Planet planet;

    public PlanetTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        planet = new Planet("", 1.0, 1.0, 2.0, 2.0, 10);

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
    public void newPosIsWorkingAsIntended() {
        planet.setPos(planet.newPos(1));

        assertEquals("3.0, 3.0", planet.getPos().toString());
    }

    @Test
    public void newVeloIsWorking() {
        planet.setAcc(new Vector(1.0, 1.0));
        assertEquals("3.0, 3.0", planet.newVelo(1).toString());
    }

    @Test
    public void newAccIsWorking() {
        Planet planet2 = new Planet("", 1.5E10, 1.5E10, 0, 0, 1E30);
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(planet);
        planets.add(planet2);
        planet.setAcc(planet.newAcc(planets));
        String acc = String.format("%.03f", planet.getAcc().getX());
        acc = acc + " " + String.format("%.03f", planet.getAcc().getY());
        assertEquals("0,105 0,105", acc);
    }

    @Test
    public void constructorWithColorAndSizeIsWorking() {
        boolean working = true;
        String name = "poop";
        double x = 1;
        double y = 2;
        double velx = 3;
        double vely = 4;
        double mass = 5;
        Color color = Color.WHITE;
        int size = 6;
        Planet p = new Planet(name, x, y, velx, vely, mass, color, size);
        if (p.getPos().getX() != x && p.getPos().getX() != y) {
            working = false;
        }
        if(p.getDrawSize() != size){
            working = false;
        }
        assertTrue(working);
    }
    @Test
    public void constructorWithoutSizeWithColorIsWorking() {
        boolean working = true;
        String name = "poop";
        double x = 1;
        double y = 2;
        double velx = 3;
        double vely = 4;
        double mass = 5;
        Color color = Color.WHITE;
        Planet p = new Planet(name, x, y, velx, vely, mass, color);
        if (p.getPos().getX() != x && p.getPos().getX() != y) {
            working = false;
        }
        if(p.getMass() != mass){
            working = false;
        }
        assertTrue(working);
    }
    
    @Test
    public void constructorWithoutColorOrSizeIsWorking() {
        boolean working = true;
        String name = "poop";
        double x = 1;
        double y = 2;
        double velx = 3;
        double vely = 4;
        double mass = 5;

        Planet p = new Planet(name, x, y, velx, vely, mass);
        if (p.getPos().getX() != x && p.getPos().getX() != y) {
            working = false;
        }
        if(p.getMass() != mass){
            working = false;
        }
        assertTrue(working);
    }
    
    @Test
    public void distanceIsWorking() {
        Planet planet2 = new Planet("",0.0,1.0,0.0,0.0,0.0);
        assertTrue(planet.distance(planet2) == 1.0);
    }
    
}
