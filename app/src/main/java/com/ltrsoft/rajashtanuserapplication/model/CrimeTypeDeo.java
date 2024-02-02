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
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintType;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class CrimeTypeDeo {

    ArrayList <ComplaintType>list;
    public static final String URL_complain= "https://rj.ltr-soft.com/public/police_api/data/c_type_read.php";
    public void getAllCrimeType(Context context , UserCallBack callBack){
        StringRequest request = new StringRequest(Request.Method.POST,URL_complain, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getContext(), "response = "+response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("response"+response.toString());;
                list= new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String sta = jsonObject.getString("complaint_type_id");
                        String cml = jsonObject.getString("complaint_type_name");
                        ComplaintType complaintType = new ComplaintType(sta,cml);
                        list.add(complaintType);
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
