package com.ltrsoft.rajashtanuserapplication.fragments;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.FirClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Firdeo;

import java.io.FileOutputStream;

public class Fir_History_Detail extends Fragment {
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9;
    private TextView fir_txt,desc_txt,date_txt,place_txt,witness,victim;
    private Button button;
    private static final int REQUESTCODE =10000 ;
    private static final String CHANNELID = "20000";
    private static final int NOTIFICATIONID =3000 ;
    public Fir_History_Detail() {    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fir__history__detail, container, false);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Fir History Detail ");
        }

        t1 = view.findViewById(R.id.cname);
        t2 = view.findViewById(R.id.cdes);
        t3 = view.findViewById(R.id.idate);
        t4 = view.findViewById(R.id.place);
        t5 = view.findViewById(R.id.wname);


        button = view.findViewById(R.id.pdf);


        t6 = view.findViewById(R.id.vname);
        t7 = view.findViewById(R.id.snme);
        t8 = view.findViewById(R.id.evidance);
        t9 = view.findViewById(R.id.status);
        Bundle bundle = getArguments();
        String  fir_id = bundle.getString("fired");
        Firdeo firdeo = new Firdeo();
        firdeo.getFir(fir_id, getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                FirClass firClass = (FirClass)object;
                t3.setText(bundle.getString("suspect"));
               t2.setText(bundle.getString("complain_name"));
               t2.setText(firClass.getComplaint_subject());
             t4.setText(bundle.getString("complaintOFir_name"));
             t4.setText(firClass.getComplaintORfir_name());
             t5.setText(firClass.getStatus_name());
             t6.setText(firClass.getSuspect_address());
             t7.setText(firClass.getInvestigation_witness_fname());
            t8.setText(firClass.getVictim_fname());
            t9.setText(firClass.getSuspect_fname());
            }

            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "some error while loading fir", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(), "PDF Download", Toast.LENGTH_SHORT).show();
                createPdf();
            }
        });
     return view;
    }
    private void createPdf() {
        Document document = new Document();

        try {
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Fir details.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Paragraph("                                        RAJASTHAN STATE POLICE: " ));
            paragraph.add(new Paragraph("                                        Police Station : Jaipur Polce Station "));
            paragraph.add(new Paragraph(" -------------------------------------------------------------------------------------------------------- " ));
            document.add(paragraph);
            document.add(new Paragraph("firid Name: " + t1.getText().toString()));
            document.add(new Paragraph("suspect  " + t2.getText().toString()));
            document.add(new Paragraph("complain_name: " + t3.getText().toString()));
            document.add(new Paragraph("complaintOFir_name " + t4.getText().toString()));
            document.add(new Paragraph("suspect_address " + t5.getText().toString()));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Note:It is Auto Downloadable format of Complaint . Please check the current state of the document."));
            document.close();
        //    sendNotification(filePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/pdf");
            startActivity(intent);
            Toast.makeText(getContext(), "PDF created successfully: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong ", Toast.LENGTH_SHORT).show();
            System.out.println("Error "+e.toString());
        }

    }
}