package com.ltrsoft.rajashtanuserapplication.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.VictimClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.model.Victimdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddVictim extends Fragment {
    private final int CAMERA_REQ_CODE = 104;
    private final int GALLERY_REQ_CODE = 105;

    private EditText name,address,contact,dob,email,addhar;
    private RadioGroup gender;
    private String encodeImage="";
    private   ArrayList<String> listcomplain;
    static  String COMPLAIN_NAME_EBY_USER1 = "https://rj.ltr-soft.com/public/police_api/data/complaint_user_read.php";

    private RadioButton male,female;
    private Button save,upload;
    private Spinner country,state,district,city,complainname;
    private ImageView photo,back_image;
    public HashMap<Integer,String>hashMap=new HashMap<>();

    public ArrayAdapter adapter,adapter2,adapter3,adapter4;
    private Bitmap bitmap;
    ArrayList<String> list=new ArrayList<>();
    ArrayList <String> listofstate=new ArrayList<>();
    ArrayList <String> listofdistrict=new ArrayList<>();
    ArrayList <String> listofcity=new ArrayList<>();
    HashMap<Integer,String> countrycode=new HashMap<>();
    HashMap <Integer,String> statecode=new HashMap<>();
    HashMap <Integer,String> districtcode=new HashMap<>();
    HashMap <Integer,String> citycode=new HashMap<>();
    String Complain_id;
    public static  final String URL1 ="https://rj.ltr-soft.com/public/police_api/country/select_country.php ";
    public static  final String URL2 ="https://rj.ltr-soft.com/public/police_api/state/select_state.php";
    public static  final String URL3 ="https://rj.ltr-soft.com/public/police_api/district/select_district.php";
    public static  final String URL4 ="https://rj.ltr-soft.com/public/police_api/city/select_city.php";

    private String URL = "https://rj.ltr-soft.com/public/police_api/data/create_complaint_victim.php";
    public AddVictim() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.add_victim, container, false);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {actionBar.setTitle(" Add Victim");}
        name=view.findViewById(R.id.fname);
        address=view.findViewById(R.id.address);
        contact=view.findViewById(R.id.contact);
        dob=view.findViewById(R.id.dob);
        email=view.findViewById(R.id.email);
        addhar=view.findViewById(R.id.addhar);
        male=view.findViewById(R.id.male);
        female=view.findViewById(R.id.female);
        save=view.findViewById(R.id.save);
        upload=view.findViewById(R.id.upload);
        country=view.findViewById(R.id.country);
        state=view.findViewById(R.id.state);
        district=view.findViewById(R.id.district);
        city=view.findViewById(R.id.city);
        photo=view.findViewById(R.id.photo);
        gender=view.findViewById(R.id.gender);
        back_image=view.findViewById(R.id.back_image);
        complainname = view.findViewById(R.id.complain_name);

        UserDataAccess access = new UserDataAccess();
        try {
            loadComplainNameByUser(access.getUserId(getActivity()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        complainname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Complain_id = hashMap.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Complain_id = hashMap.get(0);
            }
        });
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.session_container, new DashBoard())
                        .commit();
            }
        });

        RequestQueue queue;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("country_name"); // Assuming "name" is the key in your JSON response
                                String country_code2 = jsonObject.getString("country_id"); // Assuming "name" is the key in your JSON response
                                list.add(name);
                                countrycode.put(i,country_code2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        country.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error = "+error.toString(), Toast.LENGTH_SHORT).show();
                // Handle any error

            }
        }
        );
        queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonArrayRequest);
