package com.ltrsoft.rajashtanuserapplication.model;

import android.content.Context;
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
import com.ltrsoft.rajashtanuserapplication.classes.EvidenceClass;
import com.ltrsoft.rajashtanuserapplication.classes.FirClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Firdeo {

    ArrayList<FirClass> list = new ArrayList<>();
    public final static String FIR_URL = "https://rj.ltr-soft.com/public/police_api/investigation/investigation_detail.php";

    public   String  resp;

    public ArrayList<FirClass> evidenceClasses  = new ArrayList<>();

    public FirClass firClass ;



    public void getAllFir(String userId, Context context, UserCallBack userCallBack){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, FIR_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < 3; i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String fir_id = jsonObject.getString("fir_id");
                        String fir_subject = jsonObject.getString("complaint_description");
                        String fir_type_name = jsonObject.getString("incedant_reporting");
                        String status_name = jsonObject.getString("status_name");
                        String fir_date = jsonObject.getString("incident_date");
                        String firplace = jsonObject.getString("user_address");
                        String wname = jsonObject.getString("investigation_witness_fname");
                        String vname = jsonObject.getString("victim_fname");
                        String sname = jsonObject.getString("suspect_fname");
                        String evidence = jsonObject.getString("evidance_property");
                        list.add(new FirClass(fir_id, fir_subject, fir_type_name, fir_date, status_name,sname,wname,vname));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                userCallBack.userSuccess(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userCallBack.userError(error.toString());
            }
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fir_id", "2023-12-14-1");
                return params;
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);

    }

    public FirClass getEvidence(String firID, Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FIR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String fir_id = jsonObject.getString("fir_id");
                    String fir_subject = jsonObject.getString("complaint_description");
                    String fir_type_name = jsonObject.getString("incedant_reporting");
                    String status_name = jsonObject.getString("status_name");
                    String fir_date = jsonObject.getString("incident_date");
                    String firplace = jsonObject.getString("user_address");
                    String wname = jsonObject.getString("investigation_witness_fname");
                    String vname = jsonObject.getString("victim_fname");
                    String sname = jsonObject.getString("suspect_fname");
                    String evidence = jsonObject.getString("evidance_property");
                    firClass = new FirClass(fir_id, fir_subject, fir_type_name, fir_date, status_name,sname,wname,vname);

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
                hashMap.put("fir_id",firID);
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return firClass;
    }

    public String updateEvidence (String evidenceId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FIR_URL, new Response.Listener<String>() {
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
