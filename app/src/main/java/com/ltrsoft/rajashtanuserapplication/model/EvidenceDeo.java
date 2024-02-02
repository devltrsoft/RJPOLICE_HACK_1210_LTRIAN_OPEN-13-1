package com.ltrsoft.rajashtanuserapplication.model;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.Complaint_Photo;
import com.ltrsoft.rajashtanuserapplication.classes.EvidenceClass;

import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class EvidenceDeo {
    public static final String GET_ALL_EVIDENCE="https://rj.ltr-soft.com/public/police_api/data/complaint_id_photo.php";
    public static final String CREATEEVIDECEURL="https://rj.ltr-soft.com/public/police_api/data/complaint_photo_insert.php";
    public static final String URL="https://rj.ltr-soft.com/public/police_api/evidance/read_evidance.php";
    public EvidenceClass evidenceClass ;
    public   String  resp;
    public ArrayList <Complaint_Photo>evidenceClasses  = new ArrayList<>();
    public void createEvidence(String complaint_id,EvidenceClass evidenceClass , Context context, UserCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATEEVIDECEURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            callBack.userSuccess(response);
                        }
                        else {
                            callBack.userError("something error "+response.toString());
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
                param.put("complaint_id", complaint_id);
//                param.put("complaint_photo_path","");
                param.put("complaint_photo_path", evidenceClass.getImagerl());
                param.put("complaint_photo_description", evidenceClass.getDescription());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void getAllEvidence(String complaint_id, Context context , UserCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_ALL_EVIDENCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response = "+response.toString());
                if (!response.isEmpty()&&response.length()>1) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String complaint_photo_id = jsonObject.getString("complaint_photo_id");
                            String complaint_photo_path = jsonObject.getString("complaint_photo_path");
                            String complaint_photo_description = jsonObject.getString("complaint_photo_description");
                            evidenceClasses.add(new Complaint_Photo(complaint_photo_id,complaint_photo_path,complaint_photo_description));
                        }
                    } catch (JSONException e) {
                        callBack.userError(e.toString());
                        throw new RuntimeException(e);
                    }
                        callBack.userSuccess(evidenceClasses);
                }
                else {
                    evidenceClasses = new ArrayList<>();
                    callBack.userSuccess(evidenceClasses);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.userError(error.toString());
                System.out.println("error "+error.toString());
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("complaint_id",complaint_id);
//                hashMap.put("complaint_id","1");
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
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
