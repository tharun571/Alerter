package com.example.alerter.Code;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Sample {

    private static final int REQUEST_LOCATION = 1;
    Double lat, longi;
    LocationManager locationManager;


private void loc(){

    //to get location and address of the users location


    ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        onGPS();

    } else {

        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {

            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (locationGPS != null) {


                lat = locationGPS.getLatitude();
                longi = locationGPS.getLongitude();


            } else {
                Location locationGPS1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (locationGPS1 != null) {


                    lat = locationGPS1.getLatitude();
                    longi = locationGPS1.getLongitude();


                } else {
                    Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();

                }

            }
        }


    }




}
    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    List<Address> addresses;
    Geocoder geocoder;
    String address;
    private void add(){

    // to find address

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, longi, 1);

            address = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getLocality();
            String city = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String code = addresses.get(0).getPostalCode();

            String fullAddress = address;


            if (fullAddress != null) {
                text.setText(fullAddress);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }



    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String addr = "nameKey";
    public static final Double latitude = null;
    public static final Double longitude = null;
    private void sharedpreffs(){


        //sharedpreffs

        SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit();
        editor.putString("address", address);
        editor.putString("latitude", ""+lat);  //parseit from string to double while getting the value
        editor.putString("longitude", ""+longi);

        editor.apply();

    }


}
