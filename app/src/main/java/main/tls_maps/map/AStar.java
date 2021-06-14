package main.tls_maps.map;

import android.util.Log;

import java.util.ArrayList;

public class AStar {



    ArrayList< ArrayList<WayPoint> > Routen = new ArrayList<>();

    public AStar(WayPoint start, WayPoint goal) {
        this.CalculateRoute(new ArrayList<>(), start, goal);
    }

    public ArrayList<WayPoint> getRoute() {
        try {
            return getShortest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void CalculateRoute(ArrayList<WayPoint> path, WayPoint Location, WayPoint Goal) {
        for (WayPoint neighbor : Location.getNeighbourPoints()) {
            if (visited(neighbor, path))
                continue;
            Log.d("TAG", "CalculateRoute: " + neighbor.getName());
            path.add(neighbor);
            if (Location == Goal)
                Routen.add(path);
            else
                CalculateRoute(path, neighbor, Goal);
        }
    }

    private boolean visited(WayPoint neighbor, ArrayList<WayPoint> path) {
        return path.contains(neighbor);
    }

    private ArrayList<WayPoint> getShortest() throws Exception {
        int shortest = Integer.MAX_VALUE-1;
        if(Routen.isEmpty())
            throw new Exception("Routen sind nicht verfügbar!");
        for(int i = 0; i < Routen.size(); i++){
            shortest = Routen.get(i).size() < shortest ? i : shortest;
        }
        return Routen.get(shortest);
    }
}
