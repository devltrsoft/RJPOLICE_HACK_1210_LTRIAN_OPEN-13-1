package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.Adapter.FirAdpter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.FirClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Firdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirPage extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<FirClass> list ;
    public final static String FIR_URL = "https://rj.ltr-soft.com/public/police_api/investigation/investigation_detail.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fir_page, container, false);
        recyclerView = view.findViewById(R.id.firrecycleview);


//        if (!list.isEmpty()){
//            list.clear();
//        }


        Firdeo firdeo = new Firdeo();
        UserDataAccess userDataAccess = new UserDataAccess();
        firdeo.getAllFir(userDataAccess.getUserId(getActivity()), getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                list = (ArrayList<FirClass>)object;
                FirAdpter adapter = new FirAdpter(list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "Error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, FIR_URL, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                // Toast.makeText(getContext(), "response"+response, Toast.LENGTH_SHORT).show();
////                for (int i = 0; i < response.length(); i++) {
//                for (int i = 0; i < 3; i++) {
//                    try {
//
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        String fir_id = jsonObject.getString("fir_id");
//                        String fir_subject = jsonObject.getString("complaint_description");
//                        String fir_type_name = jsonObject.getString("incedant_reporting");
//                        String status_name = jsonObject.getString("status_name");
//                        String fir_date = jsonObject.getString("incident_date");
//                        String firplace = jsonObject.getString("user_address");
//                        String wname = jsonObject.getString("investigation_witness_fname");
//                        String vname = jsonObject.getString("victim_fname");
//                        String sname = jsonObject.getString("suspect_fname");
//                        String evidence = jsonObject.getString("evidance_property");
//
//                    } catch (JSONException e) {
//                        Toast.makeText(getContext(), "json exception", Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error response = " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("fir_id", "2023-12-14-1");
//                return params;
//            }
//        });
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        queue.add(request);
        return view;
}
}

// list.add(new FirClass(fir_id, fir_subject, fir_type_name, fir_date, status_name,sname,wname,vname));

