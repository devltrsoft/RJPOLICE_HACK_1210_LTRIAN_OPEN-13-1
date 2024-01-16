package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.Adapter.EvidenceAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.EvidenceClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.EvidenceDeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidencePage extends Fragment {
    private RecyclerView recyclerView;
    private EvidenceAdapter adapter;
    private List<EvidenceClass> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        {
            View view = inflater.inflate(R.layout.evidence_page, container, false);

            recyclerView = view.findViewById(R.id.evidencerecycle);
            EvidenceDeo evidenceDeo = new EvidenceDeo();

            evidenceDeo.getAllEvidence(new UserDataAccess().getUserId(getActivity()), getContext(), new UserCallBack() {
                @Override
                public void userSuccess(Object object) {

//                    Toast.makeText(getContext(), "response"+object.toString(), Toast.LENGTH_SHORT).show();
                    items = (ArrayList<EvidenceClass>)object;
                    adapter = new EvidenceAdapter(requireContext(), items, Volley.newRequestQueue(requireContext()));
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void userError(String error) {
                    Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }
}
