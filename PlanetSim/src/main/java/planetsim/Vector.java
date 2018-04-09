/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim;

/**
 *
 * @author anttkukk
 */
public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public Vector add(Vector v){
        return new Vector(this.x + v.getX(), this.y + v.getY());
        
    }
    public Vector substract(Vector v){
        return new Vector(this.x - v.getX(), this.y - v.getY());
    }
    public double dot(Vector v){
        return this.x * v.getX() + this.y * v.getY();
    }
    public Vector multiply(double i){
        return new Vector(this.x * i, this.y * i);
    }
    public Vector divide(double i){
        return new Vector(this.x / i, this.y / i);
    }
    public double length(){
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    @Override
    public boolean equals(Object obj) {
        Vector v = (Vector) obj;
        if (this.x == v.getX() && this.y == v.getY()){
            return true;
        }
        return false;

    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }

    
    
    

}
