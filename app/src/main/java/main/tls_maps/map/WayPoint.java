package main.tls_maps.map;

import java.util.ArrayList;

public class WayPoint {
    private final String Name;
    private final Vector2 position;
    private final int Level;
    private boolean hasKnot;
    private ArrayList<WayPoint> NeighbourPoints = new ArrayList<>();
    private WayPoint Knot;

    public WayPoint(String Name, Vector2 Position, int Level) {
        this.Name = Name;
        this.position = Position;
        this.Level = Level;
    }

    public void addNeighbourPoint(WayPoint wayPoint) {
        NeighbourPoints.add(wayPoint);
    }

    public ArrayList<WayPoint> getNeighbourPoints() {
        return this.NeighbourPoints;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public int getLevel() {
        return this.Level;
    }

    public String getName() {
        return this.Name;
    }

    public Vector2 getVector() {
        return this.position;
    }

    public WayPoint getKnot() {
        return this.Knot;
    }

    public boolean hasKnot() {
        return this.hasKnot;
    }

    public void sethasKnot() {
        this.hasKnot = true;
    }

    public void setKnot(WayPoint knot) {
        this.Knot = knot;
    }
}
