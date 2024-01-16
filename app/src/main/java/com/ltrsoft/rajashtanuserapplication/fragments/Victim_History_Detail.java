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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ltrsoft.rajashtanuserapplication.R;

import java.io.FileOutputStream;

public class Victim_History_Detail extends Fragment {
    private static final int REQUESTCODE =10000 ;
    private static final String CHANNELID = "20000";
    private static final int NOTIFICATIONID =3000 ;

    private TextView full_name,suspect_addhar,Suspect_full_name,suspect_address,suspect_dob,suspect_gender,suspect_mobile
            ,suspect_email,suspect_country,suspect_state,suspect_district,suspect_city;

    Button button;

    public Victim_History_Detail() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.victim__history__detail, container, false);
        full_name  =view.findViewById(R.id.full_name);
        suspect_addhar=view.findViewById(R.id.addhar);
        suspect_address=view.findViewById(R.id.address);
        suspect_dob=view.findViewById(R.id.dob);
        suspect_gender=view.findViewById(R.id.gender);
        suspect_mobile=view.findViewById(R.id.mobile);
        suspect_email=view.findViewById(R.id.email);
        suspect_country=view.findViewById(R.id.country);
        suspect_state=view.findViewById(R.id.state);
        suspect_district=view.findViewById(R.id.district);
        suspect_city=view.findViewById(R.id.city);
        Bundle bundle = getArguments();


        button=view.findViewById(R.id.downloadButton);

        full_name.setText(bundle.getString("complaint_victim_fname"+bundle.getString("complaint_victim_mname")+bundle.getString("complaint_victim_lname")));
        suspect_addhar.setText(bundle.getString("aadhar"));
        suspect_dob.setText(bundle.getString("dob"));
        suspect_gender.setText(bundle.getString("gender"));
        suspect_mobile.setText(bundle.getString("mobile"));
        suspect_country.setText(bundle.getString("country_name"));
        suspect_state.setText(bundle.getString("state_name"));
        suspect_city.setText(bundle.getString("city_name"));
        suspect_district.setText(bundle.getString("district_name"));

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Victim History Detail ");
        }
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createPdf();
            }
            private void createPdf() {

                Document document = new Document();

                try {
                    String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Victim_history detail.pdf";
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));

                    document.open();
                    Paragraph paragraph = new Paragraph();
                    paragraph.add(new Paragraph("                                        RAJASTHAN STATE POLICE: " ));
                    paragraph.add(new Paragraph("                                        Police Station : Jaipur Polce Station "));
                    paragraph.add(new Paragraph(" -------------------------------------------------------------------------------------------------------- " ));
                    document.add(paragraph);
                    document.add(new Paragraph("full_name: " + full_name.getText().toString()));
                    document.add(new Paragraph("suspect_addhar  " + suspect_addhar.getText().toString()));
                    document.add(new Paragraph("suspect_dob: " + suspect_dob.getText().toString()));
                    document.add(new Paragraph("suspect_gender " + suspect_gender.getText().toString()));
                    document.add(new Paragraph("suspect_mobile " + suspect_mobile.getText().toString()));
                    document.add(new Paragraph("suspect_country " + suspect_country.getText().toString()));
                    document.add(new Paragraph("suspect_state " + suspect_state.getText().toString()));
                    document.add(new Paragraph("suspect_city " + suspect_city.getText().toString()));
                    document.add(new Paragraph("suspect_district " + suspect_district.getText().toString()));
                    document.add(new Paragraph("Note:It is Auto Downloadable format of Complaint . Please check the current state of the document."));

                    document.close();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(filePath), "application/pdf");
                    startActivity(intent);
                   // sendNotification(filePath);
                 //   Toast.makeText(getContext(), "PDF created successfully: " + filePath, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                 //   Toast.makeText(getContext(), "Error creating PDF"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

//    private void sendNotification(String string) {
//        Bitmap largeIcon =getBitMap(ResourcesCompat.getDrawable(getResources(),R.mipmap.images,null));
//        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
//                .bigPicture(largeIcon)
//                .bigLargeIcon(largeIcon)
//                .setSummaryText("File created at "+string)
//                .setBigContentTitle("Pdf craeted");
//
//        NotificationManager nm = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
//        Notification notification;
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse(string), "application/pdf");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pi = PendingIntent.getActivity(getContext(),REQUESTCODE,intent,PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_IMMUTABLE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            nm.createNotificationChannel(new NotificationChannel(CHANNELID,"my channel",NotificationManager.IMPORTANCE_HIGH));
//            notification = new Notification.Builder(getContext())
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.mipmap.images)
//                    .setContentText("new messege ")
//                    .setStyle(bigPictureStyle)
//                    .setContentIntent(pi)
//                    .setSubText("new messege from ")
//                    .setChannelId(CHANNELID)
//                    .build();
//            nm.notify(NOTIFICATIONID,notification);
//            //set.setText("success");
//            // nm.createNotificationChannel(new NotificationChannel(CHANNELID,"my channel",NotificationManager.IMPORTANCE_HIGH));
//        }
//        else {
//            notification = new Notification.Builder(getContext())
//                    .setLargeIcon(largeIcon)
//                    .setSmallIcon(R.mipmap.images)
//                    .setContentText("new messege ")
//                    .setContentIntent(pi)
//                    .setStyle(bigPictureStyle)
//                    .setSubText("new messege from ")
//                    .build();
//            nm.notify(NOTIFICATIONID,notification);
//        }
//    }
//
//    public Bitmap getBitMap(Drawable drawable){
//
//        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//        return largeIcon;
//    }
}