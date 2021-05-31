package main.tls_maps.ui.home;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import main.tls_maps.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public Canvas canvas;
    public Paint redPaint;

    public Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ALPHA_8);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        redPaint = new Paint();
        redPaint.setARGB(255,255,0,0);

        canvas = new Canvas(bitmap);
        canvas.drawCircle(100,100,500,redPaint);

        //final TextView textView = binding.textHome;
        //final ImageView imageView = binding.imageHome;
        //final Drawable drawable = imageView.getDrawable();
        //System.out.println((drawable!=null)+" THIS IS IMPORTANT AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        //drawable.draw(canvas);

        container.draw(canvas);

        //imageView.draw(canvas);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                //imageView.draw(canvas);
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