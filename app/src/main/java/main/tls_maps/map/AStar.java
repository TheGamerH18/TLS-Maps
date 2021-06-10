package main.tls_maps.map;

import java.util.ArrayList;

public class AStar {

    public ArrayList<WayPoint> waypoints = new ArrayList<WayPoint>();

    public AStar() {
        // TODO
    }

    public void AddWaypoint(WayPoint wayPoint) {
        waypoints.add(wayPoint);
    }



    public ArrayList<WayPoint> CalculateRoute(WayPoint Location,WayPoint Goal) {
        ArrayList<WayPoint> route = new ArrayList<WayPoint>();
        route.add(Location);

        ArrayList<WayPoint> points = new ArrayList<>();

        ArrayList<WayPoint> currentNodes = new ArrayList<WayPoint>();
        boolean reachedgoal = false;

        while (reachedgoal!=true) {
            ArrayList<WayPoint> nextNodes = new ArrayList<WayPoint>();

            for (int count=0;count<currentNodes.size();count++){
                WayPoint tempNode = currentNodes.get(count);
                ArrayList<WayPoint> neighbourNodes = tempNode.getNeighbourPoints();
                nextNodes.addAll(neighbourNodes);
                if (tempNode == Goal) {
                    reachedgoal = true;
                    break;
                }
            }



        }


        return route;
    }
}
