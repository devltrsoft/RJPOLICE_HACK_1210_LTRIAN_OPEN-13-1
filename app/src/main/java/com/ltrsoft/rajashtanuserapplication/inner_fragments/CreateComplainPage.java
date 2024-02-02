package com.ltrsoft.rajashtanuserapplication.inner_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;
//import com.ltrsoft.rajsthan_user_app.R;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.fragments.AddComplaint;
import com.ltrsoft.rajashtanuserapplication.fragments.AddEvidence;
import com.ltrsoft.rajashtanuserapplication.fragments.AddSuspect;
import com.ltrsoft.rajashtanuserapplication.fragments.AddVictim;
import com.ltrsoft.rajashtanuserapplication.fragments.AddWitness;

import java.io.FileInputStream;
import java.io.IOException;

public class CreateComplainPage extends Fragment {

    public CreateComplainPage() {    }
    private CardView addcomplain,addsuspect,addevidence,addwitness,addvictim;
    private TextView complaint_txt,evidence_txt,suspect_txt,witness_txt,victim_txt;

    private String lang,translatedText,translatedText1,translatedText2,translatedText3,translatedText4,translatedText5;

    String textToTranslate,textToTranslate1,textToTranslate2,textToTranslate3,textToTranslate4,textToTranslate5;

    private String filelang = "language.txt";
    StringBuilder output = new StringBuilder();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.create_complain_page, container, false);
        addcomplain = view.findViewById(R.id.Addcompalint);
        addsuspect = view.findViewById(R.id.Addsuspect);
        addevidence = view.findViewById(R.id.Addevidance);
        addwitness = view.findViewById(R.id.Addwitness);
        addvictim = view.findViewById(R.id.Addvictim);

        complaint_txt=view.findViewById(R.id.complaint_txt);
        evidence_txt=view.findViewById(R.id.evidence_txt);
        suspect_txt=view.findViewById(R.id.suspect_txt);
        witness_txt=view.findViewById(R.id.witness_txt);
        victim_txt=view.findViewById(R.id.victim_txt);

//        language();
// Access the hosting activity and get the ActionBar
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Complaint ");
        }

        addcomplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComplaint addComplaint = new AddComplaint();
                 loadfragment(addComplaint);
            }
        });


        addsuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSuspect addSuspect=new AddSuspect();
                loadfragment(addSuspect);
            }
        });

        addevidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 AddEvidence addEvidence = new AddEvidence();
                loadfragment(addEvidence);
            }
        });

        addwitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddWitness addWitness = new AddWitness();
                loadfragment(addWitness);
            }
        });
        addvictim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVictim addVictim = new AddVictim();
                loadfragment(addVictim);
            }
        });
        return view;
    }

//    private void language() {
//        try {
//            FileInputStream fin = getActivity().openFileInput(filelang);
//            int a;
//            StringBuilder temp = new StringBuilder();
//            while ((a = fin.read()) != -1) {
//                temp.append((char) a);
//            }
//            lang=temp.toString();
//            fin.close();
//        } catch (IOException e) {
//            Toast.makeText(getContext(), "error = "+e.toString(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//
//        if (lang!=null){
//
//            TranslateOptions options = TranslateOptions.newBuilder().setApiKey("AIzaSyBgppIX4LGhGETm5dp0xPWNEqnFfIPmnOo").build();
//            Translate translate = options.getService();
//
//            Thread gfgThread=new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    try {
//                        textToTranslate = complaint_txt.getText().toString();
//                        Translation translation = translate.translate(textToTranslate, Translate.TranslateOption.targetLanguage(lang));
//                        translatedText = translation.getTranslatedText();
//
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                complaint_txt.setText(translatedText);
//                            }
//                        });
//
//                        textToTranslate1 = suspect_txt.getText().toString();
//                        Translation translation1 = translate.translate(textToTranslate1, Translate.TranslateOption.targetLanguage(lang));
//                        translatedText1 = translation1.getTranslatedText();
//
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    suspect_txt.setText(translatedText1);
//                                }
//                            });
//
//                            textToTranslate2=evidence_txt.getText().toString();
//                            Translation translation2=translate.translate(textToTranslate2,Translate.TranslateOption.targetLanguage(lang));
//                            translatedText2=translation2.getTranslatedText();
//
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                   evidence_txt.setText(translatedText2);
//                                }
//                            });
//
//                            textToTranslate3=witness_txt.getText().toString();
//                            Translation translation3=translate.translate(textToTranslate3,Translate.TranslateOption.targetLanguage(lang));
//                            translatedText3=translation3.getTranslatedText();
//
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    witness_txt.setText(translatedText3);
//                                }
//                            });
//
//                            textToTranslate4=victim_txt.getText().toString();
//                            Translation translation4=translate.translate(textToTranslate4,Translate.TranslateOption.targetLanguage(lang));
//                            translatedText4=translation4.getTranslatedText();
//
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    victim_txt.setText(translatedText4);
//                                }
//                            });
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            gfgThread.start();
//
//        }
//
//    }

    private void loadfragment(Fragment fragment) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containermain,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}