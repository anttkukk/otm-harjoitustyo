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

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    public void update(int timestep) {
        for (Planet planet : planets) {
            planet.setPos(planet.newPos(timestep));
            planet.setAcc(planet.newAcc(planets));
            planet.setVel(planet.newVelo(timestep));
            planet.setOldacc(planet.getAcc());

        }
    }
    
    public Planet getFurthest(){
        double far = 0;
        int i = 0;
        int farIndex = 0;
        for (Planet p : planets){
            if(p.getPos().length() > far){
                far = p.getPos().length();
                farIndex = i;
            }
            i++;
        }
        return planets.get(farIndex);
    }
}