//          adapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1,list);
//          spinner.setAdapter(adapter);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listofstate.clear();


                if (i==0) {
                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(MainActivity.this, "response = "+response.toString(), Toast.LENGTH_SHORT).show();
//                            System.out.println("response = "+response.toString());
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String state_name = jsonObject.getString("state_name");
                                    String state_id = jsonObject.getString("state_id");
                                    listofstate.add(state_name);
                                    statecode.put(i, state_id);
                                }
                                adapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, listofstate);
                                adapter2.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                                state.setAdapter(adapter2);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "erro" + error.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println("erro=" + error.toString());
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<>();
                            //  hashMap.put("country_id", "country-75");
                            hashMap.put("country_id","1");
                            return hashMap;
                        }
                    };
                    queue.add(stringRequest1);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(), "nothing selected = ", Toast.LENGTH_SHORT).show();
            }
        });
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listofdistrict.clear();

                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //    Toast.makeText(MainActivity.this, "response = "+response.toString(), Toast.LENGTH_SHORT).show();
                        //  System.out.println("response = "+response.toString());
                        String district_name;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            //   Toast.makeText(MainActivity.this, "statecode = "+statecode.get(i), Toast.LENGTH_SHORT).show();
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                district_name = jsonObject.getString("district_name");
                                String district_id = jsonObject.getString("district_id");
                                listofdistrict.add(district_name);
                                districtcode.put(i, district_id);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // System.out.println("districynam == "+district_name);
                        adapter3 = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, listofdistrict);
                        adapter3.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                        district.setAdapter(adapter3);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "error = "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<>();
                        // hashMap.put("state_id", "state-12");
                        // hashMap.put("state_id",statecode.get(i));
                        hashMap.put("state_id","1");
                        return hashMap;
                    }
                };
                queue.add(stringRequest2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getActivity(), "you selcted ="+listofdistrict.get(i), Toast.LENGTH_SHORT).show();
                listofcity.clear();

                StringRequest stringRequest3 = new StringRequest(Request.Method.POST, URL4, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  System.out.println("response = "+response.toString());
                        String city_name;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            //   Toast.makeText(MainActivity.this, "statecode = "+statecode.get(i), Toast.LENGTH_SHORT).show();
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                city_name = jsonObject.getString("city_name");
                                String city_id = jsonObject.getString("city_id");
                                listofcity.add(city_name);
                                citycode.put(i, city_id);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        // System.out.println("districynam == "+district_name);
                        adapter4 = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, listofcity);
                        adapter4.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                        city.setAdapter(adapter4);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "error = "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<>();
                        // hashMap.put("state_id", "state-12");
                        // hashMap.put("state_id",statecode.get(i));
                        hashMap.put("district_id","1");
                        return hashMap;
                    }
                };
                queue.add(stringRequest3);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "you selcted ="+listofcity.get(i), Toast.LENGTH_SHORT).show();
                citycode.put(i,listofcity.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        return view;
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
                        dob.setText(dateOfBirth);
                    }
                },
                year,
                month,
                day);

        // Show the date picker dialog
        datePickerDialog.show();
    }

    private void openCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Select")
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive button click
                        openGallery();
                    }
                }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Positive button click
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // Request CAMERA permission
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQ_CODE);
                        } else {
                            // CAMERA permission already granted, launch camera
                            launchCamera();
                        }
                    }



                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQ_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                bitmap = (Bitmap) extras.get("data");
                photo.setImageBitmap(bitmap);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                     encodeImage = Base64.encodeToString(bytes, Base64.NO_WRAP);
                    //
                }
                else {
             //       Toast.makeText(getContext(), "please select the image", Toast.LENGTH_SHORT).show();

                }
            }
        } else if(requestCode == GALLERY_REQ_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            Toast.makeText(getActivity(), ""+selectedImage, Toast.LENGTH_SHORT).show();
            InputStream inputStream = null;
            try {
                inputStream = getContext().getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            bitmap = BitmapFactory.decodeStream(inputStream);
            // Display the selected image in ImageView

            photo.setImageURI(selectedImage);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            encodeImage = Base64.encodeToString(bytes, Base64.DEFAULT);

        }
    }
    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQ_CODE);
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQ_CODE);
    }
    private void save() {

        if (!name.getText().toString().isEmpty()){
            if (!address.getText().toString().isEmpty()){
                if (gender.getCheckedRadioButtonId()!=-1){
                    if (!contact.getText().toString().isEmpty()){
                        if (!dob.getText().toString().isEmpty()){
                            if (!email.getText().toString().isEmpty()){
                                if (!addhar.getText().toString().isEmpty()){
                                    if (!encodeImage.isEmpty()){
                                        Toast.makeText(getContext(), "All data id valid", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "please select gender", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
            }        }
        else {
            Toast.makeText(getContext(), "please enter name", Toast.LENGTH_SHORT).show();
        }



//        final String name1 = this.name.getText().toString().trim();
//        final String address1=this.address.getText().toString().trim();
//        final String country1 = this.country.toString().trim();
//        final String state1 = this.state.toString().trim();
//        final String district1 = this.district.toString().trim();
//        final String city1 = this.city.toString().trim();
//        final String email1 = this.email.getText().toString().trim();
//        final String dob1 = this.dob.getText().toString().trim();
//        final String mobile1 = this.contact.getText().toString().trim();
//        final String addhar1 = this.addhar.getText().toString().trim();
//        String gender = male.isChecked() ? "Male" : "Female";
//
//
//        VictimClass victimClass = new VictimClass(complainname.getSelectedItem().toString(),name1,name1,name1,
//                address1,gender,addhar1,encodeImage,dob1,mobile1,state1,district1,country1);
//        Victimdeo victimdeo = new Victimdeo();
//        victimdeo.create(victimClass, getContext(), new UserCallBack() {
//            @Override
//            public void userSuccess(Object object) {
//                Toast.makeText(getContext(), "response"+(String) object, Toast.LENGTH_SHORT).show();
//                showPositiveDialogue();
//            }
//
//            @Override
//            public void userError(String error) {
//                Toast.makeText(getContext(), "response"+error, Toast.LENGTH_SHORT).show();
//                showNagativeDiaogue();
//            }
//        });

    }
    private void loadComplainNameByUser(String userId) throws JSONException {
        Complaintdeo complaintdeo = new Complaintdeo();
        complaintdeo.getUserAllComplaint(new UserDataAccess().getUserId(getActivity()).toString(), getContext()
                , new UserCallBack() {
                    @Override
                    public void userSuccess(Object object) {
                        listcomplain = new ArrayList<>();
                        ArrayList<ComplaintClass>list1 = (ArrayList<ComplaintClass>) object;
                        for (int i = 0; i < list1.size() ; i ++){
                            ComplaintClass complaintClass = list1.get(i);
                            listcomplain.add(complaintClass.getComplaint_subject()+complaintClass.getComplaint_description());
                            hashMap.put(i,(complaintClass.getComplaint_id()));
                        }
                        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1,listcomplain);
                        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                        complainname.setAdapter(adapter);
                    }
                    @Override
                    public void userError(String error) {
                        Toast.makeText(getContext(), "Error while loading complain", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void showNagativeDiaogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Failed")
                .setMessage("Something Goes Wroong ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showPositiveDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Success")
                .setMessage("Data Saved Succesfully ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}