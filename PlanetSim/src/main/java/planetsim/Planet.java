/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim;

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

    public Planet(String name, double pos_x, double pos_y, double vel_x, double vel_y, double mass) {
        this.pos = new Vector(pos_x, pos_y);
        this.vel = new Vector(vel_x, vel_y);
        this.mass = mass;
        this.acc = new Vector(0.0, 0.0);
        this.oldacc = new Vector(0.0, 0.0);
        Random r = new Random();
        this.color = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        this.name = name;
    }

    public Planet(String name, double pos_x, double pos_y, double vel_x, double vel_y, double mass, Color color) {
        this.pos = new Vector(pos_x, pos_y);
        this.vel = new Vector(vel_x, vel_y);
        this.mass = mass;
        this.acc = new Vector(0.0, 0.0);
        this.oldacc = new Vector(0.0, 0.0);
        this.color = color;
        this.name = name;
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
    
    public Vector newPos(int t){
        
        //Vector newpos = this.pos.add(this.vel.multiply(1.0*t).add(this.oldacc.multiply(1.0/2.0*Math.pow(t, 2))));
        Vector newpos = this.pos.add(this.vel.multiply(1.0*t));

        return newpos;
    }
    public Vector newAcc(ArrayList<Planet> planets){
        Vector newacc = new Vector(0.0, 0.0);
        for(Planet planet : planets){
            if(!planet.pos.equals(this.pos)){
                double dist = planet.pos.substract(this.pos).length();
                Vector vecr = planet.pos.substract(this.pos).divide(dist);
                newacc = newacc.add(vecr.divide(Math.pow(dist, 2)).multiply(planet.getMass()*6.674E-11));
            }
        }
        return newacc;
    }
    
    public Vector newVelo(int t){
        //Vector newvelo = this.vel.add(this.acc.add(this.oldacc).multiply(t*1.0/2.0));
        Vector newvelo = this.vel.add(this.acc.multiply(t));

        return newvelo;
    }

    @Override
    public boolean equals(Object obj) {
        Planet planet = (Planet)obj;
        if(this.pos.equals(planet.pos)){
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
    
    
    
    
    
    
    

    
    
    

}