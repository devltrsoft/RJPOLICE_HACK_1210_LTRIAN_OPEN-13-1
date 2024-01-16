package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.User;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Userdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import java.util.HashMap;
import java.util.Map;

public class Registration extends Fragment {
    public Registration() {
        // Required empty public constructor
    }
  User user;
    Button register;
    TextView login;
    String token;
   EditText  con_password,user_password,user_mobile,user_email,user_name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.registration, container, false);
         register=view.findViewById(R.id.register);
        con_password=view.findViewById(R.id.con_password);
        user_password=view.findViewById(R.id.user_password);
        user_mobile=view.findViewById(R.id.user_mobile);
        user_email=view.findViewById(R.id.user_email);
        user_name=view.findViewById(R.id.user_name);
        login=view.findViewById(R.id.login_text);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()){
                            Log.d("token","token failed");
                        }
                        token=task.getResult();
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        Log.d("token",token);
                    }
                });

         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
//                 getActivity().getSupportFragmentManager().beginTransaction()
//                         .replace(R.id.session_container, new Login())
//                         .addToBackStack(null)
//                         .commit();

                 final String name1 = user_name.getText().toString().trim();
                 final String email1 = user_email.getText().toString().trim();
                 final String mobile = user_mobile.getText().toString().trim();
                 final String password1 = user_password.getText().toString().trim();
                 final String confirmPassword1=con_password.getText().toString().trim();
                 final String phone1 = user_mobile.getText().toString().trim();
                 if (!name1.isEmpty()){
                     if (!email1.isEmpty()){
                         if (!phone1.isEmpty()){
                             if (!password1.isEmpty()){
                                 if (!confirmPassword1.isEmpty()){
                                     if (password1.equals(confirmPassword1)) {

                                        user = new User(name1,password1,email1,mobile,token);
                                         Userdeo userdeo = new Userdeo();

                                         userdeo.createUser(user, getContext(), new UserCallBack() {
                                             @Override
                                             public void userSuccess(Object object) {
                                                 Toast.makeText(getContext(), "response "+object, Toast.LENGTH_SHORT).show();
                                                 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.session_container, new Login())
                                                         .addToBackStack(null)
                                                         .commit();
                                             }
                                             @Override
                                             public void userError(String error) {
                                                 Toast.makeText(getContext(), "error"+error.toString(), Toast.LENGTH_SHORT).show();
                                             }
                                         });


                                     }
                                     else {
                                         con_password.setError("Password is not same");
                                     }
                                 }else {
                                     con_password.setError("Please insert confirm password");
                                 }
                             }else {
                                 user_password.setError("Please insert password");
                             }
                         }else {
                             user_mobile.setError("Please insert Phone no");
                         }
                     }else {
                         user_email.setError("Please insert email");
                     }
                 }else {
                     user_name.setError("Please insert name");
                 }
             }
         });
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 getActivity().getSupportFragmentManager().beginTransaction()
                         .replace(R.id.session_container, new Login())
                         .addToBackStack(null).commit();
             }
         });
        return view;
    }

    private void signup(User user) {




    }
}