package com.milkmatters.honoursproject.milkmatters.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.milkmatters.honoursproject.milkmatters.R;
import com.milkmatters.honoursproject.milkmatters.adapters.MapInfoWindowAdapter;
import com.milkmatters.honoursproject.milkmatters.database.DepotsTableHelper;
import com.milkmatters.honoursproject.milkmatters.model.Depot;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DepotLocatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 * code from http://stackoverflow.com/questions/34582370/how-can-i-use-google-maps-and-locationmanager-to-show-current-location-on-androi/34582595#34582595
 */
public class DepotLocatorFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private Context context;
    // Constants
    private final LatLng MILK_MATTERS = new LatLng(-33.949444, 18.475001);
    private final int DEFAULT_DURATION = 2000;
    private final int DEFAULT_ZOOM = 12;
    // Data
    private ArrayList<Depot> depots;
    private ArrayList<Marker> markers;
    // Map
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    // View
    private ViewGroup container;
    private LayoutInflater inflater;
    private View view;

    public DepotLocatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DepotLocatorFragment.
     */
    public static DepotLocatorFragment newInstance() {
        DepotLocatorFragment fragment = new DepotLocatorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.markers = new ArrayList<Marker>();
        this.context = getContext();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_depot_locator, container, false);

        hideFAB();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button findNearestDepotButton = (Button) this.view.findViewById(R.id.find_nearest_depot_button);
        findNearestDepotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNearestDepot();
            }
        });

        return this.view;
    }

    /**
     * Method to find the nearest depot and display it on the map.
     */
    public void findNearestDepot()
    {
        Marker nearestMarker = findNearestMarker();
        if (nearestMarker != null)
        {
            // Move the camera smoothly
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nearestMarker.getPosition(), DEFAULT_ZOOM));
            // Change the color of the nearest marker to yellow
            // Change the color of all other markers to pink
            for (Marker marker: markers) {
                if (!marker.equals(nearestMarker))
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
            }
            nearestMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        }
    }

    /**
     * Method to find the nearest marker to the user's location.
     * @return the nearest marker
     */
    private Marker findNearestMarker()
    {
        Marker nearestMarker = null;
        double minimumDistance = Double.MAX_VALUE;
        double currentDistance = 0;
        Location markerLocation = new Location("");

        if (mLastLocation != null)
        {
            // make a copy in case the current location changes midway through execution
            Location currentLocation = new Location(mLastLocation);
            for (Marker marker: markers)
            {
                markerLocation.setLatitude(marker.getPosition().latitude);
                markerLocation.setLongitude(marker.getPosition().longitude);
                currentDistance = currentLocation.distanceTo(markerLocation);
                if (currentDistance < minimumDistance)
                {
                    minimumDistance = currentDistance;
                    nearestMarker = marker;
                }
            }
        }

        return nearestMarker;
    }

    /**
     * Method to show the floating action button
     */
    public void showFAB()
    {
        Button fab = (Button) this.getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
    }

    /**
     * Method to hide the floating action button
     */
    public void hideFAB()
    {
        Button fab = (Button) this.getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Use a custom map info window adapter (changes the layout of the info window.)
        MapInfoWindowAdapter mapInfoWindowAdapter =
                new MapInfoWindowAdapter(this.getActivity().getLayoutInflater());
        mMap.setInfoWindowAdapter(mapInfoWindowAdapter);
        addDepots();
        addLocationLayer();
        // moveCameraAndZoom(MILK_MATTERS, DEFAULT_ZOOM);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * Method to add all the depots to the map.
     */
    public void addDepots()
    {
        // get all the depots
        DepotsTableHelper depotsTableHelper = new DepotsTableHelper(this.getContext());
        this.depots = depotsTableHelper.getAllDepots();
        depotsTableHelper.closeDB();

        // create a marker for each depot
        for (Depot depot: depots)
        {
            LatLng latLng = new LatLng(depot.getLocation().getLatitude(),
                    depot.getLocation().getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(depot.getName())
                    .snippet(depot.getSnippet())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            markers.add(marker);
        }
    }

    /**
     * Method to add the location layer to the map.
     */
    public void addLocationLayer()
    {
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                // add the location layer
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
            else
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        else {
            buildGoogleApiClient();
            // add the location layer
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                Log.e("status", status.getStatusMessage());
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    getActivity(), 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //move the camera to the current location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Snackbar.make(this.view, "The depot locator will not behave properly without being " +
                        "able to access your device's location.", Snackbar.LENGTH_LONG).show();
                Button button = (Button) this.view.findViewById(R.id.find_nearest_depot_button);
                button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorLightPink));
            }
        }
    }

    /**
     * Method to move and zoom the camera.
     * @param latLng the location
     * @param zoom the zoom of the camera
     */
    private void moveCameraAndZoom(LatLng latLng, int zoom)
    {
        // Move the camera instantly
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom), DEFAULT_DURATION, null);
    }

    /**
     * Overridden onActivityResult method.
     * Should build the Google API client and add the location layer,
     * once the user has either turned on or not turned on the device's location.
     * Display a warning message if the user does not turn on the device's location.
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data the data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_CANCELED) {
            Snackbar.make(this.view, "The depot locator will not behave properly without being " +
                    "able to access your device's location.", Snackbar.LENGTH_LONG).show();
            Button button = (Button) this.view.findViewById(R.id.find_nearest_depot_button);
            button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorLightPink));
        }
        else if (resultCode == Activity.RESULT_OK)
        {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }
}
