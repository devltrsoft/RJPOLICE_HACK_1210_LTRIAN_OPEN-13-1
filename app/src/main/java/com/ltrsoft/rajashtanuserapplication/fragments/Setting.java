package com.ltrsoft.rajashtanuserapplication.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.ltrsoft.rajashtanuserapplication.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class Setting extends Fragment {
    public Setting() {}
    private Switch aSwitch;
    private CardView cardView;
    private TextView contact_tv;
    private String filelang = "language.txt";
   private String lang;
    private String translatedText, translatedText1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting, container, false);

        aSwitch = view.findViewById(R.id.notification_switch);
        contact_tv = view.findViewById(R.id.contact_tv);
        cardView  = view.findViewById(R.id.language);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguage();
            }
        });

        // Access the hosting activity and get the ActionBar
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Setting ");
        }


        contact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString="https://ltr-soft.com";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                startActivity(intent);
            }
        });

        return view;
    }

    private void showLanguage() {

            final String[] list = {"Assamee", "Bengali", "hindi", "kannada", "Marathi", "tamil", "telgu", "Urdu", "English"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose Language...");


            builder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        setLocal("as");
                        lang = "as";
                        getActivity().recreate();
                    } else if (i == 1) {
                        setLocal("bn");
                        lang = "bn";
                        getActivity().recreate();
                    } else if (i == 2) {
                        setLocal("hi");
                        lang = "hi";
                        getActivity().recreate();
                    } else if (i == 3) {
                        setLocal("kn");
                        lang = "kn";
                        getActivity().recreate();
                    } else if (i == 4) {
                        setLocal("mr");
                        lang = "mr";
                        getActivity().recreate();
                    } else if (i == 5) {
                        setLocal("ta");
                        lang = "ta";
                        getActivity().recreate();
                    } else if (i == 6) {
                        setLocal("te");
                        lang = "te";
                        getActivity().recreate();
                    } else if (i == 7) {
                        setLocal("ur");
                        lang = "ur";
                        getActivity().recreate();
                    } else if (i == 8) {
                        setLocal("en");
                        lang = "en";
                        getActivity().recreate();
                    }

                    try {
                        FileOutputStream fos = getActivity().openFileOutput(filelang, Context.MODE_PRIVATE);
                        String data = lang;
                        fos.write(data.getBytes());
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialogInterface.dismiss();

                }
            });

            AlertDialog build = builder.create();
            build.show();
        }

        private void setLocal(String bhasa) {
            Locale local = new Locale(bhasa);
            Locale.setDefault(local);
            Configuration config = new Configuration();
            config.locale = local;
            getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
            SharedPreferences.Editor edits = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE).edit();
            edits.putString("my_lang", bhasa);
            edits.apply();
        }

        public void loadlocale() {
            SharedPreferences pref = getActivity().getSharedPreferences("Setting", Activity.MODE_PRIVATE);
            String language = pref.getString("my_lang", "");
            setLocal(language);
        }
}