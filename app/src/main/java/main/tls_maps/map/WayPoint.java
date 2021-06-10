package main.tls_maps.map;

import java.util.ArrayList;

public class WayPoint {
    public final String Name;
    public final Vector2 position;
    public final int Level;
    private ArrayList<WayPoint> NeighbourPoints = new ArrayList<WayPoint>();


    public WayPoint(String Name, Vector2 Position, int Level) {
        this.Name = Name;
        this.position = Position;
        this.Level = Level;
    }

    public void AddNeighbourPoint(WayPoint wayPoint) {
        NeighbourPoints.add(wayPoint);
    }

    public ArrayList<WayPoint> getNeighbourPoints() { return NeighbourPoints; }
}
