package main.tls_maps.ui.home;

import java.util.ArrayList;

public class Map {
    public ArrayList<String> WayPointsOnMap = new ArrayList<String>(10);
    public ArrayList<Wall> WallsOnMap = new ArrayList<Wall>(10);
    public final int Level;
    public final Vector2 Position;
    public final String Name;

    public Map(int level, Vector2 position, String name) {
        this.Level = level;
        this.Position = position;
        this.Name = name;
    }

    public void AddWall(Wall walltoadd) {
        WallsOnMap.add(walltoadd);
    }
}
