package com.epsi.gostyle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView titreApp = findViewById(R.id.titreApp);
        titreApp.setTextColor(Color.WHITE);
        TextView marque = findViewById(R.id.marqueGoStyle);
        marque.setTextColor(Color.WHITE);

        final Button scanCodeBtn = findViewById(R.id.scanCodeBtn);
        final Button siteWebBtn = findViewById(R.id.siteWebBtn);

        final Drawable drawableBouton = getResources().getDrawable(R.drawable.button);
        final Drawable drawableBoutonToucheDecendante = getResources().getDrawable(R.drawable.button_touch_down);

        // Action sur bouton SCAN CODE
        scanCodeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    scanCodeBtn.setBackgroundDrawable(drawableBouton);
                    scanReader();
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scanCodeBtn.setBackgroundDrawable(drawableBoutonToucheDecendante);
                }
                return true;
            }
        });

        // Action bouton WEBSITE
        siteWebBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    siteWebBtn.setBackgroundDrawable(drawableBouton);
                    Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                    httpIntent.setData(Uri.parse("https://gostyle.fr"));
                    startActivity(httpIntent);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    siteWebBtn.setBackgroundDrawable(drawableBoutonToucheDecendante);
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                // Si pas de résultat du scan
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                // Si resultat du scan du code -> Activité ListePromotions
                Intent myIntent = new Intent(MainActivity.this, ListePromos.class);
                myIntent.putExtra("resultContents", result.getContents());
                MainActivity.this.startActivity(myIntent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Scan du QR Code appel api
    private void scanReader(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    public void printInfo(String message) {
        Toast.makeText(MainActivity.this,message, Toast.LENGTH_LONG).show();
    }
}
