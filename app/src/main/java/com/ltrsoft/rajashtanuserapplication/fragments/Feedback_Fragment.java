package com.ltrsoft.rajashtanuserapplication.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.R;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Feedback_Fragment extends Fragment {

    private RatingBar satisfaction, trustConfidence, qualityOfService, fairness, timeTaken;
    private Button submit;
    private EditText bribeDescription;
    int rate;
    StringBuilder output = new StringBuilder();

    private static final String FEEDBACK_API_URL = "https://rj.ltr-soft.com/public/police_api/feedback/create_feedback.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_, container, false);

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Feedback ");
        }
        try {
            FileInputStream fileInputStream = getActivity().openFileInput("mytextfile.txt");
            InputStreamReader inputReader = new InputStreamReader(fileInputStream);
            char[] buffer = new char[1024];
            int read;
            while ((read = inputReader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            Toast.makeText(getContext(), "your fiile ="+output.toString(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        satisfaction = view.findViewById(R.id.satisfaction);
        trustConfidence = view.findViewById(R.id.confidance);
        bribeDescription = view.findViewById(R.id.bribe_descryption);
        qualityOfService = view.findViewById(R.id.quality_of_service);
        fairness = view.findViewById(R.id.fairness);
        timeTaken = view.findViewById(R.id.time_taken);
        submit = view.findViewById(R.id.submit_feedback);
        LayerDrawable layerDrawable = (LayerDrawable) satisfaction.getProgressDrawable();
        layerDrawable.getDrawable(2).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_ATOP);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "FeedBack Submitted "+qualityOfService.getRating(), Toast.LENGTH_SHORT).show();
                rate= (int) satisfaction.getRating();
                sendFeedback();
            }
        });

        return view;
    }

    private void sendFeedback() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, FEEDBACK_API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error: " + error.toString());
                        Toast.makeText(getContext(), "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Add feedback data to the POST parameters
                Map<String, String> params = new HashMap<>();
                params.put("satisfaction", String.valueOf(satisfaction.getRating()));
                params.put("trust", String.valueOf(trustConfidence.getRating()));
                params.put("quality_of_service", String.valueOf(qualityOfService.getRating()));
                params.put("fairness", String.valueOf(fairness.getRating()));
                params.put("time_taken_to_resoive_complaint", String.valueOf(timeTaken.getRating()));
                params.put("asked_for_brief", bribeDescription.getText().toString());
                params.put("user_id", output.toString());  // Replace with the actual user ID
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
