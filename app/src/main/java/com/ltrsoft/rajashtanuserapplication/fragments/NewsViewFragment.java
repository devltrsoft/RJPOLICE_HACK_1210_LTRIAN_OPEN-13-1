package com.ltrsoft.rajashtanuserapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ltrsoft.rajashtanuserapplication.R;


public class NewsViewFragment extends Fragment {

    private ImageView newsi;
    private TextView newsDescription1,news_title1;

    public NewsViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_view_fragment, container, false);


        newsi = view.findViewById(R.id.newsi);
        newsDescription1 = view.findViewById(R.id.news_description1);
        news_title1=view.findViewById(R.id.news_title1);


        Bundle bundle = getArguments();
       newsi.setImageResource(R.drawable.news1);

        String description=bundle.getString("news_description");
        String titile=bundle.getString("news_title");
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" "+titile);
        }
        newsDescription1.setText(description);
        news_title1.setText(titile);
        return view;
    }
}
