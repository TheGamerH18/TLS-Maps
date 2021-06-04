package main.tls_maps.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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

        binding.start.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    binding.QR.setVisibility(View.VISIBLE);
                else
                    binding.QR.setVisibility(View.GONE);
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                binding.QR.setVisibility(View.VISIBLE);
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
                    return;
                BarcodeDetector detector;
                detector = new BarcodeDetector.Builder(v.getContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();

                CameraSource cameraSource = new CameraSource.Builder(v.getContext(), detector)
                        .setRequestedPreviewSize(1920, 1080)
                        .setAutoFocusEnabled(true) //you should add this feature
                        .build();
            /*
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
             */
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