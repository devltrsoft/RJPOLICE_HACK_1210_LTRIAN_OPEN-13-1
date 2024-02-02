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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.User;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class Userdeo {
    public String LOGINURL = "https://rj.ltr-soft.com/public/police_api/login/user_login.php";
    private static final String USER_PROFILE_UPDATE_URL = "https://rj.ltr-soft.com/public/police_api/data/user_update.php";
    private static final String USER_PROFILE_READ_URL = "https://rj.ltr-soft.com/public/police_api/data/user_read.php";
    private static String URL = "https://rj.ltr-soft.com/public/police_api/data/user_insert.php";
    private static String USERCREATEURL = "https://rj.ltr-soft.com/public/police_api/data/user_insert.php";
    public StringBuilder success = new StringBuilder();
    public User user;



    public void loginUser(String email, String password, Context context, UserCallBack callBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGINURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("success"+response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Message = jsonObject.getString("Message");
                           // JSONObject jsonObject1 = jsonObject.getJSONObject("0");
                          //  callBack.userSuccess(Message);

                            if (Message.equals("100")) {
                                JSONObject userid = jsonObject.getJSONObject("0");
                                String user_id = userid.getString("user_id");
                                callBack.userSuccess(user_id);
                            } else if (Message.equals("200")) {
                                callBack.userError("User Not FOund");
                            } else if (Message.equals("300")) {
                                callBack.userError("Login Failed");
                            }
                        } catch (JSONException e) {
                            callBack.userError(e.toString());
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.userError(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

//        return getSuccess().toString();
    }
    public void getUser(String userId, Context context , UserCallBack callBack) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_PROFILE_READ_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String user_fname = jsonObject.getString("user_fname");
                                    String user_mname = jsonObject.getString("user_mname");
                                    String user_lname = jsonObject.getString("user_lname");
                                    String user_address = jsonObject.getString("user_address");

                                    String city_id = jsonObject.getString("city_name");
                                    String district_id = jsonObject.getString("district_name");
                                    String country_id = jsonObject.getString("country_name");
                                    String state_id = jsonObject.getString("state_name");

                                    String user_email = jsonObject.getString("user_email");
                                    String user_gender = jsonObject.getString("user_gender");
                                    String user_dob = jsonObject.getString("user_dob");
                                    String user_mobile1 = jsonObject.getString("user_mobile1");
                                    String user_adhar = jsonObject.getString("user_adhar");
                                    String user_pan = jsonObject.getString("user_pan");
                                    String user_fcn_token = jsonObject.getString("user_fcn_token");
                                    String photo = jsonObject.getString("user_photo");
                                    user = new User(user_fname, user_mname, user_lname, user_address, city_id,
                                            district_id, country_id, state_id,"", user_email, user_gender, user_dob, user_mobile1,
                                            user_adhar,photo,user_fcn_token,user_pan);
                                }
                            } catch (JSONException e) {
                                callBack.userError(e.toString());
                                throw new RuntimeException(e);
                            }
                            callBack.userSuccess(user);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    callBack.userError(error.toString());
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("user_id", userId);
                return hashMap;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void  createUser(User user , Context context,UserCallBack userCallBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if (response.contains("success")) {
                            userCallBack.userSuccess(response);
                            System.out.println("response "+response.toString());
                            } else {
                           userCallBack.userError(response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               userCallBack.userError(error.toString());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("user_fname", user.getUser_fname());
                param.put("user_email", user.getUser_email());
                param.put("user_password", user.getUser_password());
                param.put("user_mobile1", user.getUser_mobile1());
                param.put("user_fcn_token", user.getUser_fcn_token());
                return param;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void updateUser(User user , Context context , Activity activity , UserCallBack callBack){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, USER_PROFILE_UPDATE_URL,
                response -> {
                    if (response.contains("success")){
                        callBack.userSuccess(response.toString());
                    }
                    else {
                        callBack.userError(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.userError(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                UserDataAccess userDataAccess = new UserDataAccess();
                hashMap.put("user_id",userDataAccess.getUserId(activity) );
                hashMap.put("user_fname",user.getUser_fname());
                hashMap.put("user_mname",user.getUser_mname());
                hashMap.put("user_lname",user.getUser_lname());
                hashMap.put("user_address",user.getUser_address());
                hashMap.put("user_email",user.getUser_email());
                hashMap.put("user_gender",user.getUser_gender());
                hashMap.put("user_dob",user.getUser_dob());
                hashMap.put("user_mobile1",user.getUser_mobile1());
                hashMap.put("user_adhar",user.getUser_adhar());
                hashMap.put("country_id","1");
                hashMap.put("state_id","1");
                hashMap.put("district_id","1");
                hashMap.put("city_id","1");
                return hashMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }




}
