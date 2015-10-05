package com.example.ralph.locationmgr;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView latitudeField;
    private TextView longitudeField;
    private TextView accuracyField;
    private TextView altitudeField;
    private TextView bearingField;
    private TextView providerField;
    private TextView speedField;
    private TextView timeField;
    private TextView statusField;
    private TextView timeIntervalField;
    private TextView distanceIntervalField;
    private LocationManager locationManager;
    private String provider;
    private long minTime = 1000;
    private float minDistance = 3;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitudeField = (TextView) findViewById(R.id.LatitudeID);
        longitudeField = (TextView) findViewById(R.id.LongitudeID);
        accuracyField = (TextView) findViewById(R.id.AccuracyID);
        altitudeField = (TextView) findViewById(R.id.AltitudeID);
        bearingField = (TextView) findViewById(R.id.BearingID);
        providerField = (TextView) findViewById(R.id.ProviderID);
        speedField = (TextView) findViewById(R.id.SpeedID);
        timeField = (TextView) findViewById(R.id.TimeID);
        statusField = (TextView) findViewById(R.id.StatusID);
        timeIntervalField = (TextView) findViewById(R.id.TimeIntervalID);
        distanceIntervalField = (TextView) findViewById(R.id.DistanceIntervalID);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the location provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            Log.d("MainActivity", "Provider: " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        timeIntervalField.setText(String.valueOf(minTime));
        distanceIntervalField.setText(String.valueOf(minDistance));
        locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("MainActivity", "onLocationChanged");
        double lat = location.getLatitude();
        latitudeField.setText(String.valueOf(lat));
        double lng = location.getLongitude();
        longitudeField.setText(String.valueOf(lng));

        long tim = location.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String localTime = sdf.format(new Date(tim));
        timeField.setText(localTime);

        String pro = location.getProvider();
        providerField.setText(pro);
        if (location.hasAccuracy()) {
            float acc = location.getAccuracy();
            accuracyField.setText(String.valueOf(acc));
        }
        if (location.hasAltitude()) {
            double alt = location.getAltitude();
            altitudeField.setText(String.valueOf(alt));
        }
        if (location.hasBearing()) {
            float brg = location.getBearing();
            bearingField.setText(String.valueOf(brg));
        }
        if (location.hasSpeed()) {
            float spd = location.getBearing();
            speedField.setText(String.valueOf(spd));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("MainActivity", "onStatusChanged");
        switch (status) {
            case LocationProvider.AVAILABLE:
                statusField.setText("AVAILABLE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                statusField.setText("TEMPORARILY UNAVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                statusField.setText("OUT OF SERVICE");
                break;
            default:
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("MainActivity", "Enabled new provider: " + provider);
        statusField.setText("ENABLED");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("MainActivity", "Disabled provider: " + provider);
        statusField.setText("DISABLED");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.time_setting_1:
                Log.d("MainActivity", "onOptionsItemSelected: time_setting_1");
                minTime = 1000;
                timeIntervalField.setText(String.valueOf(minTime));
                locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
                return true;
            case R.id.time_setting_10:
                Log.d("MainActivity", "onOptionsItemSelected: time_setting_10");
                minTime = 10000;
                timeIntervalField.setText(String.valueOf(minTime));
                locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
                return true;
            case R.id.time_setting_60:
                Log.d("MainActivity", "onOptionsItemSelected: time_setting_60");
                minTime = 60000;
                timeIntervalField.setText(String.valueOf(minTime));
                locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
                return true;
            case R.id.distance_setting_1:
                Log.d("MainActivity", "onOptionsItemSelected: distance_setting_1");
                minDistance = 1;
                distanceIntervalField.setText(String.valueOf(minDistance));
                locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
                return true;
            case R.id.distance_setting_3:
                Log.d("MainActivity", "onOptionsItemSelected: distance_setting_3");
                minDistance = 3;
                distanceIntervalField.setText(String.valueOf(minDistance));
                locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
                return true;
            case R.id.distance_setting_10:
                Log.d("MainActivity", "onOptionsItemSelected: distance_setting_10");
                minDistance = 10;
                distanceIntervalField.setText(String.valueOf(minDistance));
                locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
