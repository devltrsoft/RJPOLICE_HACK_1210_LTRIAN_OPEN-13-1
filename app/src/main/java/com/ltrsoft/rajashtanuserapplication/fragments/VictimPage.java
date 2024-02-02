package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.Adapter.VictimAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.VictimClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.model.Victimdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VictimPage extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<VictimClass> list = new ArrayList<>();
    public TextView textView;
    public Spinner complaints;
    public ArrayList<String>listcomplain= new ArrayList<>();
    public ArrayAdapter adapter1 ;
    HashMap <Integer ,String>map = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.victim_page, container, false);
        recyclerView = view.findViewById(R.id.vrecycle);
        textView = view.findViewById(R.id.vtv);
        complaints = view.findViewById(R.id.vcspin);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(" Victim History Detail ");
        }
        if (list!=null){
            list.clear();
        }
        complaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String complain_id = map.get(i);
                loadVictims(complain_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            if (!map.isEmpty()){
                loadVictims(map.get(0));
            }
            }
        });

        UserDataAccess access = new UserDataAccess();
        Complaintdeo complaintdeo = new Complaintdeo();
        try {
            complaintdeo.getUserAllComplaint(access.getUserId(getActivity()), getContext(), new UserCallBack() {
                @Override
                public void userSuccess(Object object) {
                    ArrayList <ComplaintClass>list = (ArrayList<ComplaintClass>) object;
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            ComplaintClass complaintClass = list.get(i);
                            listcomplain.add(complaintClass.getComplaint_subject());
                            map.put(i, complaintClass.getComplaint_id());
                        }
                        adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, listcomplain);
                        adapter1.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                        complaints.setAdapter(adapter1);
                    }
                    else {
                        showNoComplaint();
                    }
                }
                @Override
                public void userError(String error) {
                    Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
                    showError("complaint Error"+error.toString());
                }
            });
        } catch (JSONException e) {
            Toast.makeText(getContext(), "error "+e.toString(), Toast.LENGTH_SHORT).show();
            showError("complaint Error"+e.toString());
            throw new RuntimeException(e);
        }

        return view;
    }
    private void loadVictims(String complainId) {
        Victimdeo victimdeo = new Victimdeo();
        victimdeo.getAllVVictim(complainId, getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                ArrayList<VictimClass>list1 = (ArrayList<VictimClass>) object;
                if (!list1.isEmpty()) {
                    VictimAdapter adapter = new VictimAdapter(list1);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    showNoVictim();
                }
            }
            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "error = "+error.toString(), Toast.LENGTH_SHORT).show();
                showError("victim Error "+error.toString());
            }
        });
    }
    private void showNoComplaint() {

        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("You have no complaints register");
    }
    private void showNoVictim() {
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("This complaint have no suspect");
    }
    private void showError(String s){
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("somthng error while loading "+s.toString());
    }
}
