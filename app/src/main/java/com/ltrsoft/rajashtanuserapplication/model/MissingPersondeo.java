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
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.FirClass;
import com.ltrsoft.rajashtanuserapplication.classes.Missing_person_class;
import com.ltrsoft.rajashtanuserapplication.classes.News;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MissingPersondeo {

    public String NEWS_READ_URL="";
    public String NEWS_READ_URL1="";
    private String URl="https://rj.ltr-soft.com/public/police_api/data/read_missing.php";


    public ArrayList<Missing_person_class>list = new ArrayList<>();
    public Missing_person_class missingPersonClass;

    public void getAllMissingPerson(Context context , UserCallBack userCallBack) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("response "+response.toString());
                    try {
                        for (int i = 0 ;i < response.length() ; i++){
                            JSONObject jsonObject = response.getJSONObject(i);
                            String person_name = jsonObject.getString("complaint_subject");
                            String person_description = jsonObject.getString("complaint_description");
                            String person_contact = jsonObject.getString("user_mobile1");
                            String person_location = jsonObject.getString("user_address");

                            list.add(new Missing_person_class(R.id.missing_person_pic,person_name,person_description,person_contact,person_location));
                        }
                        userCallBack.userSuccess(list);

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
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


//    public Missing_person_class getMissingPerson(String misid, Context context){
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, NEWS_READ_URL1, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for (int i = 0; i < response.length(); i++) {
//
//                        JSONObject jsonObject = response.getJSONObject(i);
//
//                        //int person_image;
//
//                        String person_name = jsonObject.getString("complaint_subject");
//                        String person_description = jsonObject.getString("complaint_description");
//                        String person_contact = jsonObject.getString("user_mobile1");
//                        String person_location = jsonObject.getString("user_address");
//
//                        missingPersonClass = new Missing_person_class(androidx.core.R.id.action_image, person_name, person_description, person_contact, person_location);
//                        }
//                 } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap <String , String>hashMap  = new HashMap<>();
//                hashMap.put("missing_person",misid);
//                return hashMap;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(context);
//        queue.add(request);
//        return missingPersonClass;
//    }
}
