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
import android.widget.ImageView;
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
import com.ltrsoft.rajashtanuserapplication.classes.SuspectClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.SuspectDeo;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class Suspect_History_Detail extends Fragment {
    private static final int REQUESTCODE =10000 ;
    private static final String CHANNELID = "20000";
    private static final int NOTIFICATIONID =3000 ;

    private ImageView back_button,edit,download,Suspect_pic;
    private ImageView sev;
    private TextView sid,suspect_addhar,Suspect_full_name,suspect_address,suspect_dob,suspect_gender,suspect_mobile
            ,suspect_email,suspect_country,suspect_state,suspect_district,suspect_city;

    public Suspect_History_Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.suspect__history__detail, container, false);
        sid=view.findViewById(R.id.suspect_name);
        suspect_addhar=view.findViewById(R.id.suspect_addhar);
        Suspect_full_name=view.findViewById(R.id.Suspect_full_name);
        suspect_address=view.findViewById(R.id.suspect_address);
        suspect_dob=view.findViewById(R.id.suspect_dob);
        suspect_gender=view.findViewById(R.id.suspect_gender);
        suspect_mobile=view.findViewById(R.id.suspect_mobile);
        suspect_email=view.findViewById(R.id.suspect_email);
        suspect_country=view.findViewById(R.id.suspect_country);
        suspect_state=view.findViewById(R.id.suspect_state);
        suspect_district=view.findViewById(R.id.suspect_district);
        suspect_city=view.findViewById(R.id.suspect_city);


        sev=view.findViewById(R.id.downloadButton);
        Suspect_pic=view.findViewById(R.id.Suspect_pic);

        // Access the hosting activity and get the ActionBar
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Suspect ");
        }
        Bundle bundle = getArguments();
        String suspect_id = String.valueOf(bundle.getInt("cid"));
        SuspectDeo suspectDeo = new SuspectDeo();
        suspectDeo.getSuspect(suspect_id, getContext(), new UserCallBack() {
            @Override
            public void userSuccess(Object object) {
                ArrayList<SuspectClass>list = (ArrayList<SuspectClass>) object;
                for (int i = 0 ; i < list.size() ; i++){
                    SuspectClass suspectClass = list.get(i);
                    suspect_dob.setText(suspectClass.getDob());
                    Suspect_full_name.setText(suspectClass.getFname());
                    suspect_address.setText(suspectClass.getStname());
                    suspect_gender.setText(suspectClass.getGender());
                    suspect_mobile.setText(suspectClass.getMobile());
                    suspect_email.setText(suspectClass.getEmail());
                    suspect_country.setText(suspectClass.getCountyname());
                    suspect_district.setText(suspectClass.getDistname());
                    suspect_state.setText(suspectClass.getStname());
                    suspect_city.setText(suspectClass.getCityname());
                }
            }
            @Override
            public void userError(String error) {
                Toast.makeText(getContext(), "error "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
      sev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPdf();
            }

            private void createPdf() {

                Document document = new Document();

                try {
                    String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/suspect_Detail.pdf";
                    PdfWriter.getInstance(document, new FileOutputStream(filePath));

                    document.open();
                    Paragraph paragraph = new Paragraph();
                    paragraph.add(new Paragraph("                                        RAJASTHAN STATE POLICE: " ));
                    paragraph.add(new Paragraph("                                        Police Station : Jaipur Polce Station "));
                    paragraph.add(new Paragraph(" -------------------------------------------------------------------------------------------------------- " ));
                    document.add(paragraph);
                    document.add(new Paragraph("full_name: " + sid.getText().toString()));
                    document.add(new Paragraph("suspect_addhar  " + suspect_dob.getText().toString()));
                    document.add(new Paragraph("suspect_dob: " + Suspect_full_name.getText().toString()));
                    document.add(new Paragraph("suspect_gender " + suspect_gender.getText().toString()));
                    document.add(new Paragraph("suspect_mobile " + suspect_mobile.getText().toString()));
                    document.add(new Paragraph("suspect_country " + suspect_email.getText().toString()));
                    document.add(new Paragraph("suspect_state " + suspect_state.getText().toString()));
                    document.add(new Paragraph("suspect_city " + suspect_city.getText().toString()));
                    document.add(new Paragraph("suspect_district " + suspect_district.getText().toString()));
                    document.add(new Paragraph("Note:It is Auto Downloadable format of Complaint . Please check the current state of the document."));
                    document.close();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(filePath), "application/pdf");
                    startActivity(intent);
                  //  sendNotification(filePath);
                 //   Toast.makeText(getContext(), "PDF created successfully: " + filePath, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error creating PDF", Toast.LENGTH_SHORT).show();
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
//    public Bitmap getBitMap(Drawable drawable){
//
//        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//        return largeIcon;
//    }
}