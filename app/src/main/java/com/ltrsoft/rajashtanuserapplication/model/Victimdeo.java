package com.ltrsoft.rajashtanuserapplication.model;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.VictimClass;
import com.ltrsoft.rajashtanuserapplication.classes.WitnessClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Victimdeo {
    public final static String URL2 = "";
    public VictimClass victimClass;
    public  String resp;

    public ArrayList<VictimClass> list = new ArrayList<>();
    private String URL = "https://rj.ltr-soft.com/public/police_api/data/create_complaint_victim.php";
    public final static String VICTIM_URL = "https://rj.ltr-soft.com/public/police_api/data/read_complaint_victim.php";


    public  void create(VictimClass victimClass , Context context  , UserCallBack userCallBack){
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
                param.put("complaint_victim_fname", victimClass.getComplaint_victim_fname());
                param.put("complaint_victim_mname", victimClass.getComplaint_victim_mname());
                param.put("complaint_victim_lname", victimClass.getComplaint_victim_lname());
                param.put("country", "1");
                param.put("state", "1");
                param.put("district", "1");
                param.put("city", "1");
                param.put("dob", victimClass.getDob());
                param.put("mobile", victimClass.getMobile());
                param.put("gender", victimClass.getGender());
                param.put("complaint_id", victimClass.getCmp_id());
                param.put("photo", victimClass.getPhoto());
                param.put("address",victimClass.getAddress());
                param.put("aadhar", victimClass.getAadhar());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void getAllVVictim(String complaintId, Context context , UserCallBack userCallBack){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, VICTIM_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String cmp_id = jsonObject.getString("cmp_id");
                                String complaint_victim_fname = jsonObject.getString("complaint_victim_fname");
                                String complaint_victim_mname = jsonObject.getString("complaint_victim_mname");
                                String complaint_victim_lname = jsonObject.getString("complaint_victim_lname");
                                String address = jsonObject.getString("address");
                                String gender = jsonObject.getString("gender");
                                String aadhar = jsonObject.getString("aadhar");
                                String photo = jsonObject.getString("photo");
                                String dob = jsonObject.getString("dob");
                                String mobile = jsonObject.getString("mobile");
                                String state_name = jsonObject.getString("state_name");
                                String district_name = jsonObject.getString("district_name");
                                String city_name = jsonObject.getString("city_name");
                                list.add(new VictimClass(cmp_id,complaint_victim_fname,complaint_victim_mname,complaint_victim_lname,address,gender,aadhar,
                                        photo,dob,mobile,state_name,district_name,city_name));
                                    userCallBack.userSuccess(list);
                            } catch (JSONException e) {
                                userCallBack.userError(e.toString());
                                e.printStackTrace();
                            }
                        }
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

    public VictimClass getVictim(String victimId){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);
                                String cmp_id = jsonObject.getString("cmp_id");
                                String complaint_victim_fname = jsonObject.getString("complaint_victim_fname");
                                String complaint_victim_mname = jsonObject.getString("complaint_victim_mname");
                                String complaint_victim_lname = jsonObject.getString("complaint_victim_lname");
                                String address = jsonObject.getString("address");
                                String gender = jsonObject.getString("gender");
                                String aadhar = jsonObject.getString("aadhar");
                                String photo = jsonObject.getString("photo");
                                String dob = jsonObject.getString("dob");
                                String mobile = jsonObject.getString("mobile");
                                String state_name = jsonObject.getString("state_name");
                                String district_name = jsonObject.getString("district_name");
                                String city_name = jsonObject.getString("city_name");
                                list.add(new VictimClass(cmp_id,complaint_victim_fname,complaint_victim_mname,complaint_victim_lname,address,gender,aadhar,
                                        photo,dob,mobile,state_name,district_name,city_name));
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
                hashMap.put("victim_id",victimId);
                return hashMap;
            }
        };
        return victimClass;

    }

    public String UpdateWitness(VictimClass witnessClass , Context context){
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
                param.put("complaint_victim_fname", victimClass.getComplaint_victim_fname());
                param.put("country", "1");
                param.put("state", "1");
                param.put("district", "1");
                param.put("city", "1");
                param.put("dob", victimClass.getDob());
                param.put("mobile", victimClass.getMobile());
                param.put("gender", victimClass.getGender());
                param.put("complaint_id", victimClass.getCmp_id());
                param.put("photo", victimClass.getPhoto());
                param.put("address",victimClass.getAddress());
                param.put("aadhar", victimClass.getAadhar());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return resp;
    }
}
