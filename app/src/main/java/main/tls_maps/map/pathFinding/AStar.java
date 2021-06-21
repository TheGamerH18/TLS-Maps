package main.tls_maps.map.pathFinding;

import java.util.ArrayList;

import main.tls_maps.map.WayPoint;

public class AStar {

    private ArrayList<WayPoint> Route = null;
    private WayPoint from;

    public AStar(WayPoint from, WayPoint to){
        this.from = from;
        System.out.println("Von: " + from.getName() + " --> " + to.getName());
        ArrayList<WayPoint> path = new ArrayList<>();
        path.add(from);
        this.start(from, to, path);
    }

    private void start(WayPoint from, WayPoint to, ArrayList<WayPoint> path) {
        // Loop through all Neighbors
        for(WayPoint wp : from.getNeighbourPoints()) {
            ArrayList<WayPoint> newPath = new ArrayList<>();
            newPath.addAll(path);

            if(newPath.contains(wp))
                continue;

            newPath.add(wp);

            if(wp.equals(to)){
                if(this.Route == null)
                    this.Route = newPath;
                else if(this.Route.size() > newPath.size())
                    this.Route = newPath;
            } else {
                    if(WayPoints.getWayPoints().size() > 40) {
                        if (wpIsRelevant(wp, to, from))
                            start(wp, to, newPath);
                    } else
                        start(wp, to, newPath);
            }
        }
    }

    private boolean wpIsRelevant(WayPoint wp, WayPoint to, WayPoint Position) {
        // Check if is distance is getting Lower
        if(to.getPosition().sub(wp.getPosition()).magnitude()+(to.getPosition().sub(wp.getPosition()).magnitude()/100*10) < to.getPosition().sub(Position.getPosition()).magnitude()){
            // if it does return true
            return true;
        }
        return false;
    }

    public ArrayList<WayPoint> getRoute() {
        return this.Route;
    }
}
