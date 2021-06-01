package main.tls_maps.ui.home;

public class Vector2 {
    public double x = 0,y = 0;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 set(double x,double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2(String string) {
        Vector2 r = new Vector2(string);
        this.set(r.x, r.y);
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

    public String tostring() {
        return this.x+","+this.y;
    }

    public Vector2 unit() {
        return this.clone().div(this.magnitude());
    }

    public double angle() {
        return Math.atan2(this.x, this.y);
    }

    public Vector2 lerp(Vector2 vector,double t) {
        return this.add(vector.sub(this).mul(t));
    }

    public Vector2 converttorender() {
        Vector2 E = this.clone();
        E.y *= -1;
        return E;
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
        //System.out.println(x+"|"+y);
        Vector2 r = new Vector2(Double.parseDouble(x),Double.parseDouble(y));
        //this.set(new Double(x),new Double(y));
        //System.out.println(r.tostring());
        return r;
    }

}