package main.tls_maps.map;

import android.util.Log;

import java.util.ArrayList;

public class AStar {



    protected static ArrayList< ArrayList<WayPoint> > Routen = new ArrayList<>();

    public AStar(WayPoint start, WayPoint goal) {
        CalculateRoute(new ArrayList<>(), start, goal);
    }

    private void CalculateRoute(ArrayList<WayPoint> path, WayPoint Location, WayPoint Goal) {
        ArrayList<WayPoint> backUpPath = path;
        for (WayPoint neighbor : Location.getNeighbourPoints()) {
            path = backUpPath;
            Log.d("TAG", "CalculateRoute: " + neighbor.getName());
            path.add(neighbor);
            if (Location != Goal)
                CalculateRoute(path, neighbor, Goal);
            else
                AStar.Routen.add(path);
        }
    }

    private boolean visited(WayPoint neighbor, ArrayList<WayPoint> path) {
        return path.contains(neighbor);
    }
    public ArrayList<WayPoint> getRoute() {
        try {
            return getShortest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<WayPoint> getShortest() throws Exception {
        int shortest = 0;


        Log.d("TAG", "all " + Routen.size());


        for(int i = 0; i < Routen.size(); ++i){
            for(WayPoint wp : Routen.get(i)){
                Log.d("Route " + i , wp.getName());
            }
        }


        if(Routen.isEmpty())
            throw new Exception("Routen sind nicht verfügbar!");


        for(int i = 0; i <= Routen.size(); i++){
            shortest = (Routen.get(i).size() < Routen.get(shortest).size())? i : shortest;
        }

        return Routen.get(shortest);
    }
}
