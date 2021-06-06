package main.tls_maps.map;

import java.util.ArrayList;

public class WayPoint {
    public final String Name;
    public final Vector2 position;
    public final int Level;
    public ArrayList<String> NeighbourPoints = new ArrayList<String>();


    public WayPoint(String Name, Vector2 Position, int Level) {
        this.Name = Name;
        this.position = Position;
        this.Level = Level;
    }

    public void AddNeighbourPoint(String name) {
        NeighbourPoints.add(name);
    }
}
