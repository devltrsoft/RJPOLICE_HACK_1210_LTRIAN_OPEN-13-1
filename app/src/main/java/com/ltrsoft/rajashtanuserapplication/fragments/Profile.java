package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends Fragment {
    public Profile() {}
    private ImageView back,edit;
    public ArrayAdapter adapter,adapter2,adapter3,adapter4;
    ArrayList<String> list=new ArrayList<>();
    ArrayList <String> listofstate=new ArrayList<>();
    ArrayList <String> listofdistrict=new ArrayList<>();
    ArrayList <String> listofcity=new ArrayList<>();
    HashMap<Integer,String> countrycode=new HashMap<>();
    HashMap <Integer,String> statecode=new HashMap<>();
    HashMap <Integer,String> districtcode=new HashMap<>();
    HashMap <Integer,String> citycode=new HashMap<>();

    public static  final String URL1 ="https://rj.ltr-soft.com/public/police_api/country/select_country.php ";
    public static  final String URL2 ="https://rj.ltr-soft.com/public/police_api/state/select_state.php";
    public static  final String URL3 ="https://rj.ltr-soft.com/public/police_api/district/select_district.php";
    public static  final String URL4 ="https://rj.ltr-soft.com/public/police_api/city/select_city.php";
    private    RequestQueue queue;
    private TextView city,district,state,country,addhar,email,mobile,gender,dob,address,fname,mname,lname;
    private static final String USER_PROFILE_READ_URL = "https://rj.ltr-soft.com/public/police_api/data/user_read.php";
    private static final String USER_PROFILE_UPDATE_URL = "https://rj.ltr-soft.com/public/police_api/data/user_read.php";

    StringBuilder output = new StringBuilder();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        queue = Volley.newRequestQueue(getContext());
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Profile ");}
        edit = view.findViewById(R.id.edit);
        back = view.findViewById(R.id.back);
        city = view.findViewById(R.id.city);
        district = view.findViewById(R.id.district);
        state = view.findViewById(R.id.state);
        country = view.findViewById(R.id.country);
        addhar = view.findViewById(R.id.addhar);
        email = view.findViewById(R.id.email);
        mobile= view.findViewById(R.id.mobile);
        gender = view.findViewById(R.id.gender);
        address = view.findViewById(R.id.address);
        dob = view.findViewById(R.id.dob);
        fname = view.findViewById(R.id.fname);
        mname = view.findViewById(R.id.mname);
        lname = view.findViewById(R.id.lname);
        loadData();



        try {
            FileInputStream fileInputStream = getActivity().openFileInput("mytextfile.txt");
            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
            char[] buffer = new char[1024];
            int read;
            while ((read = inputReader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
          //  Toast.makeText(getContext(), "your fiile ="+output.toString(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProfile profile = new UpdateProfile();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.containermain, profile).commit();
            }
        });

        return view;
    }

       private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_PROFILE_READ_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //   System.out.println("response = "+response.toString());
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

                                    String city_id = jsonObject.getString("city_name");
                                    String district_id = jsonObject.getString("district_name");
                                    String country_id = jsonObject.getString("country_name");
                                    String state_id = jsonObject.getString("state_name");

                                    String user_email = jsonObject.getString("user_email");
                                    String user_gender = jsonObject.getString("user_gender");
                                    String user_dob = jsonObject.getString("user_dob");
                                    String user_mobile1 = jsonObject.getString("user_mobile1");
                                    String user_adhar = jsonObject.getString("user_adhar");


                                    state.setText(state_id);
                                    city.setText(city_id);
                                    district.setText(district_id);
                                    country.setText(country_id);
                                    addhar.setText(user_adhar);
                                    email.setText(user_email);
                                    mobile.setText(user_mobile1);
                                    gender.setText(user_gender);
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
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                UserDataAccess userDataAccess = new UserDataAccess();
                hashMap.put("user_id", userDataAccess.getUserId(getActivity()));
                return hashMap;

        }
        };
        queue.add(stringRequest);
    }
}