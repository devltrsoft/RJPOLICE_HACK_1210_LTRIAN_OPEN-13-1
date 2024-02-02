package com.ltrsoft.rajashtanuserapplication.fragments;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.inner_fragments.ComplainHistoryPage;
import com.ltrsoft.rajashtanuserapplication.inner_fragments.CreateComplainPage;
import com.ltrsoft.rajashtanuserapplication.inner_fragments.EmergencyPage;

public class DashBoard extends Fragment implements OnMapReadyCallback{
 public DashBoard() {    }
    private ImageView Cadd;
    private boolean isLocationPermissionOk, istraffic;
    private Marker currentMarker;
    private Location currentLocation;
    private LatLng currentlatlng;
    private Button button;
    private FloatingActionButton camera, missing;
    private BottomNavigationView navigationView;
    private GoogleMap mMap;
    private ImageView addbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dash_board, container, false);

        navigationView = v.findViewById(R.id.bottomdashnav);
        camera = v.findViewById(R.id.camera);
        missing = v.findViewById(R.id.missing);
        addbtn = v.findViewById(R.id.cadd);

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(" Raj User ");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.dashboard_layout, new EmergencyPage()).commit();
        mapFragment.getMapAsync( this);


        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateComplainPage complainPage = new CreateComplainPage();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.dashboard_layout, complainPage)
                        .commit();
            }
        });
        missing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MissingPage missingPage = new MissingPage();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.containermain, missingPage)
                        .commit();
            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.ecall) {
                    item.setCheckable(true);
                    EmergencyPage emergencyPage = new EmergencyPage();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_layout, emergencyPage).commit();
                } else if (id == R.id.ecomplaint) {
                    item.setCheckable(true);
                    // Toast.makeText(getContext(), "Compalint Clicked", Toast.LENGTH_SHORT).show();
                    ComplainHistoryPage complainHistoryPage = new ComplainHistoryPage();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_layout, complainHistoryPage).commit();
                } else if (id == R.id.enews) {
                    item.setCheckable(true);
                    //Toast.makeText(getContext(), "News", Toast.LENGTH_SHORT).show();
                    NewsFragment newsPage = new NewsFragment();
                    loadfragment(newsPage);

                } else if (id == R.id.emassge) {
                    item.setCheckable(true);
                    //Toast.makeText(getContext(), "Message clicked", Toast.LENGTH_SHORT).show();
                    ChatBot message = new ChatBot();
                    loadfragment(message);
                }
                return true;
            }
        });
        final GoogleMap[] googleMap = new GoogleMap[1];

        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    googleMap[0] = map;

                    // Set a click listener for the map
                    googleMap[0].setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            // Handle the map click event here

                            MapFragment mapFragment1 = new MapFragment();

                            getFragmentManager().beginTransaction().add(R.id.containermain, mapFragment1).addToBackStack(null).commit();


                        }
                    });
                }
            });
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack("fragmenttag", FragmentManager.POP_BACK_STACK_INCLUSIVE);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPage cameraPage = new CameraPage();
                getFragmentManager().beginTransaction().replace(R.id.containermain, cameraPage).addToBackStack(null).commit();
            }
        });
        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionOk = false;
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @SuppressLint("PotentialBehaviorOverride")
            @Override
            public void onSuccess(Location location) {
                if (location!=null) {
                    currentLocation = location;
                    moveCameraToLocation(location);
                }
                else {
                    Toast.makeText(getContext(), "location is null", Toast.LENGTH_SHORT).show();
                    Log.d("location ","null");
                }
            }
        });
    }

    private void loadfragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containermain, fragment);
        fragmentTransaction.addToBackStack("fragmenttag");
        fragmentTransaction.commit();
    }

    private void moveCameraToLocation(Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);

        currentlatlng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                .snippet(firebaseAuth.getCurrentUser().getDisplayName());
        if (currentMarker != null) {
            currentMarker.remove();
        }
        currentMarker = mMap.addMarker(markerOptions);
        currentMarker.setTag(703);
        mMap.animateCamera(cameraUpdate);
    }
}