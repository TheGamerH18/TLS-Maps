package main.tls_maps.ui.home;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AStar {

    public ArrayList<WayPoint> waypoints = new ArrayList<WayPoint>();

    public AStar() {

    }

    public void AddWaypoint(WayPoint wayPoint) {
        waypoints.add(wayPoint);
    }

    public ArrayList<WayPoint> CalculateRoute(WayPoint Location,WayPoint Goal) {
        ArrayList<WayPoint> route = new ArrayList<WayPoint>();


        return route;
    }
}
