package main.tls_maps.ui.home;

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

    public void set(double x,double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 clone() {
        return new Vector2(this.x,this.y);
    }

    public Vector2 add(Vector2 b) {
        return new Vector2(this.x+b.x,this.y+b.y);
    }

    public Vector2 sub(Vector2 b) {
        return new Vector2(this.x-b.x,this.y-b.y);
    }

    public Vector2 mul(Vector2 b) {
        return new Vector2(this.x*b.x,this.y*b.y);
    }

    public Vector2 mul(double a) {
        return new Vector2(this.x*a,this.y*a);
    }

    public Vector2 div(Vector2 b) {
        return new Vector2(this.x/b.x,this.y/b.y);
    }

    public Vector2 div(double a) {
        return new Vector2(this.x/a,this.y/a);
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2));
    }

    public String ToString() {
        return this.x+","+this.y;
    }

    public Vector2 unit() {
        if (this.x==0 || this.y == 0) {
            return new Vector2();
        }
        return this.clone().div(this.magnitude());
    }

    public double angle() {
        return Math.atan2(this.x, this.y);
    }

    public Vector2 lerp(Vector2 vector,double t) {
        return this.add(vector.sub(this).mul(t));
    }

    public Vector2 Transform(double angle) {
        double radian = Math.toRadians(angle);
        double x = this.x*Math.cos(radian) - this.y*Math.sin(radian);
        double y = this.x*Math.sin(radian) + this.y*Math.cos(radian);

        return new Vector2(x,y);
    }

    public static Vector2 fromString(String string) {
        String[] ree = string.split(",");
        String x = ree[0];
        String y = ree[1];
        return new Vector2(Double.parseDouble(x),Double.parseDouble(y));
    }

    private double clamp(double val, double max, double min) {
        return Math.min(Math.max(val,min),max);
    }

    public Vector2 clamp(double max,double min) {
        return new Vector2(clamp(this.x,max,min),clamp(this.y,max,min));
    }

}