package main.tls_maps.ui.home;

import java.util.ArrayList;

public class Map {
    public ArrayList<String> WayPointsOnMap = new ArrayList<String>(10);
    public ArrayList<Wall> WallsOnMap = new ArrayList<Wall>(10);
    public final int Level;

    public Map(int level) {
        this.Level = level;
    }

    public void AddWall(Wall walltoadd) {
        WallsOnMap.add(walltoadd);
    }
}
