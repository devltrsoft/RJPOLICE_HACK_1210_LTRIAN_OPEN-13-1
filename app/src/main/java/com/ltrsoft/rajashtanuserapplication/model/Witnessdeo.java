package com.ltrsoft.rajashtanuserapplication.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.EvidenceClass;
import com.ltrsoft.rajashtanuserapplication.classes.User;
import com.ltrsoft.rajashtanuserapplication.classes.VictimClass;
import com.ltrsoft.rajashtanuserapplication.classes.WitnessClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Witnessdeo {

    private static String URL = "https://rj.ltr-soft.com/public/police_api/data/complaint_suspect_insert.php";

    public ArrayList< WitnessClass> list = new ArrayList<>();
    public WitnessClass witnessClass;
    public String  resp;
    public  void createWitness(WitnessClass witnessClass , Context context  , UserCallBack userCallBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            userCallBack.userSuccess(response.toString());
                        } else {
                            userCallBack.userError("insertion failed"+response.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userCallBack.userError(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("complaint_suspect_fname", witnessClass.getComplaint_witness_fname());
                param.put("complaint_suspect_lname", witnessClass.getComplaint_witness_lname());
                param.put("complaint_suspect_mname", witnessClass.getComplaint_witness_lname());
                param.put("country", "1");
                param.put("state", "1");
                param.put("district", "1");
                param.put("city", "1");
                param.put("dob", witnessClass.getComplaint_witness_dob());
                param.put("mobile", witnessClass.getComplaint_witness_mobile());
                param.put("gender", witnessClass.getComplaint_witness_gender());
                param.put("complaint_id", witnessClass.getComplaint_id());
                param.put("photo", witnessClass.getComplaint_witness_photo_path());
                param.put("address",witnessClass.getComplaint_witness_address());
                param.put("aadhar", witnessClass.getComplaint_witness_adhar());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void getAllWitness(String complaintId , Context context , UserCallBack userCallBack){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String complaint_witness_id = jsonObject.getString("complaint_witness_id");
                                String complaint_witness_fname = jsonObject.getString("complaint_witness_fname");
                                String complaint_witness_mname = jsonObject.getString("complaint_witness_mname");
                                String complaint_witness_lname = jsonObject.getString("complaint_witness_lname");
                                String complaint_witness_address = jsonObject.getString("complaint_witness_address");
                                String city_name = jsonObject.getString("city_name");
                                String country_name = jsonObject.getString("country_name");
                                String state_name = jsonObject.getString("state_name");
                                String district_name = jsonObject.getString("district_name");
                                String complaint_witness_dob = jsonObject.getString("complaint_witness_dob");
                                String complaint_witness_gender = jsonObject.getString("complaint_witness_gender");
                                String complaint_witness_mobile = jsonObject.getString("complaint_witness_mobile");
                                String complaint_witness_email = jsonObject.getString("complaint_witness_email");
                                String complaint_witness_photo_path = jsonObject.getString("complaint_witness_photo_path");
                                String complaint_witness_pan = jsonObject.getString("complaint_witness_pan");
                                String complaint_witness_adhar = jsonObject.getString("complaint_witness_adhar");
                                String complaint_id = jsonObject.getString("complaint_id");
                                String isWitness = jsonObject.getString("isWitness");
                                list = new ArrayList<>();

                                list.add(new WitnessClass(complaint_witness_id,complaint_witness_fname,complaint_witness_mname,complaint_witness_lname,complaint_witness_address,city_name,country_name,state_name,district_name
                                ,complaint_witness_dob,complaint_witness_gender,complaint_witness_mobile,complaint_witness_email,complaint_witness_photo_path,complaint_witness_pan,complaint_witness_adhar,complaint_id,isWitness));


                            } catch (JSONException e) {
                                userCallBack.userError(e.toString());
                                e.printStackTrace();
                            }
                        }
                        userCallBack.userSuccess(list);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userCallBack.userError(error.toString());
                        error.printStackTrace();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("complaint_photo_id",complaintId);
                return hashMap;
            }
        };

    }


    public WitnessClass getWitness(String witid){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String complaint_witness_id = jsonObject.getString("complaint_witness_id");
                                String complaint_witness_fname = jsonObject.getString("complaint_witness_fname");
                                String complaint_witness_mname = jsonObject.getString("complaint_witness_mname");
                                String complaint_witness_lname = jsonObject.getString("complaint_witness_lname");
                                String complaint_witness_address = jsonObject.getString("complaint_witness_address");
                                String city_name = jsonObject.getString("city_name");
                                String country_name = jsonObject.getString("country_name");
                                String state_name = jsonObject.getString("state_name");
                                String district_name = jsonObject.getString("district_name");
                                String complaint_witness_dob = jsonObject.getString("complaint_witness_dob");
                                String complaint_witness_gender = jsonObject.getString("complaint_witness_gender");
                                String complaint_witness_mobile = jsonObject.getString("complaint_witness_mobile");
                                String complaint_witness_email = jsonObject.getString("complaint_witness_email");
                                String complaint_witness_photo_path = jsonObject.getString("complaint_witness_photo_path");
                                String complaint_witness_pan = jsonObject.getString("complaint_witness_pan");
                                String complaint_witness_adhar = jsonObject.getString("complaint_witness_adhar");
                                String complaint_id = jsonObject.getString("complaint_id");
                                String isWitness = jsonObject.getString("isWitness");
                                list = new ArrayList<>();

                                witnessClass = new WitnessClass(complaint_witness_id,complaint_witness_fname,complaint_witness_mname,complaint_witness_lname,complaint_witness_address,city_name,country_name,state_name,district_name
                                        ,complaint_witness_dob,complaint_witness_gender,complaint_witness_mobile,complaint_witness_email,complaint_witness_photo_path,complaint_witness_pan,complaint_witness_adhar,complaint_id,isWitness);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        error.printStackTrace();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("witness_id",witid);
                return hashMap;
            }
        };
        return witnessClass;

    }

    public String UpdateWitness(WitnessClass witnessClass , Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("success")) {
                            resp = response;
                        } else {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();


                param.put("complaint_witness_fname", witnessClass.getComplaint_witness_fname());
                param.put("country_id", "1");
                param.put("state_id", "1");
                param.put("district_id", "1");
                param.put("city_id", "1");
                param.put("complaint_witness_email", witnessClass.getComplaint_witness_email());
                param.put("complaint_witness_dob", witnessClass.getComplaint_witness_dob());
                param.put("complaint_witness_mobile", witnessClass.getComplaint_witness_mobile());
                param.put("complaint_witness_gender", witnessClass.getComplaint_witness_gender());
                param.put("complaint_id",witnessClass.getComplaint_id());
                param.put("complaint_witness_photo",  witnessClass.getComplaint_witness_photo_path());
                param.put("complaint_witness_adhar", witnessClass.getComplaint_witness_adhar());

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return resp;
    }


}
