package main.tls_maps.ui.home;

import java.util.ArrayList;

public class Map {

    public ArrayList<String> WayPointsOnMap = new ArrayList<String>();
    public ArrayList<Wall> WallsOnMap = new ArrayList<Wall>();
    public final int Level;

    public Map(int level) {
        this.Level = level;
    }

    public void addWall(Wall wallToAdd) {
        WallsOnMap.add(wallToAdd);
    }
}
