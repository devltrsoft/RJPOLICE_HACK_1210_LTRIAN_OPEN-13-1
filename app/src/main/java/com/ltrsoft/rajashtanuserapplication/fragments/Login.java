package com.ltrsoft.rajashtanuserapplication.fragments;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ltrsoft.rajashtanuserapplication.MainActivity;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.User;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Userdeo;
import com.ltrsoft.rajashtanuserapplication.utils.UserDataAccess;

import java.util.Objects;

public class Login extends Fragment {
    public Login() {
        // Required empty public constructor
    }
    Button loginbtn;
    EditText Email,Password;


    TextView registration,forgot_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginbtn=view.findViewById(R.id.loginbtn);
        registration=view.findViewById(R.id.registration);
        forgot_password=view.findViewById(R.id.forgot_password);
        Email=view.findViewById(R.id.email);
        Password=view.findViewById(R.id.password);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.session_container, new Registration())
                        .addToBackStack(null)
                        .commit();
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.session_container, new ForgetPassword())
                        .addToBackStack(null)
                        .commit();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = Email.getText().toString().trim();
                String mPass = Password.getText().toString().trim();

                if (!mEmail.isEmpty() && !mPass.isEmpty()) {
                    Userdeo userdeo = new Userdeo();
                    userdeo.loginUser(mEmail, mPass, getContext(), new UserCallBack() {
                        @Override
                        public void userSuccess(Object object) {
                            String userId = (String) object;
                            UserDataAccess dataAccess = new UserDataAccess();
                            dataAccess.setUserId(userId,getActivity());

                            SharedPreferences pref = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("flag", true)
                                    .apply();
                            Intent main_activity_intent = new Intent(getContext(), MainActivity.class);
                            startActivity(main_activity_intent);
                        }
                        @Override
                        public void userError(String error) {
                            Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Email.setError("Please insert email");
                    Password.setError("Please insert Password");
                }
            }
        });
        return view;
    }
}