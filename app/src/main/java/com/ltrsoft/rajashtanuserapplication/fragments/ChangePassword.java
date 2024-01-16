package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ltrsoft.rajashtanuserapplication.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends Fragment {
    public ChangePassword() {
        // Required empty public constructor
    }
    private Button submit;
    private String pass,confirmpass,mobno;
    private EditText con_new_pass,new_password;
    private static  final String UPDATE_PASSWORD = "https://rj.ltr-soft.com/public/police_api/data/update_password.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.change_password, container, false);
        submit=view.findViewById(R.id.submit_pass);
        new_password = view.findViewById(R.id.new_password);
        con_new_pass = view.findViewById(R.id.con_new_pass);
        Bundle bundle = getArguments();
        mobno =bundle.getString("mobno");

        pass =new_password.getText().toString();
        confirmpass = con_new_pass.getText().toString();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass =new_password.getText().toString();
                confirmpass = con_new_pass.getText().toString();
                if (pass==confirmpass){
                //    changePass();
                    Toast.makeText(getContext(), "password is same", Toast.LENGTH_SHORT).show();
                }
                else {
                  //  Toast.makeText(getContext(), "enter valid password", Toast.LENGTH_SHORT).show();
                    con_new_pass.setError("Enter same password here");
                }
            }
        });

        return view;
    }
    private void changePass() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response.toString());
               // Toast.makeText(getContext(), "response"+response.toString(), Toast.LENGTH_SHORT).show();
                if (response.contains("success")){
                    Toast.makeText(getContext(), "password changed succesfully", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map = new HashMap();
                  map.put("password",confirmpass);
                  map.put("user_mobile",mobno);
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.session_container, new Login())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}