package com.ltrsoft.rajashtanuserapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.fragments.NavigationDrawer;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public  final static String INVESTIGATION_URL = "https://rj.ltr-soft.com/public/police_api/data/complaint_user_read.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationDrawer navigationDrawer=new NavigationDrawer();
        getSupportFragmentManager().beginTransaction().add(R.id.container_mains,navigationDrawer).commit();
    }
}