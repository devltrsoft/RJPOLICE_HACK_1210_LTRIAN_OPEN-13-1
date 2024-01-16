package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltrsoft.rajashtanuserapplication.Adapter.Missing_person_Adapter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.MissingPersondeo;
import com.ltrsoft.rajashtanuserapplication.classes.Missing_person_class;
import java.util.ArrayList;

public class MissingPage extends Fragment {
    public MissingPage() {    }
    private RecyclerView person_card_recycler;
    private String URl="https://rj.ltr-soft.com/public/police_api/data/read_missing.php";
    ArrayList<Missing_person_class> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.missing_page, container, false);
        Toast.makeText(getContext(), "in missing page", Toast.LENGTH_SHORT).show();
        person_card_recycler=view.findViewById(R.id.missing_person_recycle);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Missing Person ");
        }
        if (list!=null){
            list.clear();
        }
        loadCases();
        return view;
    }

    private void loadCases() {

        MissingPersondeo missingPersondeo = new MissingPersondeo();
        missingPersondeo.getAllMissingPerson(getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                list=(ArrayList<Missing_person_class>)object;
                Missing_person_Adapter adapter=new Missing_person_Adapter(list);
                LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
                person_card_recycler.setLayoutManager(layoutManager);
                person_card_recycler.setAdapter(adapter);
            }

            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


//        list.add(new Missing_person_class(1,"ganesh","lost to heavean","99999","latur"));
//        Missing_person_Adapter adapter=new Missing_person_Adapter(list);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
//        person_card_recycler.setLayoutManager(layoutManager);
//        person_card_recycler.setAdapter(adapter);

//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URl,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("response",""+response.toString());
//                        Toast.makeText(getContext(), "response = "+response.toString(), Toast.LENGTH_SHORT).show();
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//
//                            for (int i = 0 ;i < jsonArray.length() ; i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                                //int person_image;
//
//                                String person_name = jsonObject.getString("complaint_subject");
//                                String person_description = jsonObject.getString("complaint_description");
//                                String person_contact = jsonObject.getString("user_mobile1");
//                                String person_location = jsonObject.getString("user_address");
//
//                                list.add(new Missing_person_class(R.id.missing_person_pic,person_name,person_description,person_contact,person_location));
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getContext(), "json error"+e.toString(), Toast.LENGTH_SHORT).show();
//                            throw new RuntimeException(e);
//                        }
////                        Missing_person_Adapter adapter=new Missing_person_Adapter(list);
////                        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
////                        person_card_recycler.setLayoutManager(layoutManager);
////                        person_card_recycler.setAdapter(adapter);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext() ,"volley error"+error.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> param = new HashMap<>();
//                return param;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);
    }
}