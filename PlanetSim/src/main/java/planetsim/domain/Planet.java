/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.domain;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author anttkukk
 */
public class Planet {

    private Vector pos;
    private Vector vel;
    private double mass;
    private Vector acc;
    private Vector oldacc;
    private Color color;
    private String name;
    private int drawSize;

    /**
     * Constructor for a Planet. Colour is randomized and draw size of the planet is always 2.
     * @param name Name of the Planet
     * @param posX X-coordinate of the planet's position
     * @param posY Y-coordinate of the planet's position
     * @param velX X-coordinate of the planet's velocity
     * @param velY Y-coordinate of the planet's velocity
     * @param mass Mass of the object
     */
    public Planet(String name, double posX, double posY, double velX, double velY, double mass) {
        this.pos = new Vector(posX, posY);
        this.vel = new Vector(velX, velY);
        this.mass = mass;
        this.acc = new Vector(0.0, 0.0);
        this.oldacc = new Vector(0.0, 0.0);
        Random r = new Random();
        this.color = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        this.name = name;
        this.drawSize = 2;
    }
    /**
     * Constructor for a planet with user set colour.
     * @param name Name of the object
     * @param posX X-coordinate of the planet's position
     * @param posY Y-coordinate of the planet's position
     * @param velX X-coordinate of the planet's velocity
     * @param velY Y-coordinate of the planet's velocity
     * @param mass Mass of the object
     * @param color Draw colour of the object
     */
    public Planet(String name, double posX, double posY, double velX, double velY, double mass, Color color) {
        this.pos = new Vector(posX, posY);
        this.vel = new Vector(velX, velY);
        this.mass = mass;
        this.acc = new Vector(0.0, 0.0);
        this.oldacc = new Vector(0.0, 0.0);
        this.color = color;
        this.name = name;
        this.drawSize = 3;
    }

    /**
     * Constructor for Planet with user set everything
     * @param name Name of the planet
     * @param posX X-coordinate of the planet's position
     * @param posY Y-coordinate of the planet's position
     * @param velX X-coordinate of the planet's velocity
     * @param velY Y-coordinate of the planet's velocity
     * @param mass Mass of the Planet
     * @param color Draw colour of the planet
     * @param drawSize Draw size of the planet. Should carefully think if user wants to draw bigger than 5
     */
    public Planet(String name, double posX, double posY, double velX, double velY, double mass, Color color, int drawSize) {
        this.pos = new Vector(posX, posY);
        this.vel = new Vector(velX, velY);
        this.mass = mass;
        this.acc = new Vector(0.0, 0.0);
        this.oldacc = new Vector(0.0, 0.0);
        Random r = new Random();
        this.color = color;
        this.name = name;
        this.drawSize = drawSize;
    }
    /**
     * Constructor for Planet with randomised colour but user set everything else
     * @param name Name of the planet
     * @param posX X-coordinate of the planet's position
     * @param posY Y-coordinate of the planet's position
     * @param velX X-coordinate of the planet's velocity
     * @param velY Y-coordinate of the planet's velocity
     * @param mass Mass of the Planet
     * @param drawSize Draw size of the planet. Should carefully think if user wants to draw bigger than 5
     */
    public Planet(String name, double posX, double posY, double velX, double velY, double mass, int drawSize) {
        this.pos = new Vector(posX, posY);
        this.vel = new Vector(velX, velY);
        this.mass = mass;
        this.acc = new Vector(0.0, 0.0);
        this.oldacc = new Vector(0.0, 0.0);
        Random r = new Random();
        this.color = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        this.name = name;
        this.drawSize = drawSize;
    }

    public Vector getPos() {
        return pos;
    }

    public Vector getVel() {
        return vel;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }

    public void setVel(Vector vel) {
        this.vel = vel;
    }

    /**
     * Calculates a new position vector to planet with a timestep given as a parameter.
     * If the timestep is 60, the method calculates a new position using planets current velocity to where it would be in 60 seconds. 
     * @param t used to calculate the new position
     * @return Returns a vector which is the new position of the planet.
     *
     */
    public Vector newPos(int t) {

        Vector newpos = this.pos.add(this.vel.multiply(1.0 * t));

        return newpos;
    }

    /**
     * Calculates new acceleration to planet using the sum force from all other planets which it gets in the parameter in an ArrayList
     * Uses Newton's gravitational force to calculate the acceleration. 
     * Ignores itself in the ArrayList so acceleration is accurate.
     * @param planets ArrayList of all the bodies in the star system
     * @return A vector which is the new acceleration of the planet.
     */
    public Vector newAcc(ArrayList<Planet> planets) {
        Vector newacc = new Vector(0.0, 0.0);
        for (Planet planet : planets) {
            if (!planet.pos.equals(this.pos)) {
                double dist = planet.pos.substract(this.pos).length();
                Vector vecr = planet.pos.substract(this.pos).divide(dist);
                newacc = newacc.add(vecr.divide(Math.pow(dist, 2)).multiply(planet.getMass() * 6.674E-11));
            }
        }
        return newacc;
    }
    
    /**
     * Calculates new velocity for the planet using timestep from the parameter. With a timestep of 60, it calculates the velocity to 60 seconds in the future.
     * 
     * @param t timestep which is used in the calculation
     * @return A vector which is the new velocity of the object
     */

    public Vector newVelo(int t) {
        Vector newvelo = this.vel.add(this.acc.multiply(t));

        return newvelo;
    }
    
    @Override
    public boolean equals(Object obj) {
        Planet planet = (Planet) obj;
        if (this.pos.equals(planet.pos)) {
            return true;
        }
        return false;
    }

    public Vector getAcc() {
        return acc;
    }

    public Vector getOldacc() {
        return oldacc;
    }

    public void setAcc(Vector acc) {
        this.acc = acc;
    }

    public void setOldacc(Vector oldacc) {
        this.oldacc = oldacc;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawSize() {
        return drawSize;
    }

    public void setDrawSize(int drawSize) {
        this.drawSize = drawSize;
    }

    /**
     * Calculates the distance to a planet given as a parameter
     * @param planet The planet it calculates the distance to.
     * @return The distance to the parameter planet as a double
     */
    public double distance(Planet planet) {
        return Math.sqrt(Math.pow(this.pos.getX() - planet.getPos().getX(), 2) + Math.pow(this.pos.getY() - planet.getPos().getY(), 2));
    }

    @Override
    public String toString() {
        return this.name;
    }

}
