package com.ltrsoft.rajashtanuserapplication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.Adapter.WitnessAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.WitnessClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.model.Witnessdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WitnessFragment extends Fragment {
    RecyclerView recyclerView;
    private SearchView searchView;
    TextView t;
    WitnessAdapter myAdapter;
    ArrayList<WitnessClass> witnessList;
    public TextView textView;
    public Spinner complaints;
    public ArrayList<String>listcomplain= new ArrayList<>();
    public ArrayAdapter adapter1 ;
    HashMap<Integer ,String> map = new HashMap<>();
    String url = "https://rj.ltr-soft.com/public/police_api/data/complaint_witness_read.php"; // Change the URL

    public WitnessFragment() {
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_witness_page, container, false); // Change to your witness fragment layout
        recyclerView = v.findViewById(R.id.wrecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textView = v.findViewById(R.id.vtv);
        complaints = v.findViewById(R.id.vcspin);

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
                    showError("complaint "+error.toString());
                }
            });
        } catch (JSONException e) {
            Toast.makeText(getContext(), "error "+e.toString(), Toast.LENGTH_SHORT).show();
            showError("complaint "+e.toString());
            throw new RuntimeException(e);
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

        return v;
    }
    private void loadVictims(String s) {
        Witnessdeo witnessdeo = new Witnessdeo();
        witnessdeo.getAllWitness(s, getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                witnessList = (ArrayList<WitnessClass>)object;
                if (!witnessList.isEmpty()) {
                    myAdapter = new WitnessAdapter(witnessList); // Change to WitnessAdapter
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(myAdapter);
                }
                else {
                    showNoVictim();
                }
            }
            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
                showError("witness "+error.toString());
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
