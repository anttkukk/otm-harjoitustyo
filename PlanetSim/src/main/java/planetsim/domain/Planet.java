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
        System.out.println(name + " " + Color.SKYBLUE);

    }

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

    public Vector newPos(int t) {

        //Vector newpos = this.pos.add(this.vel.multiply(1.0*t).add(this.oldacc.multiply(1.0/2.0*Math.pow(t, 2))));
        Vector newpos = this.pos.add(this.vel.multiply(1.0 * t));

        return newpos;
    }

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

    public Vector newVelo(int t) {
        //Vector newvelo = this.vel.add(this.acc.add(this.oldacc).multiply(t*1.0/2.0));
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

    public double distance(Planet planet) {
        return Math.sqrt(Math.pow(this.pos.getX() - planet.getPos().getX(), 2) + Math.pow(this.pos.getY() - planet.getPos().getY(), 2));
    }

    @Override
    public String toString() {
        return this.name;
    }
    

}
