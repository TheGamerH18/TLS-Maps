package main.tls_maps.ui.home;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import main.tls_maps.R;
import main.tls_maps.databinding.ActivityMainBinding;
import main.tls_maps.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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

            @Override
            public void onClick(View v) {

                String from = binding.start.getText().toString();
                String target = binding.target.getText().toString();

                if(from == "" || from.equals("")){
                    Log.d("FindRoom", "getTargets: " + target);
                    return;
                }
                Log.d("LaunchAStar", "Start: " + from + "\tTarget: " + target);
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
        binding = null;
    }
}