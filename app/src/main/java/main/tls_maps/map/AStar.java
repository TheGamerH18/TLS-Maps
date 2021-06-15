package main.tls_maps.map;

import android.util.Log;

import java.util.ArrayList;

public class AStar {


    private final WayPoint Goal;
    private final WayPoint Start;
    private ArrayList<WayPoint> Route;
    private WayPoint Position;

    public AStar(WayPoint start, WayPoint goal) {
        this.Goal = goal;
        this.Start = start;
        this.Position = start;
        ArrayList<WayPoint> path = new ArrayList<>();
        path.add(start);
        CalculateRoute(path, start);
    }

    public void CalculateRoute(ArrayList<WayPoint> path, WayPoint Location) {
        for(WayPoint wp : Location.getNeighbourPoints()) {
            if(wp.getName().equals(Goal.getName()) || wp.equals(Goal)) {
                Log.d("TAG", "CalculateRoute: Goal found!");
                path.add(wp);
                this.Route = path;
            }
            if(wpIsRelavant(wp)){
                if(path.contains(wp))
                    continue;
                Log.d("TAG", "CalculateRoute: Relavant WayPoint found!" + wp.getName());
                path.add(wp);
                this.Position = wp;
                CalculateRoute(path, wp);
            } else {

            }
        }
    }

    private boolean wpIsRelavant(WayPoint wp) {
        Log.d("TAG", "wpIsRelavant: " + (Goal.getPosition().sub(wp.getPosition()).magnitude()) + " < " + Goal.getPosition().sub(Position.getPosition()).magnitude() + "      WP: " + wp.getName());
        if(Goal.getPosition().sub(wp.getPosition()).magnitude() < Goal.getPosition().sub(Position.getPosition()).magnitude()){
            return true;
        }
        return false;
    }

    public ArrayList<WayPoint> getRoute() {
        if(this.Route == null)
            return new ArrayList<>();
        return this.Route;
    }
}