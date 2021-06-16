package main.tls_maps.map;

import java.util.ArrayList;

public class WayPoint {
    private final String Name;
    private final Vector2 position;
    private final int Level;
    private ArrayList<WayPoint> NeighbourPoints = new ArrayList<WayPoint>();


    public WayPoint(String Name, Vector2 Position, int Level) {
        this.Name = Name;
        this.position = Position;
        this.Level = Level;
    }

    public Vector2 getPosition() {return this.position;}

    public void AddNeighbourPoint(WayPoint name) {
        NeighbourPoints.add(name);
    }
}
