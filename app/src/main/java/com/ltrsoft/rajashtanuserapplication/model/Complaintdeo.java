package com.ltrsoft.rajashtanuserapplication.model;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Complaintdeo  {
    private static final String INVESTIGATION_URL = "" ;
    private static final String COMPLAINTCREATE = "https://rj.ltr-soft.com/public/police_api/data/complaint_insert.php" ;
    private static final String COMPLAINTGETALL = "https://rj.ltr-soft.com/public/police_api/data/complaint_user_read.php" ;
    private static final String COMPLAINTUPDATE = "" ;
    private static final String COMPLAINTDELETE = "" ;
    public String responses;

    public  void createComplain(ComplaintClass complaintClass , Context context, UserCallBack userCallBack){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, COMPLAINTCREATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("complaint_data");
                            String complain_id = jsonObject1.getString("complaint_id");
                                userCallBack.userSuccess(complain_id);
                        } catch (JSONException e) {
                         userCallBack.userError(e.toString());
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userCallBack.userError(error.toString());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                 param.put("complain_name",complaintClass.getComplaint_subject());
                param.put("complaint_type_name",complaintClass.getComplaint_type_name());//complaint_type_id);
                param.put("complaint_description",complaintClass.getComplaint_description());
                param.put("complaint_against",complaintClass.getComplaint_against());
                param.put("incident_date",complaintClass.getIncident_date());
                param.put("complaint_location",complaintClass.getComplaint_location());
                param.put("user_id",complaintClass.getUser_id());
//                param.put("latitude",complaintClass.getLatitude());
//                param.put("longitude",complaintClass.getLongitude());

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
    public void getUserAllComplaint(String userId , Context context, UserCallBack userCallBack) throws JSONException {
            ArrayList <ComplaintClass> listofComplain = new ArrayList<>();
            StringRequest stringRequest =  new StringRequest(Request.Method.POST, COMPLAINTGETALL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("resposne "+response.toString());
                            try {
                                JSONArray jsonArray  = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String complaint_id = jsonObject.getString("complaint_id");
                                    String complaint_subject = jsonObject.getString("complaint_subject");
                                    String complaint_description = jsonObject.getString("complaint_description");
                                    String incident_date = jsonObject.getString("incident_date");
                                    String complaint_location = jsonObject.getString("complaint_location");
                                    String user_address = jsonObject.getString("user_address");
                                    String complaint_type_name = jsonObject.getString("complaint_type_name");
                                    UserDataAccess userDataAccess = new UserDataAccess();
                                    listofComplain.add(new ComplaintClass(complaint_subject,complaint_description,incident_date,complaint_location,user_address,"",
                                            "",complaint_type_name));
                                    userCallBack.userSuccess(listofComplain);
                                }
                            } catch (JSONException e) {
                                userCallBack.userError(e.toString());
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    userCallBack.userError(error.toString());
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap <String , String> hashMap = new HashMap();
                    hashMap.put("user_id",userId);
                    return hashMap;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
    }
    public void getUserComplain(String complaintId , Context context , UserCallBack userCallBack) throws JSONException {
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, COMPLAINTGETALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject  = new JSONObject(response);
                                String complaint_id = jsonObject.getString("complaint_id");
                                String complaint_subject = jsonObject.getString("complaint_subject");
                                String complaint_description = jsonObject.getString("complaint_description");
                                String incident_date = jsonObject.getString("incident_date");
                                String complaint_location = jsonObject.getString("complaint_location");
                                String user_address = jsonObject.getString("user_address");
                                String status_name = jsonObject.getString("status_name");
                                String complaint_type_name = jsonObject.getString("complaint_type_name");
                                   userCallBack.userSuccess(new ComplaintClass(complaint_id,complaint_subject,complaint_description,incident_date,
                                        complaint_location,user_address,status_name,complaint_type_name));
                        } catch (JSONException e) {
                          userCallBack.userError(e.toString());
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            userCallBack.userError(error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String , String> hashMap = new HashMap();
                hashMap.put("complaint_id",complaintId);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public String  updateComplain(ComplaintClass complaintClass, Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INVESTIGATION_URL,
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
                        public void onErrorResponse(VolleyError error) {                        }
                    })

                    {
                        @Override
                        protected Map<String, String> getParams () throws AuthFailureError {
                       HashMap <String , String> param = new HashMap<>();
                            param.put("complaint_type_id","1");
                            param.put("complaint_description",complaintClass.getComplaint_description());
                            param.put("complaint_against",complaintClass.getComplaint_against());
                            param.put("incident_date",complaintClass.getIncident_date());
                            param.put("complaint_location",complaintClass.getComplaint_location());
                            param.put("station_id",complaintClass.getStation_id());
                            param.put("user_id",complaintClass.getUser_id());
                            param.put("latitude",complaintClass.getLatitude());
                            param.put("longitude",complaintClass.getLongitude());
                        return param;
                    }
                    };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return responses;
    }
    public String DeleteComplain(String useId , Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INVESTIGATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    responses = jsonObject.getString("Message");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responses = String.valueOf(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String , String>map = new HashMap<>();
                map.put("complaint_id",useId);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return responses;
    }
}
