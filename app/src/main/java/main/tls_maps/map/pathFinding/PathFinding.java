package main.tls_maps.map.pathFinding;

import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import main.tls_maps.databinding.FragmentHomeBinding;
import main.tls_maps.map.WayPoint;

public class PathFinding {

    /**
     * This Class is for the Path finding, it store the Route and makr the Access easier
     * @param from the Start Location
     * @param target the Targeted Location
     * @param binding binding for the Home Fragment to set Route in the Map
     * @param v the View to make Snackbar if something went wrong
     */
    public PathFinding(String from, String target, FragmentHomeBinding binding, View v){

        ArrayList<WayPoint> wayPoints = WayPoints.getWayPoints();

        if(from == "" || from.equals("")){
            WayPoint targetWP = new getWP().getWayPoint(target, wayPoints);
            Log.d("FindRoom", "getTargets: " + target);
            binding.imageHome.setMarkRoom(targetWP);
            return;
        }
        WayPoint fromWP = new getWP().getWayPoint(from, wayPoints);
        WayPoint targetWP = new getWP().getWayPoint(target, wayPoints);
        if(fromWP == null)
            Snackbar.make(v, "Startpunkt konnte nicht gefunden werden", Snackbar.LENGTH_LONG).show();
        else if(targetWP == null)
            Snackbar.make(v, "Zielpunkt konnte nicht gefunden werden", Snackbar.LENGTH_LONG).show();
        else {
            ArrayList<WayPoint> route = new ArrayList<>();
            Log.d("TAG", "onClick: before");
            if(!(""+ from.charAt(1)).equals(""+target.charAt(1))) {
                Log.d("TAG", "onClick: if");
                // get the difference
                try {
                    int fromInt = Integer.parseInt(""+from.charAt(1));
                    int targetInt = Integer.parseInt(""+target.charAt(1));
                    int difference = fromInt > targetInt ? fromInt - targetInt : targetInt - fromInt;
                    Log.d("TAG", "onClick: " + difference + " = " + from.charAt(1) + " - " + target.charAt(1));

                    // Add Connetction to the Stairs
                    WayPoint nearstStair = new AStar().getStair(fromWP, wayPoints);
                    Log.d("TAG", "onClick: " + nearstStair.getName());

                    route.addAll(new AStar(fromWP, nearstStair).getRoute());

                    // go higher
                    String newStair = new StringBuilder(nearstStair.getName()).replace(nearstStair.getName().length()-2,nearstStair.getName().length()-2,""+difference).toString().replaceFirst("0", "");
                    nearstStair = new getWP().getWayPoint(newStair, wayPoints);
                    route.addAll(new AStar(nearstStair, targetWP).getRoute());
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            } else {
                Log.d("TAG", "onClick: else");
                AStar astar = new AStar(fromWP, targetWP);
                route = astar.getRoute();

            }
            Log.d("TAG", "onClick: after");
            binding.imageHome.getMapAtLevel(binding.imageHome.getLevel()).setRoute(route);
            if(route == null || route.isEmpty())
                Log.d("TAG", "onClick: ALLE");
            for (WayPoint wp : route) {
                Log.d("TAG", "onClick: for");
                Log.d("Route", " " + wp.getName());
            }
        }
    }

    class getWP {
        public WayPoint getWayPoint(String wpString, ArrayList<WayPoint> wayPoints) {
            for(WayPoint wp: wayPoints) {
                if(wp.getName().toLowerCase().equals(wpString.toLowerCase())) {
                    return wp;
                }
            }
            return null;
        }
    }
}
