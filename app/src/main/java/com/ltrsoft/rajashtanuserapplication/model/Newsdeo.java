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
import com.ltrsoft.rajashtanuserapplication.classes.FirClass;
import com.ltrsoft.rajashtanuserapplication.classes.News;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Newsdeo {
    String URLLL="https://rj.ltr-soft.com/public/police_api/news/select_news.php";
    public News news ;
    public ArrayList<News>list=new ArrayList<>();

    public void getAllNews(Context context, UserCallBack userCallBack){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URLLL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String news_title = jsonObject.getString("news_title");
                        String news_description = jsonObject.getString("news_description");
                        String news_date = jsonObject.getString("news_date");
                        String news_category_name = jsonObject.getString("news_category_name");
                        String news_photo_path = jsonObject.getString("news_photo_path"); // Assuming this is the photo path

                       list.add(new News(news_title, news_description, news_date, news_category_name, news_photo_path));
                    } catch (JSONException e) {
                        userCallBack.userError(e.toString());
                        throw new RuntimeException(e);
                    }
                    userCallBack.userSuccess(list);
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
    public News getNews(String complaintId, Context context){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URLLL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String news_title = jsonObject.getString("news_title");
                        String news_description = jsonObject.getString("news_description");
                        String news_date = jsonObject.getString("news_date");
                        String news_category_name = jsonObject.getString("news_category_name");
                        String news_photo_path = jsonObject.getString("news_photo_path"); // Assuming this is the photo path
                        news = new News(news_title, news_description, news_date, news_category_name, news_photo_path);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
        return news;

    }

}
