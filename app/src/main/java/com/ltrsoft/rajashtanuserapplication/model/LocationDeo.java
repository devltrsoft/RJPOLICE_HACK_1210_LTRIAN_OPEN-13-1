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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocationDeo {
    private ArrayList<String>listofstate = new ArrayList<>();
    private ArrayList<String>listofcountry = new ArrayList<>();
    private ArrayList<String>listofsidtrict = new ArrayList<>();
    private ArrayList<String>listofcity = new ArrayList<>();

    public static  final String COUNTRY_URL ="https://rj.ltr-soft.com/public/police_api/country/select_country.php ";
    public static  final String STATE_URL ="https://rj.ltr-soft.com/public/police_api/state/select_state.php";
    private HashMap <Integer,String>statecode = new HashMap<>();
    private HashMap <Integer,String>countrycode = new HashMap<>();

    public void getCountry(Context context ,UserCallBack callBack){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, COUNTRY_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("country_name"); // Assuming "name" is the key in your JSON response
                                String country_code2 = jsonObject.getString("country_id"); // Assuming "name" is the key in your JSON response
                                listofcountry.add(name);
                                countrycode.put(i,country_code2);
                            } catch (JSONException e) {
                                callBack.userError(e.toString());
                                e.printStackTrace();
                            }
                        }
                        callBack.userSuccess(listofcountry);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.userError(error.toString());
            }
        }
        );
      RequestQueue requestQueue = Volley.newRequestQueue(context);
      requestQueue.add(jsonArrayRequest);

    }
    public void getState(Context context , UserCallBack callBack,int countryid){
     StringRequest stringRequest1 = new StringRequest(Request.Method.POST, STATE_URL, new Response.Listener<String>() {
         @Override
            public void onResponse(String response) {
             listofstate = new ArrayList<>();
             try {
                 JSONArray jsonArray = new JSONArray(response);
                 for (int i = 0; i < jsonArray.length(); i++) {
                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                     String state_name = jsonObject.getString("state_name");
                     String state_id = jsonObject.getString("state_id");
                     listofstate.add(state_name);
                    statecode.put(i, state_id);
                 }
             } catch (JSONException e) {
                 callBack.userError(e.toString());
             }
             callBack.userSuccess(listofstate);
         }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            System.out.println("erro=" + error.toString());
            callBack.userError(error.toString());
        }
    }) {
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("country_id",statecode.get(countryid));
                return hashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
         queue.add(stringRequest1);
    }

}
