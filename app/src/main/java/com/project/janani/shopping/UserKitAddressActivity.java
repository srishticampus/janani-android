package com.project.janani.shopping;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;


public class UserKitAddressActivity extends AppCompatActivity {

    private EditText etUserName;
    private EditText etUserLocation;
    private EditText etPhoneNumber;
    private TextView btCurrentLocation;
    private Button btPlaceOrderButton;

    int i = 0;
    int j = 0;

    public static FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 1;
    public static Location currentLocation;
    public static double getLatitude, getLongitude;
    LocationManager locationManager;


    boolean gps_enabled = false;
    boolean network_enabled = false;
    private CardView cvUserLocation;
    SharedPreferences locationSharedPreference;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_kit_address);
        initView();

        locationSharedPreference = getSharedPreferences("loginShared", MODE_PRIVATE);
        editor = locationSharedPreference.edit();


        editor.apply();

        etUserLocation.setVisibility(View.GONE);


        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        btPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("address", etUserLocation.getText().toString());
                editor.putString("optionalPhoneNumber", etPhoneNumber.getText().toString());
                editor.apply();
                startActivity(new Intent(getApplicationContext(), UserKitPaymentActivity.class));

            }
        });


        btCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0) {
                    Toast.makeText(UserKitAddressActivity.this, "Picking current location", Toast.LENGTH_SHORT).show();
                    try {
                        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    } catch (Exception ex) {
                    }

                    try {
                        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    } catch (Exception ex) {
                    }

                    getCurrentLocation();
                } else {
                    Toast.makeText(UserKitAddressActivity.this, "Location Already Picked", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void checkGPSEnabled() {
        AlertDialog.Builder obj = new AlertDialog.Builder(UserKitAddressActivity.this);
        obj.setMessage("GPS not enabled");
        obj.setTitle("alert!");
        obj.setCancelable(false);
        obj.setPositiveButton("Open Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        obj.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );
        AlertDialog alertDialog = obj.create();
        alertDialog.show();
    }

    private void initView() {
        etUserName = findViewById(R.id.et_user_name);
        etUserLocation = findViewById(R.id.et_user_location);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btCurrentLocation = findViewById(R.id.bt_current_location);
        btPlaceOrderButton = findViewById(R.id.bt_place_order_button);
        cvUserLocation = findViewById(R.id.cv_user_location);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Allow Permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        CurrentLocationRequest.Builder builder = new CurrentLocationRequest.Builder();
        CancellationTokenSource cts = new CancellationTokenSource();
        if (gps_enabled && network_enabled) {
            btCurrentLocation.setTextColor(Color.parseColor("#FF3700B3"));
            etUserLocation.setVisibility(View.GONE);
            fusedLocationProviderClient.getCurrentLocation(builder.build(), cts.getToken()).addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;
                        getLatitude = currentLocation.getLatitude();
                        getLongitude = currentLocation.getLongitude();

                        UserKitPaymentActivity userKitPaymentActivity = new UserKitPaymentActivity(String.valueOf(getLatitude), String.valueOf(getLongitude));

                    } else {
                        Toast.makeText(UserKitAddressActivity.this, "Unable to pick location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            i++;
        } else {
            checkGPSEnabled();
            i = 0;
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Toast.makeText(this, "entered onRequest", Toast.LENGTH_SHORT).show();
        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                    break;
                }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.cv_user_location:
                if (j == 0) {
                    etUserLocation.setVisibility(View.VISIBLE);
                    btCurrentLocation.setEnabled(false);
                    btCurrentLocation.setTextColor(Color.parseColor("#000000"));
                    j++;

                } else {
                    etUserLocation.setVisibility(View.GONE);
                    btCurrentLocation.setEnabled(true);
                    etUserLocation.setText("");
                    j = 0;
                }


        }
    }
}