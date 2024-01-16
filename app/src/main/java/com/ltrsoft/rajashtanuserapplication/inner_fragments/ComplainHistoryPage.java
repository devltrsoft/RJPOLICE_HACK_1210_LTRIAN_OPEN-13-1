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


import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.fragments.ComplainPage;
import com.ltrsoft.rajashtanuserapplication.fragments.EvidencePage;
import com.ltrsoft.rajashtanuserapplication.fragments.FirPage;
import com.ltrsoft.rajashtanuserapplication.fragments.SuspectPage;
import com.ltrsoft.rajashtanuserapplication.fragments.WitnessFragment;

import java.io.FileInputStream;
import java.io.IOException;

public class ComplainHistoryPage extends Fragment {
    public ComplainHistoryPage() {    }
    public ActionBar actionBar;
    private CardView fir,complaint,evidence,suspect,witness,victim;

    private TextView fir_text,complaint_txt,evidence_txt,suspect_txt,witness_txt,victim_txt;

    private String lang,translatedText,translatedText1,translatedText2,translatedText3,translatedText4,translatedText5;

    String textToTranslate,textToTranslate1,textToTranslate2,textToTranslate3,textToTranslate4,textToTranslate5;

    private String filelang = "language.txt";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.complain_history_page, container, false);

        fir = view.findViewById(R.id.fir);
        complaint = view.findViewById(R.id.complaint);
        evidence = view.findViewById(R.id.evidance);
        suspect = view.findViewById(R.id.suspect);
        witness = view.findViewById(R.id.witness);
        victim=view.findViewById(R.id.victim);

        fir_text=view.findViewById(R.id.fir_text);
        complaint_txt=view.findViewById(R.id.complaint_txt);
        evidence_txt=view.findViewById(R.id.evidence_txt);
        suspect_txt=view.findViewById(R.id.suspect_txt);
        witness_txt=view.findViewById(R.id.witness_txt);
        victim_txt=view.findViewById(R.id.victim_txt);

        language();

        // Access the hosting activity and get the ActionBar
        actionBar= ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Complaint Details ");
        }

        fir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              FirPage firPage = new FirPage();
                loadfragment(firPage);
            }
        });

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplainPage complainPage = new ComplainPage();
                loadfragment(complainPage);
            }
        });

        evidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvidencePage evidencePage = new EvidencePage();
                loadfragment(evidencePage);
            }
        });

        suspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              SuspectPage suspectPage = new SuspectPage();
                loadfragment(suspectPage);
            }
        });

        witness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WitnessFragment witnessPagev = new WitnessFragment();
                loadfragment(witnessPagev);
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                VictimPage victimPage = new VictimPage();
//                loadfragment(victimPage);
            }
        });
        return view;
    }

    private void language() {
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

        if (lang!=null) {
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

                        textToTranslate = fir_text.getText().toString();
                        Translation translation = translate.translate(textToTranslate, Translate.TranslateOption.targetLanguage(lang));
                        translatedText = translation.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fir_text.setText(translatedText);
                            }
                        });

                        textToTranslate1=complaint_txt.getText().toString();
                        Translation translation1=translate.translate(textToTranslate1,Translate.TranslateOption.targetLanguage(lang));
                        translatedText1=translation1.getTranslatedText();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                complaint_txt.setText(translatedText1);
                            }
                        });

                        textToTranslate2=evidence_txt.getText().toString();
                        Translation translation2=translate.translate(textToTranslate2,Translate.TranslateOption.targetLanguage(lang));
                        translatedText2=translation2.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                evidence_txt.setText(translatedText2);
                            }
                        });

                        textToTranslate3= suspect_txt.getText().toString();
                        Translation translation3= translate.translate(textToTranslate3,Translate.TranslateOption.targetLanguage(lang));
                        translatedText3=translation3.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                suspect_txt.setText(translatedText3);
                            }
                        });

                        textToTranslate4=witness_txt.getText().toString();
                        Translation translation4=translate.translate(textToTranslate4,Translate.TranslateOption.targetLanguage(lang));
                        translatedText4=translation4.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                witness_txt.setText(translatedText4);
                            }
                        });

                        textToTranslate5=victim_txt.getText().toString();
                        Translation translation5=translate.translate(textToTranslate5,Translate.TranslateOption.targetLanguage(lang));
                        translatedText5=translation5.getTranslatedText();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                victim_txt.setText(translatedText5);
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