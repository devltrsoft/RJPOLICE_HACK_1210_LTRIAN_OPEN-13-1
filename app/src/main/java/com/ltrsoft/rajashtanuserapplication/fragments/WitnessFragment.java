package com.ltrsoft.rajashtanuserapplication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.Adapter.WitnessAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.WitnessClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Witnessdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WitnessFragment extends Fragment {
    RecyclerView recyclerView;
    private SearchView searchView;
    TextView t;
    WitnessAdapter myAdapter;
    ArrayList<WitnessClass> witnessList;
    WitnessClass modelWitness;
    LinearLayoutManager manager;
    String date;
    String time;
    StringBuilder complainId = new StringBuilder();
    String url = "https://rj.ltr-soft.com/public/police_api/data/complaint_witness_read.php"; // Change the URL

    public WitnessFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_witness_page, container, false); // Change to your witness fragment layout
        recyclerView = v.findViewById(R.id.wrecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        try {
//            FileInputStream fileInputStream = requireContext().openFileInput("complain.txt");
//            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
//            char[] buffer = new char[1024];
//            int read;
//            while ((read = inputReader.read(buffer)) > 0) {
//                complainId.append(buffer, 0, read);
//            }
//
//          //  Toast.makeText(getContext(), "Complaint ID: " + complainId.toString(), Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//         //   Toast.makeText(getContext(), "Error reading Complaint ID", Toast.LENGTH_SHORT).show();
//            Log.d("FIE ERROR ",e.toString());
//        }
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray json = new JSONArray(response);
//                    for (int i = 0; i < json.length(); i++) {
//                        JSONObject jsonObject = json.getJSONObject(i);
//                        String complaint_witness_fname = jsonObject.getString("complaint_witness_fname");
//                        String complaint_witness_mname = jsonObject.getString("complaint_witness_mname");
//                        String complaint_witness_lname = jsonObject.getString("complaint_witness_fname");
//                        String complaint_witness_dob = jsonObject.getString("complaint_witness_dob");
//                        String complaint_witness_gender=jsonObject.getString("complaint_witness_gender");
//                        String complaint_witness_mobile=jsonObject.getString("complaint_witness_mobile");
//                        String complaint_witness_address=jsonObject.getString("complaint_witness_address");
//                        String complaint_witness_email=jsonObject.getString("complaint_witness_email");
//                        String complaint_witness_photo_path=jsonObject.getString("complaint_witness_photo_path");
//                        String complaint_witness_adhar=jsonObject.getString("complaint_witness_adhar");
//
//                        String country_name=jsonObject.getString("country_name");
//                        String state_name=jsonObject.getString("state_name");
//                        String district_name=jsonObject.getString("district_name");
//                        String city_name=jsonObject.getString("city_name");
//
//                        complaint_witness_photo_path = "https://rj.ltr-soft.com/public/police_api/inputfiles/abc.jpg" + complaint_witness_photo_path;
//
//                        witnessList.add(new WitnessClass(complaint_witness_fname,complaint_witness_mname,complaint_witness_lname,
//                                complaint_witness_address,city_name,country_name,state_name,district_name,complaint_witness_dob
//                        ,complaint_witness_gender,complaint_witness_mobile,complaint_witness_email,complaint_witness_photo_path
//                        ,complaint_witness_adhar));
//                    }
//                    myAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error: " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        queue.add(request);


        Witnessdeo witnessdeo = new Witnessdeo();
        UserDataAccess access = new UserDataAccess();
        witnessdeo.getAllWitness(access.getUserId(getActivity()), getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                witnessList = (ArrayList<WitnessClass>)object;
                myAdapter = new WitnessAdapter(witnessList); // Change to WitnessAdapter
                LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter);
            }
            @Override
            public void userError(String error) {
                System.out.println("error"+error.toString());
                Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }
}
