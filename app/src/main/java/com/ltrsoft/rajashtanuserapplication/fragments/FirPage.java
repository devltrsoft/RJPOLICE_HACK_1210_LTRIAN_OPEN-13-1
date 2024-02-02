package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
    TextView textView ;
    public final static String FIR_URL = "https://rj.ltr-soft.com/public/police_api/investigation/investigation_detail.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fir_page, container, false);
        recyclerView = view.findViewById(R.id.firrecycleview);
        textView = view.findViewById(R.id.firtv);

//        if (!list.isEmpty()){
//            list.clear();
//        }


        Firdeo firdeo = new Firdeo();
        UserDataAccess userDataAccess = new UserDataAccess();
        System.out.println("userid"+userDataAccess.getUserId(getActivity()));
        firdeo.getAllFir(userDataAccess.getUserId(getActivity()), getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                list = (ArrayList<FirClass>)object;
                if (list!=null) {
                    FirAdpter adapter = new FirAdpter(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getContext(), "You have No Fir", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("You have no fir registered");
                }
            }
            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "Error"+error.toString(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("Error While Loading FIR ");
            }
        });
        return view;
}
}
