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
import android.util.Log;
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

import java.io.FileOutputStream;

public class ComplaintDetailFragment extends Fragment {
    public ComplaintDetailFragment() {
    }

    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9;
    private Button button;
    private static final int REQUESTCODE =10000 ;
    private static final String CHANNELID = "20000";
    private static final int NOTIFICATIONID =3000 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.complaint_detail_fragment, container, false);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Complaint History Detail ");
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
        t1.setText(bundle.getString("complain_name"));
        t2.setText(bundle.getString("crime_type"));
        t3.setText(bundle.getString("cid"));
        t4.setText(bundle.getString("description"));
        t5.setText(bundle.getString("place"));
        t6.setText(bundle.getString("complain_name"));
        t7.setText(bundle.getString("place"));
        t8.setText(bundle.getString("description"));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "PDF Download", Toast.LENGTH_SHORT).show();
                createPdf();
            }
        });


        return view;
    }

    private void createPdf() {
        Document document = new Document();

        try {
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/complaint_detail.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            Paragraph paragraph = new Paragraph();
            paragraph.setAlignment(Paragraph.ALIGN_CENTER);
            paragraph.add(new Paragraph("                                        RAJASTHAN STATE POLICE: " ));
            paragraph.add(new Paragraph("                                        Police Station : Jaipur Polce Station "));
            paragraph.add(new Paragraph(" -------------------------------------------------------------------------------------------------------- " ));
            paragraph.add(new Paragraph("complain_name: " + t1.getText().toString()));
            paragraph.add(new Paragraph("crime_type  " + t2.getText().toString()));
            paragraph.add(new Paragraph("cid: " + t3.getText().toString()));
            document.add(paragraph);
            document.add(new Paragraph("place " + t4.getText().toString()));
            document.add(new Paragraph("status " + t5.getText().toString()));
            paragraph.add(new Paragraph("\n\n\n\n"));

            document.add(new Paragraph("Note:It is Auto Downloadable format of Complaint . Please check the current state of the document."));

            document.close();
//            sendNotification(filePath);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(filePath), "application/pdf");
            startActivity(intent);
         //   Toast.makeText(getContext(), "PDF created successfully: " + filePath, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error creating PDF"+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("pdf error",e.toString());

        }

    }
//    private void sendNotification(String string) {
//        Bitmap largeIcon =getBitMap(ResourcesCompat.getDrawable(getResources(),R.drawable.complaint,null));
//        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
//                .bigPicture(largeIcon)
//                .bigLargeIcon(largeIcon)
//                .setSummaryText("File created  ")
//                .setBigContentTitle("Pdf craeted");
//
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
//                    .setContentText("new messege ")
//                    .setStyle(bigPictureStyle)
//                    .setContentIntent(pi)
//                    .setSubText("new messege from ")
//                    .setChannelId(CHANNELID)
//                    .build();
//            nm.notify(NOTIFICATIONID,notification);
//        }
//        else {
//            notification = new Notification.Builder(getContext())
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
//        BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//        return largeIcon;
//    }

}