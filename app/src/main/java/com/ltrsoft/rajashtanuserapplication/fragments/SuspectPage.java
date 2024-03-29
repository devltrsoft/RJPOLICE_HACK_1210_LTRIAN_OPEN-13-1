package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltrsoft.rajashtanuserapplication.Adapter.SuspectAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.SuspectClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.model.SuspectDeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class SuspectPage extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<SuspectClass> list ;
    public TextView textView;
    public Spinner complaints;
    public ArrayList<String>listcomplain= new ArrayList<>();
    public ArrayAdapter adapter1 ;
    HashMap <Integer ,String>map = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suspect_page, container, false);
        recyclerView = view.findViewById(R.id.investigation_recycler);
        textView = view.findViewById(R.id.stv);
        complaints = view.findViewById(R.id.scspin);

        complaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String complain_id = map.get(i);
                Toast.makeText(getContext(), ""+complain_id, Toast.LENGTH_SHORT).show();
                loadSuspect(complain_id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (!map.isEmpty()){
                loadSuspect(map.get(0));
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
                }
            });
        } catch (JSONException e) {
            Toast.makeText(getContext(), "error "+e.toString(), Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
        return view;
    }
    private void loadSuspect(String complainId) {
        SuspectDeo suspectDeo = new SuspectDeo();
        suspectDeo.getAllSuspect(complainId, getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                list  = (  ArrayList<SuspectClass>)object;
                //   Toast.makeText(getContext(), "list "+list.toString(), Toast.LENGTH_SHORT).show();
                if (!list.isEmpty()) {
                    SuspectAdapter adapter = new SuspectAdapter(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
                else {
//                    Toast.makeText(getContext(), "You Have no Compaint ", Toast.LENGTH_SHORT).show();
                    showNOSuspect();
                }
            }
            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
              showError("Suspect"+"  Error "+error.toString());
            }
        });
    }
    private void showNoComplaint() {
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("You have no complaints register");
    }
    private void showNOSuspect() {
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("This complaint have no suspect select other");
    }
    private void showError(String s){
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("somthng error while loading "+s.toString());
    }
}
