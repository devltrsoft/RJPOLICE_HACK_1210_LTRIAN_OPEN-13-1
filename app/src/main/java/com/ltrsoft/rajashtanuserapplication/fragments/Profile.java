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
import com.ltrsoft.rajashtanuserapplication.classes.User;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Userdeo;
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
           Userdeo userdeo = new Userdeo();
           UserDataAccess userDataAccess = new UserDataAccess();
           userdeo.getUser(userDataAccess.getUserId(getActivity()), getContext(), new UserCallBack() {
               @Override
               public void userSuccess(Object object) {
                   User user =  (User) object;
                   state.setText(user.getState_name());
                   city.setText(user.getCity_name());
                   district.setText(user.getDistrict_name());
                   country.setText(user.getCountry_name());
                   addhar.setText(user.getUser_adhar());
                   email.setText(user.getUser_email());
                   mobile.setText(user.getUser_mobile1());
                   gender.setText(user.getUser_gender());
                   dob.setText(user.getUser_dob());
                   address.setText(user.getUser_address());
                   fname.setText(user.getUser_fname());
                   mname.setText(user.getUser_mname());
                   lname.setText(user.getUser_lname());
               }
               @Override
               public void userError(String error) {
                   Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
               }
           });
    }
}