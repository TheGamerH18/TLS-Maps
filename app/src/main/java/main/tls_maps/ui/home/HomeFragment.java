package main.tls_maps.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import main.tls_maps.databinding.FragmentHomeBinding;
import main.tls_maps.map.WayPoint;
import main.tls_maps.map.pathFinding.PathFinding;

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

        binding.QR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), main.tls_maps.QRCode.Scanner.class));
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
                new PathFinding(from, target, binding, v);
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