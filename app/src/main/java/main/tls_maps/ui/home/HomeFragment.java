package main.tls_maps.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import main.tls_maps.databinding.FragmentHomeBinding;
import main.tls_maps.map.AStar;
import main.tls_maps.map.WayPoint;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static String from;
    private ArrayList<WayPoint> RouteLVL0;
    private ArrayList<WayPoint> RouteLVL1;
    private ArrayList<WayPoint> RouteLVL2;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the View to the MainActivity
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.start.setText(from);

        binding.floatingActionButtonUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // This makes the Level go one Up, if Possible.
                //Log.d("ClickEvent","The Level Up button has been Pressed");
                binding.imageHome.ChangeLevel(1);
                showRoute();
            }
        });
        binding.floatingActionButtonDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // This makes the level go one Down, if Possible.
                //Log.d("ClickEvent","The Level Down button has been Pressed");
                binding.imageHome.ChangeLevel(-1);
                showRoute();
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {

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

            /**
             * This is for the A*
             *
             * The debug of Switching the Level is also Handled here
             * @param v
             */
            @Override
            public void onClick(View v) {

                String from = binding.start.getText().toString();
                String target = binding.target.getText().toString();

                if(from == "" || from.equals("")){
                    Log.d("FindRoom", "getTargets: " + target);
                    return;
                }
                ArrayList<WayPoint> wayPoints = binding.imageHome.getWayPoints();
                WayPoint fromWP = new getWP().getWayPoint(from, wayPoints);
                WayPoint targetWP = new getWP().getWayPoint(target, wayPoints);
                if(fromWP == null)
                    Snackbar.make(v, "Startpunkt konnte nicht gefunden werden", Snackbar.LENGTH_LONG).show();
                else if(targetWP == null)
                    Snackbar.make(v, "Zielpunkt konnte nicht gefunden werden", Snackbar.LENGTH_LONG).show();
                else {
                    ArrayList<WayPoint> route = new ArrayList<>();
                    if(!(""+ from.charAt(1)).equals(target.charAt(1))) {
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

                            for(WayPoint wp : route){
                                Log.d("TAG", "Route: " + wp.getName());
                            }


                        } catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    } else {
                        AStar astar = new AStar(fromWP, targetWP);
                        route = astar.getRoute();
                    }
                    for (WayPoint wp : route) {
                        Log.d("Route", " " + wp.getName());
                    }
                }
            }
        });

        binding.start.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    binding.QR.setVisibility(View.VISIBLE);
                else
                    binding.QR.setVisibility(View.GONE);
            }
        });

        binding.QR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), main.tls_maps.QRCode.Scanner.class));
            }
        });

        binding.floatingActionButtonDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("ClickEvent","The Level Down button has been Pressed");
                binding.imageHome.ChangeLevel(-1);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        from = null;
        binding = null;
    }

    // UNUSED atm
    private void showRoute() {

        int currentLvl = binding.imageHome.getLevel();
        switch(currentLvl){
            case 0:
                if(RouteLVL0 == null)
                    return;
                for(WayPoint wp : RouteLVL0) {
                    Log.d("Route Lvl 1", "onClick: ");
                }
                break;
            case 1:
                if(RouteLVL1 == null)
                    return;
                for(WayPoint wp : RouteLVL1) {
                    Log.d("Route Lvl 1", "onClick: ");
                }
                break;
            case 2:
                if(RouteLVL2 == null)
                    return;
                for(WayPoint wp : RouteLVL2) {
                    Log.d("Route Lvl 1", "onClick: ");
                }
                break;
        }
    }
}