package main.tls_maps.map;

import android.util.Log;

import java.util.ArrayList;

public class AStar {


    private final WayPoint Start, Goal;
    protected WayPoint[][] Routen = new WayPoint[][]{};

    public AStar(WayPoint start, WayPoint goal) {
        this.Start = start;
        this.Goal = goal;
        CalculateRoute(new WayPoint[]{start}, start, goal);
    }

    private void CalculateRoute(WayPoint[] path, WayPoint Location, WayPoint Goal) {
        WayPoint[] backUpPath = path;
        for (WayPoint neighbor : Location.getNeighbourPoints()) {
            path = backUpPath;
            Log.d("", "Cuurent Path:");
            for(WayPoint wp : path) {
                Log.d("Path", wp.getName());
            }
            Log.d("", "\t\t\t\t\t");
            if(!isValid(neighbor, path))
                continue;

            path = arrayAdd(neighbor, path);
            Routen = arrayAdd(path, Routen);
            CalculateRoute(path, neighbor, Goal);
        }
    }

    private WayPoint[] arrayAdd (WayPoint addTo, WayPoint[] array) {
        WayPoint[] newArray = new WayPoint[array.length+1];
        for(int i = 0; i < array.length+1; ++i){
            if(i < array.length)
                newArray[i] = array[i];
            else
                newArray[i] = addTo;
        }
        return newArray;
    }

    private WayPoint[][] arrayAdd (WayPoint[] addTo, WayPoint[][] array) {
        WayPoint[][] newArray = new WayPoint[array.length+1][];
        for(int i = 0; i < array.length+1; ++i){
            if(i < array.length)
                newArray[i] = array[i];
            else
                newArray[i] = addTo;
        }
        return newArray;
    }

    private boolean isValid(WayPoint neighbor, WayPoint[] path) {
        if(pathContains(neighbor, path) || path.length > 25)
            return false;

        return true;
    }

    private boolean pathContains(WayPoint neighbor, WayPoint[] path) {
        for(WayPoint wp : path){
            if(wp.getName().equals(neighbor.getName()))
                return true;
        }
        return false;
    }

    private boolean isGoal(WayPoint neighbor, WayPoint goal) {
        return neighbor == goal;
    }

    private boolean visited(WayPoint neighbor, ArrayList<WayPoint> path) {
        return path.contains(neighbor);
    }
    public WayPoint[] getRoute() {
        try {
            return getShortest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WayPoint[] getShortest() throws Exception {
        int shortest = 0;

        Log.d("", "all: " + Routen.length);

        if(Routen == null)
            throw new Exception("Keine Routen gefunden!");


        for(int i = 0; i < Routen.length; i++){
            if(Routen[i].length < Routen[shortest].length && isValid(i))
                shortest = i;
        }
        Log.d("TAG", "getShortest: " + Routen[shortest].length);
        return Routen[shortest];
    }

    private boolean isValid(int i) {
        boolean containsStart = false, containsGoal = false;
        for(int x = 0; x < Routen[i].length; ++x) {
            if(Routen[i][x].getName().equals(Goal.getName()))
                containsGoal = true;
            if(Routen[i][x].getName().equals(Start.getName()))
                containsStart = true;
        }
        if(containsGoal && containsStart)
            return true;
        return false;
    }
}
