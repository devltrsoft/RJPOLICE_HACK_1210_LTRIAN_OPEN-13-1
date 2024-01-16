package com.ltrsoft.rajashtanuserapplication.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.model.Userdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddComplaint extends Fragment {
    private FusedLocationProviderClient mFusedLocationClient;
    private ImageView calender;
    private com.google.android.gms.location.LocationRequest mLocationRequest;
    public static final String URL_complain= "https://rj.ltr-soft.com/public/police_api/data/c_type_read.php";
    private String URL = "https://rj.ltr-soft.com/public/police_api/data/complaint_insert.php";
    public EditText complain_name,complain_contact_no,incident_date,complain_Againsts,complain_description,incident_addresss;
    private Spinner station_id,complain_Type;
    Button submit,complain_location;
    private   Location location;
    int PERMISSION_ID = 44;
    private  RequestQueue requestQueue1;

    ArrayList <String> list,list_complain;
     private ArrayAdapter adapter,adapter2;

    String lattitude = "", langitude = "";
    String complaint_type_id;
    StringBuilder output = new StringBuilder();

    StringBuilder complaintId = new StringBuilder();

    View view;
    private static final String STATION_URL = "https://rj.ltr-soft.com/public/police_api/police_station/read_police_station.php";

    public AddComplaint() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.add_complaint, container, false);
            setId();

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {actionBar.setTitle(" Add Complaint");}
        loadSattion();
        loadcomplaintype();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 String complain_name1=complain_name.getText().toString().trim();
                     String complain_desc= complain_description.getText().toString().trim();
                 String incident_dates=incident_date.getText().toString().trim();
                 String complaint_subject=complain_name.getText().toString().trim();
                 String incident_address=incident_addresss.getText().toString().trim();
                 String complain_Against=complain_Againsts.getText().toString().trim();
                 String lattitude=String.valueOf(location.getLatitude());
                 String longitude=String.valueOf(location.getLongitude());
                complaint_type_id = complain_Type.getSelectedItem().toString();
                UserDataAccess userDataAccess = new UserDataAccess();

//                ComplaintClass complaintClass = new ComplaintClass(complain_name1,complain_desc,incident_dates,complain_location.getText().toString(),incident_addresss.getText().toString(),
//                        complain_Against,userDataAccess.getUserId(getActivity()).toString(),complaint_type_id);

                ComplaintClass complaintClass =new ComplaintClass(complaint_subject,complain_desc,incident_dates,incident_address,"",""
                ,"",complain_Type.getSelectedItem().toString());
                Complaintdeo complaintdeo = new Complaintdeo();
                complaintdeo.createComplain(complaintClass, getContext(), new UserCallBack() {
                    @Override
                    public void userSuccess(Object object) {
                        String success = (String) object;
                        Toast.makeText(getContext(), "success"+success.toString(), Toast.LENGTH_SHORT).show();
                        showPositiveDialogue(success.toString());
                    }

                    @Override
                    public void userError(String error) {
                        Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showDatePickerDialog();
            }
        });
        complain_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
                getLastLocation();
            }

            @SuppressLint("MissingPermission")
            private void getLastLocation() {
                // check if permissions are given
                if (checkPermissions()) {
                    if (isLocationEnabled()) {
                        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                           location= task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                   // Toast.makeText(view.getContext(), "" + location.getLatitude() + location.getLongitude(), Toast.LENGTH_SHORT).show();
                                    complain_location.setText("Location ="+location.getLatitude()+location.getLongitude());

                                    lattitude = String.valueOf(location.getLatitude());
                                    langitude = String.valueOf(location.getLongitude());

                                }
                            }
                        });


                    } else {
                        Toast.makeText(view.getContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                } else {

                    requestPermissions();
                }
            }


            @SuppressLint("MissingPermission")
            private void requestNewLocationData() {


                com.google.android.gms.location.LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();

                mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
                mLocationRequest.setInterval(5);
                mLocationRequest.setFastestInterval(0);
                mLocationRequest.setNumUpdates(1);


                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            }

            private LocationCallback mLocationCallback = new LocationCallback() {

                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location mLastLocation = locationResult.getLastLocation();
                    Toast.makeText(view.getContext(), "Longitude" + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            };

            private boolean checkPermissions() {
                return ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            }

            private void requestPermissions() {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
            }

            private boolean isLocationEnabled() {
                LocationManager locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
                return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            }


            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                AddComplaint.super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                if (requestCode == PERMISSION_ID) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getLastLocation();
                    }
                }
            }

            public void onResume() {

                AddComplaint.super.onResume();
                if (checkPermissions()) {
                    getLastLocation();
                }
            }
        });
        return view;

    }
    private void setId() {
        calender = view.findViewById(R.id.calender);
        complain_name = view.findViewById(R.id.complain_name);
        complain_contact_no = view.findViewById(R.id.complain_contact_no);
        incident_date = view.findViewById(R.id.incident_date);
        complain_Againsts = view.findViewById(R.id.complain_Against);
        complain_location = view.findViewById(R.id.complain_location);
        complain_description = view.findViewById(R.id.complain_description);
        incident_date = view.findViewById(R.id.incident_date);
        incident_addresss = view.findViewById(R.id.incident_address);
        station_id = view.findViewById(R.id.complain_station_id);
        complain_Type = view.findViewById(R.id.complain_Type);
        submit = view.findViewById(R.id.submit);
    }

    private void loadcomplaintype() {
        StringRequest request = new StringRequest(Request.Method.POST,URL_complain, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getContext(), "response = "+response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("response"+response.toString());;
                list_complain= new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer sta = Integer.valueOf(jsonObject.getString("complaint_type_id"));
                        String cml = jsonObject.getString("complaint_type_name");
//                        list_complain.add(String.valueOf(sta));
                        list_complain.add(cml);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "error json "+e.toString(), Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
                adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list_complain);
                adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
                 complain_Type.setAdapter(adapter2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        requestQueue1 = Volley.newRequestQueue(getContext());
        requestQueue1.add(request);
    }
    private void loadSattion() {
            StringRequest request = new StringRequest(Request.Method.POST, STATION_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   // Toast.makeText(getContext(), "response = "+response.toString(), Toast.LENGTH_SHORT).show();
                    list = new ArrayList<>();
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String sta = jsonObject.getString("police_station_name");
                            list.add(sta);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "error json "+e.toString(), Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                    adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    station_id.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return super.getParams();
                }
            };
            requestQueue1 = Volley.newRequestQueue(getContext());
            requestQueue1.add(request);
        }


    private void registratin() {


    }

    private void showPositiveDialogue(String id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Success")
                .setMessage("Data Saved Succesfully \n Complain ID is "+id)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    private void showDatePickerDialog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // Handle the date selection or update the TextView
                        String dateOfBirth = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        incident_date.setText(dateOfBirth);
                    }
                },
                year,
                month,
                day);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}