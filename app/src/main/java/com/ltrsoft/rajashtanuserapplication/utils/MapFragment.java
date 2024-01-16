package com.ltrsoft.rajashtanuserapplication.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TravelMode;
import com.ltrsoft.rajashtanuserapplication.R;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private boolean isLocationPermissionOk, istraffic;
    private Marker currentMarker;

    private Location currentLocation;

    LatLng currentlatlng;
    private FloatingActionButton traffic, currentlocations, maptype;
    private int currentMapType = GoogleMap.MAP_TYPE_NORMAL; // Initial map type

    CardView distancedata;

    private GoogleMap mMap;

    private FloatingActionButton currentlocation;
    private TextView textView;
    private EditText address;

    private Button car, bike, search;
    private Marker currentLocationMarker;
    private List<LatLng> nearbyLocations;

    // Add a Polyline for the route
    private Polyline routePolyline;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map_framgment, container, false);

        car = view.findViewById(R.id.button);
        bike = view.findViewById(R.id.button2);

        textView = view.findViewById(R.id.textView);
        distancedata = view.findViewById(R.id.addressofmarker);


        nearbyLocations = new ArrayList<>();
        nearbyLocations.add(new LatLng(18.421488794807782, 76.57396003603935)); // Example nearby location 1
        nearbyLocations.add(new LatLng(18.449754050985188, 76.54711045324802)); // Example nearby location 2
        nearbyLocations.add(new LatLng(18.4134986799656, 76.56697321683168)); // Example nearby location 3


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        traffic = view.findViewById(R.id.Traffic);
        traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (istraffic) {
                    if (mMap != null) {
                        mMap.setTrafficEnabled(false);
                        istraffic = false;
                    }
                } else {
                    if (mMap != null) {
                        mMap.setTrafficEnabled(true);
                        istraffic = true;
                    }
                }
            }
        });


        currentlocations = view.findViewById(R.id.currentLocations);
        currentlocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcurrentlocation();
            }
        });

        maptype = view.findViewById(R.id.maptype);
        maptype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentMapType) {
                    case GoogleMap.MAP_TYPE_NORMAL:
                        currentMapType = GoogleMap.MAP_TYPE_HYBRID;
                        break;
                    case GoogleMap.MAP_TYPE_HYBRID:
                        currentMapType = GoogleMap.MAP_TYPE_SATELLITE;
                        break;
                    case GoogleMap.MAP_TYPE_SATELLITE:
                        currentMapType = GoogleMap.MAP_TYPE_TERRAIN;
                        break;
                    case GoogleMap.MAP_TYPE_TERRAIN:
                        currentMapType = GoogleMap.MAP_TYPE_NORMAL;
                        break;
                }

                // Set the new map type
                mMap.setMapType(currentMapType);
            }
        });

        return view;
    }


    private LatLng getLatLngFromAddress(String enteredAddress) {

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(enteredAddress, 1);
            if (addresses != null && addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);


            getLastKnownLocation();

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (currentLocationMarker != null) {
                    LatLng currentLatLng = currentLocationMarker.getPosition();
                    LatLng nearbyLatLng = marker.getPosition();
                    double distance = calculateDistance(currentLatLng, nearbyLatLng);
                    displayDistance(distance);

                    drawRoute(currentLatLng, nearbyLatLng);
                    distancedata.setVisibility(view.VISIBLE);

                }

                return false;
            }
        });
    }

    private void getLastKnownLocation() {
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                if (currentLocationMarker == null) {
                    currentLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(currentLatLng)
                            .title("Current Location")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                } else {

                    currentLocationMarker.setPosition(currentLatLng);
                }


                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f));


                for (LatLng nearbyLocation : nearbyLocations) {
                    float distance = (float) calculateDistance(currentLatLng, nearbyLocation);
                    if (distance <= 2000) {
                        mMap.addMarker(new MarkerOptions().position(nearbyLocation).title("Nearby Location"));

                        // Add latitude and longitude to your TextView or any other view as needed

                        textView.setText("Latitude: " + nearbyLocation.latitude + "\nLongitude: " + nearbyLocation.longitude);
                    }
                }
            }
        });
    }

    private double calculateDistance(LatLng point1, LatLng point2) {
        Location loc1 = new Location("");
        loc1.setLatitude(point1.latitude);
        loc1.setLongitude(point1.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(point2.latitude);
        loc2.setLongitude(point2.longitude);

        return loc1.distanceTo(loc2);
    }

    private void displayDistance(double distance) {
        // Display the distance in a toast
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedDistance = decimalFormat.format(distance / 1000); // Convert meters to kilometers
        Toast.makeText(getContext(), "Distance: " + formattedDistance + " km", Toast.LENGTH_SHORT).show();

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double bikingSpeedKmph = 15.0;
                double timeInHours = distance / (bikingSpeedKmph * 1000);
                double timeInMinutes = timeInHours * 60;

                textView.setText("Distance: " + (float) (distance / 1000) + " Km\n" +
                        "Bike Time: " + (int) timeInHours + " hours " + (int) (timeInMinutes % 60) + " minutes");
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double caringspeedkph = 30.0;
                double carhaurs = distance / (caringspeedkph * 1000);
                double carminute = caringspeedkph * 60;

                textView.setText("Distance: " + (float) (distance / 1000) + " Km\n" +
                        "Car Time: " + (int) carhaurs + " hours " + (int) (carminute % 60) + " minutes");
            }
        });
    }

    private void drawRoute(LatLng origin, LatLng destination) {
        DirectionsResult results = getDirectionsDetails(origin, destination);


        if (results != null) {
            List<LatLng> path = new ArrayList<>();


            for (DirectionsStep step : results.routes[0].legs[0].steps) {
                path.addAll(PolyUtil.decode(step.polyline.getEncodedPath()));
            }

            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(path)
                    .width(5)
                    .color(Color.BLUE);


            if (routePolyline != null) {
                routePolyline.remove();
            }


            routePolyline = mMap.addPolyline(polylineOptions);
        }
    }

    private DirectionsResult getDirectionsDetails(LatLng origin, LatLng destination) {
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyABn51m493riDcBk3eT9Rc0Vbo6ILyaNTA")
                    .build();

            // Perform the directions request
            DirectionsApiRequest request = DirectionsApi.getDirections(context,
                    origin.latitude + "," + origin.longitude,
                    destination.latitude + "," + destination.longitude);


            request.mode(TravelMode.DRIVING);

            return request.await();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap);
            }
        }
    }


    public void getcurrentlocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionOk = false;
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @SuppressLint("PotentialBehaviorOverride")
            @Override
            public void onSuccess(Location location) {
                currentLocation = location;
                moveCameraToLocation(location);

            }
        });
    }


    private void moveCameraToLocation(Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new
                LatLng(location.getLatitude(), location.getLongitude()), 15);

        currentlatlng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                .snippet(firebaseAuth.getCurrentUser().getDisplayName());
        if (currentMarker != null) {
            currentMarker.remove();
        }

        currentMarker = mMap.addMarker(markerOptions);
        currentMarker.setTag(703);
        mMap.animateCamera(cameraUpdate);

    }
}
