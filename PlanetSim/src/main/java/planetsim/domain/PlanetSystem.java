/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.domain;

import java.util.ArrayList;

/**
 *
 * @author anttkukk
 */
public class PlanetSystem {

    private ArrayList<Planet> planets;

    /**
     * Constructor for PlanetSystem
     *
     * @param planets List of Objects in the system
     */
    public PlanetSystem(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    /**
     * Updates the system. Goes through the planets ArrayList calculating new
     * positions, accelerations, velocities and setting them as current values.
     * Lastly updates the old acceleration value to current acceleration so it
     * is used as old acceleration in the next iteration.
     *
     * @param timestep Timestep used to calculate new values for planets
     * properties.
     */
    public void update(int timestep) {
        for (Planet planet : planets) {
            planet.setPos(planet.newPos(timestep));
            planet.setAcc(planet.newAcc(planets));
            planet.setVel(planet.newVelo(timestep));
            planet.setOldacc(planet.getAcc());

        }
    }

    /**
     * Returns the furthest planet from the middle of the coordinates
     *
     * @return Returns the planet which is furthest away
     */
    public Planet getFurthest() {
        if (planets.isEmpty()) {
            return null;
        }
        double far = 0;
        int i = 0;
        int farIndex = 0;
        for (Planet p : planets) {
            if (p.getPos().length() > far) {
                far = p.getPos().length();
                farIndex = i;
            }
            i++;
        }
        return planets.get(farIndex);
    }
}
