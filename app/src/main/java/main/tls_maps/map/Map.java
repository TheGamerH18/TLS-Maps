package main.tls_maps.map;

import java.util.ArrayList;

public class Map {

    public ArrayList<WayPoint> WayPointsOnMap = new ArrayList<WayPoint>();
    public ArrayList<Wall> WallsOnMap = new ArrayList<Wall>();
    public final int Level;

    public Map(int level) {
        this.Level = level;
    }

    public void addWall(Wall wallToAdd) {
        WallsOnMap.add(wallToAdd);
    }

    public void addWayPoint(WayPoint wayPoint) {
        WayPointsOnMap.add(wayPoint);
    }

    public void setRoute(ArrayList<WayPoint> route) {
        this.WayPointsOnMap = route;
    }
}
