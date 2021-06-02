package main.tls_maps.ui.home;

import java.util.ArrayList;

public class Map {
    public ArrayList<String> WayPointsOnMap = new ArrayList<String>(10);
    public ArrayList<Wall> WallsOnMap = new ArrayList<Wall>(10);
    public final int Level;
    public final Vector2 Position;

    public Map(int level, Vector2 position) {
        this.Level = level;
        this.Position = position;
    }

    public void AddWall(Wall walltoadd) {
        WallsOnMap.add(walltoadd);
    }
}
