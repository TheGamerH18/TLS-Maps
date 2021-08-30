package main.tls_maps.map;

public class Vector2 {

    public double x, y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Overrides a Vector2.
     * @param x the X Value.
     * @param y the Y Value.
     */
    public void set(double x,double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This just Clones a Vector2.
     * @return Returns a new Vector2.
     */
    public Vector2 clone() {
        return new Vector2(this.x,this.y);
    }

    /**
     * Adds another Vector2 to this one.
     * @param b the other Vector2.
     * @return Returns a new Vector2.
     */
    public Vector2 add(Vector2 b) {
        return new Vector2(this.x+b.x,this.y+b.y);
    }

    /**
     * Subtracts another Vector2 to this one.
     * @param b the other Vector2.
     * @return Returns a new Vector2.
     */
    public Vector2 sub(Vector2 b) {
        return new Vector2(this.x-b.x,this.y-b.y);
    }

    /**
     * Multiplies another Vector2 to this one.
     * @param b the other Vector2.
     * @return Returns a new Vector2.
     */
    public Vector2 mul(Vector2 b) {
        return new Vector2(this.x*b.x,this.y*b.y);
    }

    /**
     * Multiplies the Vector2 with a double.
     * @param a the number to multiply.
     * @return Returns a new Vector2
     */
    public Vector2 mul(double a) {
        return new Vector2(this.x*a,this.y*a);
    }

    /**
     * Divides the Vector2 with another one.
     * @param b the other Vector2.
     * @return Returns a new Vector2
     */
    public Vector2 div(Vector2 b) {
        return new Vector2(this.x/b.x,this.y/b.y);
    }

    /**
     * Divides the Vector2 with a double.
     * @param a the double to divide by.
     * @return Returns a new Vector2.
     */
    public Vector2 div(double a) {
        double inverse = 1/a;
        return new Vector2(this.x/inverse,this.y/inverse);
    }

    /**
     * Calculates the distance of the Vector2 to the Position 0,0.
     * @return double of the distance to the Center.
     */
    public double magnitude() {
        return Math.sqrt(this.x*this.x+this.y*this.y);
    }

    /**
     * Turns the Vector2 into a String, possible to turn back by fromString.
     * @return A String of the Vector2.
     */
    public String ToString() {
        return this.x+","+this.y;
    }

    /**
     * Makes a Vector2 Normalized.
     * @return Returns a normalized Vector2.
     */
    public Vector2 unit() {
        double mag = this.magnitude();
        //double mag = Vector3.Dot(this,new Vector3());
        if (mag>0) {
            double invmag = 1 / mag;
            return this.clone().mul(invmag);
        }
        return this;
    }

    /**
     * Calculates the Angle the Vector2 is facing at from 0,0.
     * @return the Angle it is.
     */
    public double angle() {
        return Math.atan2(this.x, this.y);
    }

    /**
     * This interpolates a Vector2 to another Vector2.
     * @param vector the other Vector2.
     * @param t the Alpha value on how far to interpolate (1 is the full distance).
     * @return the interpolated Vector2 between those two.
     */
    public Vector2 lerp(Vector2 vector,double t) {
        return this.add(vector.sub(this).mul(t));
    }

    /**
     * Transforms a Vector2 around the Center.
     * @param angle the Angle to transform by.
     * @return A new Vector2 thats Transformed.
     */
    public Vector2 Transform(double angle) {
        double radian = Math.toRadians(angle);
        double x = this.x*Math.cos(radian) - this.y*Math.sin(radian);
        double y = this.x*Math.sin(radian) + this.y*Math.cos(radian);

        return new Vector2(x,y);
    }

    /**
     * Turns a String into a Vector2 ("x,y"), works with ToString function.
     * @param string the String, needs to be like "10,50".
     * @return Returns a new Vector2 by String.
     */
    public static Vector2 fromString(String string) {
        String[] ree = string.split(",");
        String x = ree[0];
        String y = ree[1];
        return new Vector2(Double.parseDouble(x),Double.parseDouble(y));
    }

    /**
     * Limits a Value between a Max and a Min value.
     * @param val the Value to Limit.
     * @param max the Max Value.
     * @param min the Min Value.
     * @return the Value or one of those Limits.
     */
    private double clamp(double val, double max, double min) {
        return Math.min(Math.max(val,min),max);
    }

    /**
     * Clamps a Vector2 using the.
     * @param max the Max Value.
     * @param min the Min Value.
     * @return Returns a new Vector2 thats Clamped.
     */
    public Vector2 clamp(double max,double min) {
        return new Vector2(clamp(this.x,max,min),clamp(this.y,max,min));
    }

}