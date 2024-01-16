package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.Adapter.NewsAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.News;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Newsdeo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
            public NewsFragment() {
            }

            private RecyclerView recyclerView;
            ArrayList<News> list ;
            private final static String NEWS_READ_URL = "https://rj.ltr-soft.com/public/police_api/news/select_news.php";

            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.news_fragment, container, false);
                recyclerView = view.findViewById(R.id.recycler_news);
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

                if (actionBar != null) {
                    actionBar.setTitle(" News ");
                }

                Newsdeo newsdeo = new Newsdeo();
                newsdeo.getAllNews(getContext(), new UserCallBack() {
                    @Override
                    public void userSuccess(Object object) {
                        list=(ArrayList<News>)object;
                        NewsAdapter adapter = new NewsAdapter(list);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void userError(String error) {
                        Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                if (list != null) {
                    list.clear();
                }
//                JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, NEWS_READ_URL, null, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(i);
//
//                                String news_title = jsonObject.getString("news_title");
//                                String news_description = jsonObject.getString("news_description");
//                                String news_date = jsonObject.getString("news_date");
//                                String news_category_name = jsonObject.getString("news_category_name");
//                                String news_photo_path = jsonObject.getString("news_photo_path"); // Assuming this is the photo path
//
//                                list.add(new News(news_title, news_description, news_date, news_category_name, news_photo_path));

//
//                            } catch (JSONException e) {
//                                Log.d("error = ", " error " + e.toString());
//                                Toast.makeText(getContext(), "json exception", Toast.LENGTH_SHORT).show();
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("error = ", " error " + error.toString());
//                        Toast.makeText(getContext(), "error = " + error.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                RequestQueue queue = Volley.newRequestQueue(getContext());
//                queue.add(request);

                return view;
            }
        }