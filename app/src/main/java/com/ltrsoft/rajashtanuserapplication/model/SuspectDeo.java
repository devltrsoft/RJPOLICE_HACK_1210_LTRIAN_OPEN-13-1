package com.ltrsoft.rajashtanuserapplication.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.EvidenceClass;
import com.ltrsoft.rajashtanuserapplication.classes.SuspectClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuspectDeo {

    public ArrayList<SuspectClass>list = new ArrayList<>();
    public SuspectClass suspectClass ;
    public String resp;
    public final static String SUSPECT_URL = "https://rj.ltr-soft.com/public/police_api/data/complaint_suspect_read.php";

    private static String URL = "https://rj.ltr-soft.com/public/police_api/data/complaint_suspect_insert.php";
    public void createSuspect(SuspectClass suspectClass , Context context , Activity activity, UserCallBack userCallBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        if (response.contains("success")) {
                            userCallBack.userSuccess(response.toString());
                        } else {
                            userCallBack.userError(" failed to submit");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userCallBack.userError("error"+error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("complaint_id ", "1");
                param.put("complaint_suspect_fname", suspectClass.getFname());
                param.put("country_id","1");
                param.put("state_id", "1");
                param.put("city_id", "1");
                param.put("district_id", "1");
                param.put("complaint_suspect_email", suspectClass.getEmail());
                param.put("complaint_suspect_dob", suspectClass.getDob());
                param.put("complaint_suspect_mobile_no", suspectClass.getMobile());
                param.put("complaint_suspect_gender", suspectClass.getGender());
                param.put("complaint_suspect_adhar", suspectClass.getAdharno());
                param.put("complaint_suspect_photo", suspectClass.getPhotourl());
                param.put("complaint_suspect_address",suspectClass.getPhotourl());
                UserDataAccess userDataAccess  = new UserDataAccess();
                param.put("user_id", userDataAccess.getUserId(activity));
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void getAllSuspect(String complaintId, Context context,UserCallBack userCallBack){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, SUSPECT_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String complaint_suspect_fname = jsonObject.getString("complaint_suspect_fname");
                                String complaint_suspect_mname = jsonObject.getString("complaint_suspect_mname");
                                String complaint_suspect_lname = jsonObject.getString("complaint_suspect_lname");
                                String complaint_suspect_gender = jsonObject.getString("complaint_suspect_gender");
                                String complaint_suspect_mobile_no = jsonObject.getString("complaint_suspect_mobile_no");
                                String complaint_suspect_email = jsonObject.getString("complaint_suspect_email");
                                String complaint_suspect_adhar = jsonObject.getString("complaint_suspect_adhar");
                                String country_name = jsonObject.getString("country_name");
                                String city_name = jsonObject.getString("city_name");
                                String state_name = jsonObject.getString("state_name");
                                String district_name = jsonObject.getString("district_name");
                                String complaint_suspect_dob = jsonObject.getString("complaint_suspect_dob");
                                String isSuspect = jsonObject.getString("is_c_suspect");
                                String photourl = jsonObject.getString("complaint_suspect_photo_path");
                                int complaint_suspect_id = jsonObject.getInt("complaint_suspect_id");
                                int complaint_id = jsonObject.getInt("complaint_id");

                                list.add(new SuspectClass(complaint_suspect_fname,complaint_suspect_mname,complaint_suspect_lname,complaint_suspect_dob
                                        ,complaint_suspect_gender,complaint_suspect_mobile_no,complaint_suspect_email,complaint_suspect_adhar,
                                        country_name,state_name,district_name,city_name,isSuspect,photourl,complaint_suspect_id,complaint_id));
                                userCallBack.userSuccess(list);
                            }
                        } catch (JSONException e) {
                            userCallBack.userError(" "+e.toString());
                            throw new RuntimeException(e);
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
                hashMap.put("complaint_id",complaintId);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);

    }
    public SuspectClass getSuspect(String evidenceId, Context context){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, SUSPECT_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String complaint_suspect_fname = jsonObject.getString("complaint_suspect_fname");
                                String complaint_suspect_mname = jsonObject.getString("complaint_suspect_mname");
                                String complaint_suspect_lname = jsonObject.getString("complaint_suspect_lname");
                                String complaint_suspect_gender = jsonObject.getString("complaint_suspect_gender");
                                String complaint_suspect_mobile_no = jsonObject.getString("complaint_suspect_mobile_no");
                                String complaint_suspect_email = jsonObject.getString("complaint_suspect_email");
                                String complaint_suspect_adhar = jsonObject.getString("complaint_suspect_adhar");
                                String country_name = jsonObject.getString("country_name");
                                String city_name = jsonObject.getString("city_name");
                                String state_name = jsonObject.getString("state_name");
                                String district_name = jsonObject.getString("district_name");
                                String complaint_suspect_dob = jsonObject.getString("complaint_suspect_dob");
                                String isSuspect = jsonObject.getString("is_c_suspect");
                                String photourl = jsonObject.getString("complaint_suspect_photo_path");
                                int complaint_suspect_id = jsonObject.getInt("complaint_suspect_id");
                                int complaint_id = jsonObject.getInt("complaint_id");

                                list.add(new SuspectClass(complaint_suspect_fname,complaint_suspect_mname,complaint_suspect_lname,complaint_suspect_dob
                                        ,complaint_suspect_gender,complaint_suspect_mobile_no,complaint_suspect_email,complaint_suspect_adhar,
                                        country_name,state_name,district_name,city_name,isSuspect,photourl,complaint_suspect_id,complaint_id));
                            }
                        } catch (JSONException e) {
                            Log.d("json error",e.toString());
                            throw new RuntimeException(e);
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
                hashMap.put("complaint_id",evidenceId);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
        return suspectClass;

    }

    public String DeleteSuspect(String evidenceId , Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SUSPECT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    resp = jsonObject.getString("Message");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resp = String.valueOf(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String , String>map = new HashMap<>();
                map.put("complaint_id",evidenceId);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return resp;
    }

    public String  updateEvidence(SuspectClass  suspectClass, Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SUSPECT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            response = jsonObject.getString("success");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }}
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {      resp = String.valueOf(error);}
                })

        {
            @Override
            protected Map<String, String> getParams () throws AuthFailureError {
                HashMap <String , String> param = new HashMap<>();

                param.put("complaint_suspect_fname",suspectClass.getFname());
                param.put("complaint_suspect_mname",suspectClass.getMname());
                param.put("complaint_suspect_lname",suspectClass.getLname());
                param.put("complaint_suspect_gender",suspectClass.getGender());
                param.put("complaint_suspect_email",suspectClass.getEmail());
                param.put("complaint_suspect_adhar",suspectClass.getAdharno());
                param.put("complaint_suspect_mobile_no",suspectClass.getMobile());
                param.put("country_name",suspectClass.getCountyname());
                param.put("district_name",suspectClass.getDistname());
                param.put("state_name",suspectClass.getStname());
                param.put("complaint_suspect_dob",suspectClass.getDob());
                param.put("isSuspect",suspectClass.getIsSuspect());
                param.put("photourl",suspectClass.getPhotourl());
                param.put("complaint_suspect_id",String.valueOf(suspectClass.getSuspectid()));
                param.put("complaint_id",String.valueOf(suspectClass.getCaseid()));

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return resp;
    }


}
