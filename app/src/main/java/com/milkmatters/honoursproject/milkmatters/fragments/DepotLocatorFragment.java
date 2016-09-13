package com.milkmatters.honoursproject.milkmatters.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 */
public class DepotLocatorFragment extends Fragment implements OnMapReadyCallback {
    private View view;
    private GoogleMap mMap;
    private ArrayList<Depot> depots;

    public DepotLocatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DepotLocatorFragment.
     */
    public static DepotLocatorFragment newInstance(String param1, String param2) {
        DepotLocatorFragment fragment = new DepotLocatorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_depot_locator, container, false);

        hideFAB();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return this.view;
    }

    /**
     * Method to show the floating action button
     */
    public void showFAB() {
        FloatingActionButton fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fab);
        fab.show();
    }

    /**
     * Method to hide the floating action button
     */
    public void hideFAB() {
        FloatingActionButton fab = (FloatingActionButton) this.getActivity().findViewById(R.id.fab);
        fab.hide();
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
        // add the location layer
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // store the location of milk matters
        LatLng milkMatters = new LatLng(-33.949444, 18.475001);

        // get all the depots
        DepotsTableHelper depotsTableHelper = new DepotsTableHelper(this.getContext());
        ArrayList<Depot> depots = depotsTableHelper.getAllDepots();
        depotsTableHelper.closeDB();

        // create a marker for each depot
        for (Depot depot: depots)
        {
            LatLng latLng = new LatLng(depot.getLocation().getLatitude(),
                    depot.getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng)
                    .title(depot.getName())
                    .snippet(depot.getSnippet())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        }

        // Move the camera instantly to milk matters with a zoom of 13.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(milkMatters, 13));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 1500, null);

        /* Use a custom map info window adapter.
           Changes the layout of the info window.
         */
        MapInfoWindowAdapter mapInfoWindowAdapter =
                new MapInfoWindowAdapter(this.getActivity().getLayoutInflater());
        mMap.setInfoWindowAdapter(mapInfoWindowAdapter);
    }
}
