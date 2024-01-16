package com.ltrsoft.rajashtanuserapplication.inner_fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.utils.LanguageTranslation;

import org.apache.commons.codec.language.bm.Lang;

import java.io.FileInputStream;
import java.io.IOException;

public class EmergencyPage extends Fragment {
    public EmergencyPage() {}
    private ActionBar actionBar;
    private CardView ambulance,whelp,fire,policastation,cybercrime,bombsquad;
    private TextView ambulance_txt,woman_txt,firebrigate_txt,policeno_txt,bombsquad_txt,cybercrime_txt,safety_txt;

    String textToTranslate,textToTranslate1,textToTranslate2,textToTranslate3,textToTranslate4,textToTranslate5,textToTranslate6;
    private String lang,translatedText,translatedText1,translatedText2,translatedText3,translatedText4,translatedText5,translatedText6;

    private String filelang = "language.txt";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emergency_page, container, false);
        ambulance = view . findViewById(R.id.Ambulance);
        whelp = view . findViewById(R.id.womenhelpline);
        fire = view . findViewById(R.id.Firman);
        cybercrime = view . findViewById(R.id.cybercrime);
        policastation = view . findViewById(R.id.policeno);
        bombsquad = view.findViewById(R.id.bombsquad);
        actionBar= ((AppCompatActivity) requireActivity()).getSupportActionBar();

        ambulance_txt = view.findViewById(R.id.amb);
        woman_txt = view.findViewById(R.id.womenhelpline_txt);
        firebrigate_txt = view.findViewById(R.id.Firman_txt);
        policeno_txt = view.findViewById(R.id.policeno_txt);
        bombsquad_txt = view.findViewById(R.id.bombsquad_txt);
        cybercrime_txt = view.findViewById(R.id.cybercrime_txt);
        safety_txt=view.findViewById(R.id.disaster_txt);

        checkLamguage();


        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amb="tel:"+"102";//102
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse(amb));

                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.CALL_PHONE},1);
                }
            }
        });
        whelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wh="tel:"+"1091";//1091

                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse(wh));

                if(ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.CALL_PHONE},1);
                }
            }
        });

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fb="tel:"+"101";//101
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse(fb));

                if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.CALL_PHONE},1);
                }
            }
        });

        policastation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ps="tel:"+"100";//100
                Intent intent= new Intent(Intent.ACTION_CALL,Uri.parse(ps));

                if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.CALL_PHONE},1);
                }
            }
        });

        cybercrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em= "tel:"+"1930";//108
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse(em));

                if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.CALL_PHONE},1);
                }
            }
        });

        bombsquad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em= "tel:"+"9212111";//108
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse(em));

                if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(intent);
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.CALL_PHONE},1);
                }
            }
        });
        return view;
    }

    private void checkLamguage() {
        try {
            FileInputStream fin = getActivity().openFileInput(filelang);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1) {
                temp.append((char) a);
            }
            lang=temp.toString();
            fin.close();
        } catch (IOException e) {
            Toast.makeText(getContext(), "error = "+e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (lang != null) {
            TranslateOptions options = TranslateOptions.newBuilder().setApiKey("AIzaSyBgppIX4LGhGETm5dp0xPWNEqnFfIPmnOo").build();
            Translate translate = options.getService();

            Thread gfgThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {


                        textToTranslate = actionBar.getTitle().toString();
                        Translation translation0 = translate.translate(textToTranslate, Translate.TranslateOption.targetLanguage(lang));
                        translatedText = translation0.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                actionBar.setTitle(translatedText);
                            }
                        });

                        textToTranslate = ambulance_txt.getText().toString();
                        Translation translation = translate.translate(textToTranslate, Translate.TranslateOption.targetLanguage(lang));
                        translatedText = translation.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ambulance_txt.setText(translatedText);
                            }
                        });



                        textToTranslate1 = woman_txt.getText().toString();
                        Translation translation1 = translate.translate(textToTranslate1, Translate.TranslateOption.targetLanguage(lang));
                        translatedText1 = translation1.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                woman_txt.setText(translatedText1);

                            }
                        });

                        textToTranslate2 = firebrigate_txt.getText().toString();
                        Translation translation2 = translate.translate(textToTranslate2, Translate.TranslateOption.targetLanguage(lang));
                        translatedText2 = translation2.getTranslatedText();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                firebrigate_txt.setText(translatedText2);
                            }
                        });

                        textToTranslate3 = policeno_txt.getText().toString();
                        Translation translation3 = translate.translate(textToTranslate3, Translate.TranslateOption.targetLanguage(lang));
                        translatedText3 = translation3.getTranslatedText();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                policeno_txt.setText(translatedText3);
                            }
                        });

                        textToTranslate4 = bombsquad_txt.getText().toString();
                        Translation translation4 = translate.translate(textToTranslate4, Translate.TranslateOption.targetLanguage(lang));
                        translatedText4 = translation4.getTranslatedText();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bombsquad_txt.setText(translatedText4);
                            }
                        });
                        textToTranslate5 = cybercrime_txt.getText().toString();
                        Translation translation5 = translate.translate(textToTranslate5, Translate.TranslateOption.targetLanguage(lang));
                        translatedText5 = translation5.getTranslatedText();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cybercrime_txt.setText(translatedText5);
                            }
                        });

                        textToTranslate6 = safety_txt.getText().toString();
                        Translation translation6 = translate.translate(textToTranslate6, Translate.TranslateOption.targetLanguage(lang));
                        translatedText6 = translation6.getTranslatedText();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                safety_txt.setText(translatedText6);
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            gfgThread.start();
        }

    }




    private void loadfragment(Fragment fragment) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containermain,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}