package main.tls_maps.ui.home;

import java.util.ArrayList;

public class WayPoint {
    public final String Name;
    public final Vector2 position;
    public final int Level;
    public ArrayList<String> NeighbourPoints = new ArrayList<String>();


    public WayPoint(String name, Vector2 position, int level) {
        this.Name = name;
        this.position = position;
        this.Level = level;
    }

    public void AddNeighbourPoint(String name) {
        NeighbourPoints.add(name);
    }
}
