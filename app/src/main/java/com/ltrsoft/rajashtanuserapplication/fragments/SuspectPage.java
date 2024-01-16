package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.Adapter.SuspectAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.SuspectClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.SuspectDeo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuspectPage extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<SuspectClass> list = new ArrayList<>();
    StringBuilder complainId = new StringBuilder();



    public final static String SUSPECT_URL = "https://rj.ltr-soft.com/public/police_api/data/complaint_suspect_read.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suspect_page, container, false);
        recyclerView = view.findViewById(R.id.investigation_recycler);

        SuspectDeo suspectDeo = new SuspectDeo();
        suspectDeo.getAllSuspect("27", getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
            list  = (  ArrayList<SuspectClass>)object;
                SuspectAdapter adapter = new SuspectAdapter(list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void userError(String error) {

            }
        });

        return view;
    }
}
