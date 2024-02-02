package com.ltrsoft.rajashtanuserapplication.model;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.Station;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class StationDeo {
    ArrayList <Station> list;
    private static final String STATION_URL = "https://rj.ltr-soft.com/public/police_api/police_station/read_police_station.php";

    public void getAllStation(Context context, UserCallBack callBack){
        StringRequest request = new StringRequest(Request.Method.POST, STATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                list = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String sta = jsonObject.getString("police_station_name");
                        String police_station_id = jsonObject.getString("police_station_id");
                        list.add(new Station(police_station_id,sta));
                    }
                } catch (JSONException e) {
                    callBack.userError(e.toString());
                    throw new RuntimeException(e);
                }
                callBack.userSuccess(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.userError(error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
       RequestQueue requestQueue1 = Volley.newRequestQueue(context);
        requestQueue1.add(request);
    }
}
