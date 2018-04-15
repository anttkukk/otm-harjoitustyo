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

    public PlanetSystem(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }
    public void Update(int timestep){
        for (Planet planet : planets){
            planet.setPos(planet.newPos(timestep));
            planet.setAcc(planet.newAcc(planets));
            planet.setVel(planet.newVelo(timestep));
            planet.setOldacc(planet.getAcc());
            
        }
    }
}
