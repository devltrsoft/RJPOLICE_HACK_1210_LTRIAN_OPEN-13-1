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

import com.ltrsoft.rajashtanuserapplication.Adapter.EvidenceAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.classes.Complaint_Photo;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.model.EvidenceDeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EvidencePage extends Fragment {
    private RecyclerView recyclerView;
    private EvidenceAdapter adapter;
    private List<Complaint_Photo> items;
    public TextView textView;
    public Spinner complaints;
    public ArrayList<String>listcomplain= new ArrayList<>();
    public ArrayAdapter<String> adapter1 ;
    HashMap <Integer ,String>map = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.evidence_page, container, false);

            recyclerView = view.findViewById(R.id.evidencerecycle);
            textView = view.findViewById(R.id.evidencetv);
            complaints = view.findViewById(R.id.ecspin);
            UserDataAccess access = new UserDataAccess();
            Complaintdeo complaintdeo = new Complaintdeo();
            try {
                complaintdeo.getUserAllComplaint(access.getUserId(getActivity()), getContext(), new UserCallBack() {
                    @Override
                    public void userSuccess(Object object) {
                        ArrayList<ComplaintClass> list = (ArrayList<ComplaintClass>) object;
                        if (!list.isEmpty()) {
                            for (int i = 0; i < list.size(); i++) {
                                ComplaintClass complaintClass = list.get(i);
                                listcomplain.add(complaintClass.getComplaint_subject());
                                map.put(i, complaintClass.getComplaint_id());
                            }
                            adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_expandable_list_item_1, listcomplain);
                            adapter1.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                            complaints.setAdapter(adapter1);
                        } else {
                                showNoComplaint();
                        }
                    }
                    @Override
                    public void userError(String error) {
                        Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
                        showError("this page Error "+error.toString());
                    }
                });
            } catch (JSONException e) {
                Toast.makeText(getContext(), "error "+e.toString(), Toast.LENGTH_SHORT).show();
                showError("complaint Error "+e.toString());
                throw new RuntimeException(e);
            }

            complaints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getContext(), "complaint is"+map.get(i), Toast.LENGTH_SHORT).show();
                    loadEvidence(map.get(i));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    if (!map.isEmpty()) {
                        loadEvidence(map.get(0));
                    }
                }
            });

            return view;
    }
    private void loadEvidence(String s) {
        EvidenceDeo evidenceDeo = new EvidenceDeo();
        evidenceDeo.getAllEvidence(s, getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                items = (ArrayList<Complaint_Photo>)object;
                if (!items.isEmpty()) {
                    adapter = new EvidenceAdapter(items);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(getContext(), "size = "+items.size(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "You have no complaint registered", Toast.LENGTH_SHORT).show();
                 showNOEvidenvce();
                }
                System.out.println("succes");
            }
            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
               showError("evidence"+error.toString());
            }
        });
    }
    private void showNoComplaint() {
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("You have no complaints register");
    }
    private void showNOEvidenvce() {
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("This complaint have no Evidence select other");
    }
    private void showError(String s){
        recyclerView.setVisibility(View.GONE);
        complaints.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("somthng error while loading "+s.toString());
    }
}
