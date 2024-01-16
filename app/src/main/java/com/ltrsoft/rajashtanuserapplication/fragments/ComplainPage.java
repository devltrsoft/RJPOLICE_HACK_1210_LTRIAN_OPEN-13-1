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
import com.ltrsoft.rajashtanuserapplication.Adapter.ComplaintAdapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Complaintdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplainPage extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<ComplaintClass> list;
    public final static String INVESTIGATION_URL = "https://rj.ltr-soft.com/public/police_api/data/complaint_user_read.php";

    StringBuilder output = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complain_page, container, false);
        recyclerView = view.findViewById(R.id.investigation_recycler);

        loaRecycle();




        return view;
    }

    private void loaRecycle() {
        Complaintdeo complaintdeo  = new Complaintdeo();
        UserDataAccess userDataAccess = new UserDataAccess();
        try {
            complaintdeo.getUserAllComplaint(userDataAccess.getUserId(getActivity()), getContext(), new UserCallBack() {
                @Override
                public void userSuccess(Object object) {
                    list = (ArrayList<ComplaintClass>)object;
                    ComplaintAdapter adapter = new ComplaintAdapter(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
                @Override
                public void userError(String error) {
                    Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
                    loaRecycle();
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}
