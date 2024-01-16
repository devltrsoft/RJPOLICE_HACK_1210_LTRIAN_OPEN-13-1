package com.ltrsoft.rajashtanuserapplication.fragments;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ltrsoft.rajashtanuserapplication.R;

import java.util.Random;

public class ForgetPassword extends Fragment {
 public ForgetPassword() {  }
    private int randomNum;
    EditText edtPhone, edtOTP;
    Button verifyOTPBtn, generateOTPBtn;
    String verificationId;
    ProgressBar progressBar;
    TextView resend_otp;
    private static final int REQUEST_SEND_SMS = 1000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);


        edtPhone = view.findViewById(R.id.phone_number);
        edtOTP = view.findViewById(R.id.otp);
        generateOTPBtn = view.findViewById(R.id.send_otp);
        verifyOTPBtn = view.findViewById(R.id.submit_otp);
        resend_otp = view.findViewById(R.id.resend_otp);
        progressBar = view.findViewById(R.id.progressbar);

        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                    Toast.makeText(getContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = "+91" + edtPhone.getText().toString();
                    generateOtp(phone);
//                    sendVerificationCode(phone);
                }
            }
        });


        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(getContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {

                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
        return view;
    }

    private void verifyCode(String code) {

        int c=Integer.parseInt(code);
        if (c==randomNum){
            Toast.makeText(getContext(), "Verification Succesful", Toast.LENGTH_SHORT).show();
//            ChangePassword changePassword = new ChangePassword();
//            Bundle bundle = new Bundle();
//            changePassword.setArguments(bundle);
//            bundle.putString("mobno",edtPhone.getText().toString());
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.session_container,changePassword)
//                    .addToBackStack(null)
//                    .commit();

        }
        else {
            Toast.makeText(getContext(), "Verification UnSuccesful", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateOtp(String phone) {
        randomNum = getRandomNumber(1000, 9999);
        String otpString = String.format("%06d", randomNum);

        //  give the permission and sending massage on number
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sendSMS(edtPhone.getText().toString(), otpString);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
        }

    }

    private void sendSMS(String number, String otpString) {
        SmsManager smsManager = SmsManager.getDefault();
        try {
            smsManager.sendTextMessage(number, null, otpString, null, null);
            Toast.makeText(getContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "SMS failed, please try again later.", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }

    }
    private static int getRandomNumber(int minValue, int maxValue) {
        Random random = new Random();
        // nextInt generates a random integer between 0 (inclusive) and the specified bound (exclusive)
        return random.nextInt(maxValue - minValue) + minValue;
    }

}