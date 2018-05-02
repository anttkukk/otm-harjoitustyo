/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetsim.domain;

/**
 *
 * @author anttkukk
 */
public class Vector {

    private double x;
    private double y;
    
    /**
     * Constructor for a vector
     * @param x X-component of the vector
     * @param y Y-component of the vector
     */

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

    /**
     * Adds the components of this vector and a vector given as a parameter together and returns the sum vector as a new vector.
     * @param v Vector which is added to this vector
     * @return Returns a new vector which is the sum of this vector and the vector given as a parameter.
     */
    public Vector add(Vector v) {
        return new Vector(this.x + v.getX(), this.y + v.getY());

    }
    /**
     * Substracts the components of the vector given as a parameter and this vector. Returns the substracted vector as a new vector.
     * @param v Vector to be substracted from this vector.
     * @return The substraction of the two vectors as a new vector
     */

    public Vector substract(Vector v) {
        return new Vector(this.x - v.getX(), this.y - v.getY());
    }

    /**
     * Calculates the dot product of this vector and the vector given as a parameter
     * @param v Vector which is used to calculate the dot product with.
     * @return Returns the the dot product of the vectors as a new vectors
     */
    public double dot(Vector v) {
        return this.x * v.getX() + this.y * v.getY();
    }

    /**
     * Multiplies the components of the vector with a double value given as a parameter.
     * @param i Double value used to multiply the vector's components
     * @return Returns the multiplied vector as a new vector
     */
    public Vector multiply(double i) {
        return new Vector(this.x * i, this.y * i);
    }

    /**
     * Dividers the components of the vector with a double value given as a parameter.
     * @param i double value used to multiply the vector's components
     * @return Returns the multiplied vector as a new vector
     */
    public Vector divide(double i) {
        return new Vector(this.x / i, this.y / i);
    }

    /**
     * Calculates the length of the vector
     * @return Returns the length of the vector as a double value
     */
    public double length() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    @Override
    public boolean equals(Object obj) {
        Vector v = (Vector) obj;
        if (this.x == v.getX() && this.y == v.getY()) {
            return true;
        }
        return false;

    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }

}
