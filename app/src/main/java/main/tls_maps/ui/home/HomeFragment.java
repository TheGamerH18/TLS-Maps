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
                Log.d("ClickEvent","The Level Up button has been Pressed");
                binding.imageHome.ChangeLevel(1);
            }
        });
        binding.floatingActionButtonDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("ClickEvent","The Level Down button has been Pressed");
                binding.imageHome.ChangeLevel(-1);
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
                    ArrayList<WayPoint> route = new AStar(fromWP, targetWP).getRoute();
                    for(WayPoint wp: route) {
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
}