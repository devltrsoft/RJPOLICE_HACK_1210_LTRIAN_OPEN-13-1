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
import com.ltrsoft.rajashtanuserapplication.classes.Camera;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CameraDao {
    private static  final String CREATE_CAMEARA_URL = "https://rj.ltr-soft.com/public/police_api/camera_uploading/insert_camera.php";
    private Context context ;

    public CameraDao(Context context) {
        this.context = context;
    }

    ArrayList<Camera>list;
    public void createCamera(Camera camera , UserCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CREATE_CAMEARA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               System.out.println("resposne = "+response.toString());
                if (response.contains("success")){
                    callBack.userSuccess("success");
                }
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
                HashMap <String , String> hashMap = new HashMap<>();
                hashMap.put("station_id",camera.getStation_id());
                hashMap.put("user_id",camera.getUser_id());
                hashMap.put("photo_path",camera.getPhoto_path());
                hashMap.put("description",camera.getDescription());
                hashMap.put("address",camera.getAddress());
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.add(stringRequest);
    }
}
