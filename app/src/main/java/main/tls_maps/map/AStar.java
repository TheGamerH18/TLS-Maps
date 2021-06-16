package main.tls_maps.map;

import java.util.ArrayList;

public class AStar {
    private ArrayList<WayPoint> Route;
    private ArrayList<WayPoint> StartRoute;
    private ArrayList<WayPoint> GoalRoute;
    private WayPoint Position;

    /**
     *  Empty Constructor for Stair searching
     */
    public AStar () {

    }

    /**
     * The Constructor for the Aglorythm
     * @param start - Where is the Start
     * @param goal - Where is the End
     */
    public AStar(WayPoint start, WayPoint goal) {

        // Empty ArrayList for the Recursive call
        ArrayList<WayPoint> path = new ArrayList<>();

        // If the Goal has a Knot point make the Path from Knot to the Goal
        if(goal.hasKnot()) {
            // Set the Knot as Start
            WayPoint tempStart = goal.getKnot();
            this.Position = tempStart;

            // Add the Start Position to the path
            path.add(tempStart);

            // Calculate the Route
            CalculateRoute(path, tempStart, goal);

            // set the Route to the GoalRoute
            // they will combine later
            this.GoalRoute = Route;

            // reset Everything
            Route = null;
            path = new ArrayList<>();

            // Make the Knot the new Goal
            goal = goal.getKnot();
        }
        // If the Start has a Knot point make the Path from Start to the Knot
        if(start.hasKnot()) {
            // Sed the Position
            this.Position = start;

            // add it to the Array List
            path.add(start);

            // Calculate the Route
            CalculateRoute(path, start, start.getKnot());

            // Put the Route in
            this.StartRoute = Route;

            // reset Everything
            Route = null;
            path = new ArrayList<>();

            // Make the Knot as Start
            start = start.getKnot();
        }

        // This calculate the Rest of the Route

        // Set the Position
        this.Position = start;

        // Add the Pos
        path.add(start);

        // Calculate the Route
        CalculateRoute(path, start, goal);

        // put together
        if(this.StartRoute != null) {
            // The StartRoute needs to come before
            this.StartRoute.addAll(this.Route);
            this.Route = this.StartRoute;
        }
        if(this.GoalRoute != null)
            this.Route.addAll(this.GoalRoute);
    }


    /**
     * This Method is Recursiv and Shouldnt called with empty Constructor
     *
     * The Algorythm goes throw the Neighbors of the Waypoint, but ist check if the Distance between is going down
     * if it goes down than the Algorythm goes to it, otherwise they nothing happen.
     *
     * @param path - The Current Path of the Algorythm
     * @param Location - where is the Algorhythm
     * @param Goal - the Target WayPoint
     */
    public void CalculateRoute(ArrayList<WayPoint> path, WayPoint Location, WayPoint Goal) {

        // Loop through all Neighbors
        for(WayPoint wp : Location.getNeighbourPoints()) {

            // Check if the WayPoint is the Target
            if(wp.getName().equals(Goal.getName()) || wp.equals(Goal)) {
                path.add(wp);
                this.Route = path;
            }

            // check if the Waypoint is Relevant at all
            if(wpIsRelavant(wp, Goal)){
                // If the Waypoint is Allready in the Route, dont go there
                if(path.contains(wp))
                    continue;

                // Add the Path and restart with this Position until there is now Way more or the Target is Reached
                path.add(wp);
                this.Position = wp;
                CalculateRoute(path, wp, Goal);
            }
        }
    }

    /**
     *  This Method checks if a WayPoint is relevant or not
     * @param wp - the WayPoint which should be Checked
     * @param Goal - Where we need to get to
     * @return true if the WayPoint is Relevant
     */
    private boolean wpIsRelavant(WayPoint wp, WayPoint Goal) {

        // Check if is distance is getting Lower
        if(Goal.getPosition().sub(wp.getPosition()).magnitude() < Goal.getPosition().sub(Position.getPosition()).magnitude()){
            // if it does return true
            return true;
        }
        return false;
    }

    /**
     * This Method is to get the Route
     * @return the fastest Route to get to the Target
     */
    public ArrayList<WayPoint> getRoute() {
        // Catch if there isnt a Route
        if(this.Route == null)
            return new ArrayList<>();
        return this.Route;
    }

    /**
     * This Method get the Nearst Stair
     *
     * Its need because we are in a 2D World
     *
     * @param fromWP - the Start Position
     * @param wayPoints - the ArrayList of all WayPoints
     * @return
     */
    public WayPoint getStair(WayPoint fromWP, ArrayList<WayPoint> wayPoints) {

        // Init the Variables
        double lowest = Integer.MAX_VALUE;
        WayPoint lowestDist = null;

        // Loop through every WayPoint
        for(WayPoint wp : wayPoints) {
            // Check if the Name match a Stair
            if(wp.getName().contains("_Treppe")) {
                // Check if the Stair is nearer than the previously
                if(lowest > fromWP.getPosition().sub(wp.getPosition()).magnitude()){
                    // if it is nearer than remember it
                    lowest = fromWP.getPosition().sub(wp.getPosition()).magnitude();
                    lowestDist = wp;
                }
            }
        }

        // Return the Nearst Stair
        return lowestDist;
    }
}