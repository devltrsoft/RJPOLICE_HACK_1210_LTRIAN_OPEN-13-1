package com.ltrsoft.rajashtanuserapplication.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends Fragment {
    public UpdateProfile() {}
    private ImageView back;
    private   RequestQueue queue;
    private Button submit;
    String genders;
    private EditText city,district,state,country,addhar,email,mobile,dob,address,fname,mname,lname;
    private static final String USER_PROFILE_READ_URL = "https://rj.ltr-soft.com/public/police_api/data/user_read.php";
    private static final String USER_PROFILE_UPDATE_URL = "https://rj.ltr-soft.com/public/police_api/data/user_update.php";
    private RadioGroup group;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_profile, container, false);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Update Profile ");
        }

        back = view.findViewById(R.id.back);
        submit = view.findViewById(R.id.submit_btn);
        group = view.findViewById(R.id.gender_id);

        city = view.findViewById(R.id.city);
        district = view.findViewById(R.id.district);
        state = view.findViewById(R.id.state);
        country = view.findViewById(R.id.country);
        addhar = view.findViewById(R.id.addhar);
        email = view.findViewById(R.id.email);
        mobile= view.findViewById(R.id.mobile);
        address = view.findViewById(R.id.address);
        dob = view.findViewById(R.id.dob);
        fname = view.findViewById(R.id.fname);
        mname = view.findViewById(R.id.mname);
        lname = view.findViewById(R.id.lname);
        queue= Volley.newRequestQueue(getContext());
        loaddata();
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rmale){
                    genders="male";
                }
                else {
                    genders="female";
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = new Profile();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.containermain, profile).commit();
            }
        });

        return view;
    }

    private void updateData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_PROFILE_UPDATE_URL,
                response -> {
                    Toast.makeText(getContext(), "response = "+response.toString(), Toast.LENGTH_SHORT).show();
                    if (response.contains("success")){
                        Toast.makeText(getContext(), "succes", Toast.LENGTH_SHORT).show();
                        showSuccessDialogue();
                    }
                    else {
                        Toast.makeText(getContext(), "update failed", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Volley Error = "+error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("Error",""+error.toString());
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                UserDataAccess userDataAccess = new UserDataAccess();
                hashMap.put("user_id", userDataAccess.getUserId(getActivity()));

                hashMap.put("user_fname",fname.getText().toString());
                hashMap.put("user_mname",mname.getText().toString());
                hashMap.put("user_lname",lname.getText().toString());
                hashMap.put("user_address",address.getText().toString());
                hashMap.put("user_email",email.getText().toString());
                hashMap.put("user_gender",genders);
                hashMap.put("user_dob",dob.getText().toString());
                hashMap.put("user_mobile1",mobile.getText().toString());
                hashMap.put("user_adhar",addhar.getText().toString());
                hashMap.put("country_id","1");
                hashMap.put("state_id","1");
                hashMap.put("district_id","1");
                hashMap.put("city_id","1");
                return hashMap;
            }
        };
        queue.add(stringRequest);
    }

    private void showSuccessDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Success")
                .setMessage("Data Saved Succesfully")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.containermain, new Profile()).commit();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void loaddata() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_PROFILE_READ_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response = "+response.toString());
                        if (response!=null){
                            //Toast.makeText(getContext(), "response = "+response.toString(), Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0 ; i < jsonArray.length() ; i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String user_fname = jsonObject.getString("user_fname");
                                    String user_mname = jsonObject.getString("user_mname");
                                    String user_lname = jsonObject.getString("user_lname");
                                    String user_address = jsonObject.getString("user_address");
                                    //String user_photo = jsonObject.getString("user_fname");
                                    String country_id = jsonObject.getString("country_name");
                                    String state_id = jsonObject.getString("state_name");
                                    String user_email = jsonObject.getString("user_email");
                                    String user_gender = jsonObject.getString("user_gender");
                                    String user_dob = jsonObject.getString("user_dob");
                                    String user_mobile1 = jsonObject.getString("user_mobile1");
                                    String user_adhar = jsonObject.getString("user_adhar");
                                    String city_id = jsonObject.getString("city_name");
                                    String district_id = jsonObject.getString("district_name");

                                    state.setText(state_id);
                                    city.setText(city_id);
                                    district.setText(district_id);
                                    country.setText(country_id);
                                    addhar.setText(user_adhar);
                                    email.setText(user_email);
                                    mobile.setText(user_mobile1);
                                    if (user_gender.equalsIgnoreCase("male")) {
                                        group.check(R.id.rmale);
                                    }else {
                                        group.check(R.id.rfemale);
                                    }
                                    dob.setText(user_dob);
                                    address.setText(user_address);
                                    fname.setText(user_fname);
                                    mname.setText(user_mname);
                                    lname.setText(user_lname);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getContext(), "json error"+e.toString(), Toast.LENGTH_SHORT).show();
                                throw new RuntimeException(e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
               UserDataAccess userDataAccess = new UserDataAccess();
                hashMap.put("user_id", userDataAccess.getUserId(getActivity()));
                return hashMap;
            }
        };
        queue.add(stringRequest);

    }
}