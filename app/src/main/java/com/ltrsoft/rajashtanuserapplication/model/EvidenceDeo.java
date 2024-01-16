package com.ltrsoft.rajashtanuserapplication.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EvidenceDeo {
    public static final String URL="https://rj.ltr-soft.com/public/police_api/evidance/read_evidance.php";
    public static final String CREATEEVIDECEURL="https://rj.ltr-soft.com/public/police_api/data/complaint_photo_insert.php";
//    public static final String CREATEEVIDECEURL="https://rj.ltr-soft.com/public/police_api/evidance/read_evidance.php";
    public EvidenceClass evidenceClass ;
    public   String  resp;
    public ArrayList <EvidenceClass>evidenceClasses  = new ArrayList<>();
    public void createEvidence(EvidenceClass evidenceClass , Context context, UserCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATEEVIDECEURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            callBack.userSuccess(response);
                        }
                        else {
                            callBack.userError("something error ");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.userError(error.toString());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
//                    param.put("complaint_id", complain_name.getSelectedItem().toString());
                param.put("complaint_id", evidenceClass.getId());
                param.put("complaint_photo_path", evidenceClass.getImagerl());
                param.put("complaint_photo_description", evidenceClass.getDescription());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void getAllEvidence(String userID, Context context , UserCallBack callBack){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String path = "";
                                String idd = jsonObject.getString("complaint_id");
                               // String description = jsonObject.getString("evidence_description");
                                evidenceClasses.add(new EvidenceClass(path,idd,"description"));
                                callBack.userSuccess(evidenceClasses);
                            } catch (JSONException e) {
                                callBack.userError(e.toString());
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.userError(error.toString());
                        error.printStackTrace();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("complaint_photo_id",userID);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public EvidenceClass getEvidence(String evidenceId, Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String path = jsonObject.getString("complaint_photo_path");
                    String idd = jsonObject.getString("complaint_id");
                    String description = jsonObject.getString("evidence_description");
                    evidenceClass=new EvidenceClass(path,idd,description);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String , String> hashMap = new HashMap();
                hashMap.put("evidence_id",evidenceId);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return evidenceClass;
    }

    public String updateEvidence (String evidenceId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                  resp = jsonObject.getString("success");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resp=String.valueOf(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String , String>hashMap = new HashMap<>();
                hashMap.put("evidence_id",evidenceId);
                return hashMap;
            }
        };
        return resp;
    }
    public String deleteEvidence (String evidenceId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    resp = jsonObject.getString("success");

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resp=String.valueOf(error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String , String>hashMap = new HashMap<>();
                hashMap.put("evidence_id",evidenceId);
                return hashMap;
            }
        };
        return resp;
    }



}
