package com.ltrsoft.rajashtanuserapplication.utils;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.FileInputStream;
import java.io.IOException;

import io.grpc.netty.shaded.io.netty.internal.tcnative.AsyncTask;

public class LanguageTranslation {

        String  lang;
        TranslateOptions options;
        Translate translat;
        String textToTranslate,translatedText;
        public LanguageTranslation(Activity activity) {

                try {
                        String filelang = "language.txt";
                        FileInputStream fin = activity.openFileInput(filelang);
                        int a;
                        StringBuilder temp = new StringBuilder();
                        while ((a = fin.read()) != -1) {
                                temp.append((char) a);
                        }
                        this.lang=temp.toString();
                        fin.close();
                } catch (IOException e) {
//                        Toast.makeText(activity, "error = "+e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                }
             this.options = TranslateOptions.newBuilder().setApiKey("AIzaSyBgppIX4LGhGETm5dp0xPWNEqnFfIPmnOo").build();
              this.translat= options.getService();
        }
        public synchronized String translateTextView(TextView textView){
                Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                textToTranslate= textView.getText().toString();
                                System.out.println("translate dwsatext"+textToTranslate);
                                Translation translation = translat.translate(textToTranslate, com.google.cloud.translate.Translate.TranslateOption.targetLanguage(lang));
                                 translatedText = translation.getTranslatedText();
                                 System.out.println("translated text"+translatedText);
                        }
                });
                thread.start();

                return  translatedText;
        }
        public String translateEditText(EditText textView){
                String textToTranslate = textView.getText().toString();
                Translation translation = translat.translate(textToTranslate, com.google.cloud.translate.Translate.TranslateOption.targetLanguage(lang));
                String translatedText = translation.getTranslatedText();
                return  translatedText;
        }
        public String translateButton(Button textView){
                String textToTranslate = textView.getText().toString();
                Translation translation = translat.translate(textToTranslate, com.google.cloud.translate.Translate.TranslateOption.targetLanguage(lang));
                String translatedText = translation.getTranslatedText();
                return  translatedText;
        }
}
