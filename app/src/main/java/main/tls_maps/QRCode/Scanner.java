package main.tls_maps.QRCode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import main.tls_maps.MainActivity;
import main.tls_maps.R;
import main.tls_maps.ui.home.HomeFragment;

public class Scanner extends AppCompatActivity {

    /**
     * This Activity is for the QR Code Scanner
     */

    SurfaceView surfaceView;
    private BarcodeDetector QRCodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the Transition of switching the Activity to 0
        overridePendingTransition(0,0);

        setContentView(R.layout.activity_scan_barcode);
        surfaceView = findViewById(R.id.surfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Close the Camera if the Activity is closed
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Initialse every thing
        initialiseDetectorsAndSources();
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) startActivity(getIntent());
    }

    private void initialiseDetectorsAndSources() {

        // Create the BarCodeScanner
        QRCodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        // Create the Camera View
        cameraSource = new CameraSource.Builder(this, QRCodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    // Check the Permission
                    if (ActivityCompat.checkSelfPermission(Scanner.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        // Request Permission
                        ActivityCompat.requestPermissions(Scanner.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // Stop the Camera
                cameraSource.stop();
            }
        });


        QRCodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            /**
             * Calleed if there is an QR Code Detected
             * @param detections - List of the QR-Codes
             */
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                // Check if there is something in the List
                if (barcodes.size() != 0) {
                    surfaceView.post(new Runnable() {
                        @Override
                        public void run() {
                            // Put the QR-Code text in the HomeFragment
                            HomeFragment.from = barcodes.valueAt(0).rawValue;

                            // Go back to the MainActivity
                            startActivity(new Intent(Scanner.this, MainActivity.class));
                        }
                    });
                }
            }
        });
    }
}