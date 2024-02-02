package com.ltrsoft.rajashtanuserapplication.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintType;
import com.ltrsoft.rajashtanuserapplication.classes.Station;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.model.CrimeTypeDeo;
import com.ltrsoft.rajashtanuserapplication.model.StationDeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AddComplaint extends Fragment {
    private FusedLocationProviderClient mFusedLocationClient;
    private ImageView calender;
    private com.google.android.gms.location.LocationRequest mLocationRequest;
    public EditText complain_name,complain_contact_no,incident_date,complain_Againsts,complain_description,incident_addresss;
    private Spinner station_id,complain_Type;
    Button submit,complain_location;
    private Location location ;
    String lang;
    private boolean flag = false;
    int PERMISSION_ID = 44;
    ArrayList <ComplaintType>list_complain;
    ArrayList <Station>list;
     private ArrayAdapter adapter,adapter2;
    String lattitude = "", langitude = "",statinId,CrimeTypeId;
    String complaint_type_id;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private static final int REQUEST_CODE_FOR_DESC = 2;
    private static final int REQUEST_CODE_FOR_AGAINST = 3;
    private static final int REQUEST_CODE_FOR_ADRESS = 4;
    private static final int REQUEST_CODE_FOR_CONTACTNO = 5;
    View view;
    public AddComplaint() {}
    HashMap<Integer , String> hashMap = new HashMap();
    HashMap<Integer , String> hashMap2 = new HashMap();
    public ImageView mike,descmike,againstmike,adressmike,contactnomike;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.add_complaint, container, false);
       setId();

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {actionBar.setTitle(" Add Complaint");}
        loadSattion();
        loadcomplaintype();

        complain_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CrimeTypeId = hashMap2.get(i);
//                Toast.makeText(getContext(), "crime type "+CrimeTypeId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                CrimeTypeId = hashMap2.get(0);
            }
        });

        station_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statinId = hashMap.get(i);
//                Toast.makeText(getContext(), "station Id"+statinId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                statinId = hashMap.get(0);
            }
        });
        mike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi-IN");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast.makeText(getContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        againstmike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_FOR_AGAINST);
                } catch (Exception e) {
                    Toast.makeText(getContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        descmike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
                try {
                    startActivityForResult(intent, REQUEST_CODE_FOR_DESC);
                } catch (Exception e) {
                    Toast.makeText(getContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        adressmike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_FOR_ADRESS);
                } catch (Exception e) {
                    Toast.makeText(getContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        contactnomike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_FOR_CONTACTNO);
                } catch (Exception e) {
                    Toast.makeText(getContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDataAccess userDataAccess = new UserDataAccess();

                if (!complain_name.getText().toString().isEmpty()) {
                    if (!incident_addresss.getText().toString().isEmpty()) {
                        if (!complain_description.getText().toString().isEmpty()){
                            if (!complain_Againsts.getText().toString().isEmpty()){
                                if (!incident_addresss.getText().toString().isEmpty()) {
                                        if (flag){
                                            String complain_desc= complain_description.getText().toString().trim();
                                             String incident_dates=incident_date.getText().toString().trim();
                                             String complaint_subject=complain_name.getText().toString().trim();
                                             String incident_address=incident_addresss.getText().toString().trim();
                                             String complain_Against=complain_Againsts.getText().toString().trim();
                                            String lattitude=String.valueOf(location.getLatitude());
                                            String longitude=String.valueOf(location.getLongitude());
                                            Toast.makeText(getContext(), "all data is valid", Toast.LENGTH_SHORT).show();

                                            ComplaintClass complaintClass = new ComplaintClass(complain_Against,statinId,lattitude,longitude,complaint_subject
                                                    ,complain_desc,incident_dates,incident_address,userDataAccess.getUserId(getActivity()).toString(),complaint_type_id,"");
//                                            ComplaintClass complaintClass = new ComplaintClass(complain_Against, statinId, lattitude, longitude, complaint_subject, complain_desc,
//                                                    incident_dates, incident_address, userDataAccess.getUserId(getActivity()).toString(),complaint_type_id,"");

                                            Complaintdeo complaintdeo = new Complaintdeo();
                                            complaintdeo.createComplain(complaintClass, getContext(), new UserCallBack() {
                                                @Override
                                                public void userSuccess(Object object) {
                                                    String success = (String) object;
                                                    Toast.makeText(getContext(), "success" + success.toString(), Toast.LENGTH_SHORT).show();
                                                    showPositiveDialogue(success.toString());
                                                }

                                                @Override
                                                public void userError(String error) {
                                                    Toast.makeText(getContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                        else {
                                            Toast.makeText(getContext(), "location is empty", Toast.LENGTH_SHORT).show();
                                        }
                                } else {
                                    Toast.makeText(getContext(), "incident_dates is empty", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getContext(), "complain_Against is empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getContext(), "complain_desc is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "incident_address is empty", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "complaint_subject is empty", Toast.LENGTH_SHORT).show();
                }
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
                                        flag=true;
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
        againstmike = view.findViewById(R.id.compagainstmike);
        descmike = view.findViewById(R.id.descmike);
        mike = view.findViewById(R.id.mike);
        adressmike = view.findViewById(R.id.adressmike);
        contactnomike = view.findViewById(R.id.contactmike);


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
        CrimeTypeDeo crimeTypeDeo = new CrimeTypeDeo();
        crimeTypeDeo.getAllCrimeType(getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                list_complain = (ArrayList<ComplaintType>)object;
                ArrayList<String>arrayList = new ArrayList<>();
                for (int i = 0; i < list_complain.size(); i++) {
                    ComplaintType complaintType = list_complain.get(i);
                    String complaint_type_name = complaintType.getComplaint_type_name();
                    arrayList.add(complaint_type_name);
                    String complaint_type_id = complaintType.getComplaint_type_id();
                    hashMap2.put(i,complaint_type_id);
                }
                adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);
                adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
                complain_Type.setAdapter(adapter2);
            }
            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadSattion() {


        StationDeo deo = new StationDeo();
        deo.getAllStation(getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                list =(ArrayList<Station>)object;
                ArrayList<String>arrayList = new ArrayList<>();
                for (int i = 0; i <list.size(); i++) {
                    Station station = list.get(i);
                    hashMap.put(i,station.getStation_id());
                    arrayList.add(station.getStation_name());
                }
                adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                station_id.setAdapter(adapter);
            }

            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                complain_name.setText(Objects.requireNonNull(result).get(0));
            }

        } else if (requestCode == REQUEST_CODE_FOR_DESC){
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                complain_description.setText(Objects.requireNonNull(result).get(0));
            }

        }
        else if (requestCode == REQUEST_CODE_FOR_AGAINST){
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                complain_Againsts.setText(Objects.requireNonNull(result).get(0));
            }

        }
        else if (requestCode == REQUEST_CODE_FOR_ADRESS){
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                incident_addresss.setText(Objects.requireNonNull(result).get(0));
            }

        }
        else if (requestCode == REQUEST_CODE_FOR_CONTACTNO){
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                complain_contact_no.setText(Objects.requireNonNull(result).get(0));
            }

        }
    }
}