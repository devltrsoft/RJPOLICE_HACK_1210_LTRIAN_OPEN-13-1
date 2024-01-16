package com.ltrsoft.rajashtanuserapplication.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.ltrsoft.rajashtanuserapplication.R;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;

public class WitnessDetailFragment extends Fragment {
    private TextView wid, wfname, wmname, wlname, waddress, wdob,wgender,wsub,email;
    private TextView city_name,country_name,state_name,district_name,adhar;

    private ImageView imageView;
    private Button button;

    public WitnessDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.witness__history__detail, container, false);

       // Toast.makeText(getActivity(), "event  detail", Toast.LENGTH_SHORT).show();
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // Set the title on the ActionBar or Toolbar
        if (actionBar != null) {
            actionBar.setTitle(" Witness Detail ");
        }

        wid = v.findViewById(R.id.cidno);
        wfname = v.findViewById(R.id.fname);
        wmname =v.findViewById(R.id.mname);
        wlname=v.findViewById(R.id.lname);
        waddress = v.findViewById(R.id.address);
        wdob = v.findViewById(R.id.dob);
        wgender = v.findViewById(R.id.gender);
        wsub = v.findViewById(R.id.mobile);
        email = v.findViewById(R.id.email);
        button=v.findViewById(R.id.ctn);
        imageView = v.findViewById(R.id.Profile_pic);
        city_name = v.findViewById(R.id.city);
        country_name = v.findViewById(R.id.country);
        state_name = v.findViewById(R.id.state);
        district_name = v.findViewById(R.id.district);
        adhar = v.findViewById(R.id.addhar);
        Bundle bundle = getArguments();
        if (bundle != null) {
        wfname.setText(bundle.getString("witnessfame"));
        wmname.setText(bundle.getString("witnessfame"));
            wlname.setText(    bundle.getString("witnessmame"));
            waddress.setText(  bundle.getString("witnesslame"));
            wdob.setText(  bundle.getString("witnessfame"));
            wgender.setText(  bundle.getString("complaint_witness_gender"));
            wsub.setText(   bundle.getString("complaint_witness_mobile"));
            email.setText( bundle.getString("complaint_witness_email"));
            city_name.setText( bundle.getString("city_name"));
            country_name.setText( bundle.getString("country_name"));
            state_name.setText( bundle.getString("state_name"));
            district_name.setText( bundle.getString("district_name"));

            String imageUrl = bundle.getString("imag");
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).into(imageView);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(v.getContext(), "joined succssfully", Toast.LENGTH_SHORT).show();
                createPdf();
            }

            private void createPdf() {
                Document document = new Document();
                try {
                    String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/witnwss_detail.pdf";

                     PdfWriter.getInstance(document, new FileOutputStream(filePath));
                    document.open();
                    Paragraph paragraph = new Paragraph();
                    paragraph.add(new Paragraph("                                        RAJASTHAN STATE POLICE: " ));
                    paragraph.add(new Paragraph("                                        Police Station : Jaipur Polce Station "));
                    paragraph.add(new Paragraph(" -------------------------------------------------------------------------------------------------------- " ));
                    document.add(paragraph);
                    document.add(new Paragraph("full_name: " + wfname.getText().toString()));
                    document.add(new Paragraph("suspect_addhar  " + wmname.getText().toString()));
                    document.add(new Paragraph("suspect_dob: " + wlname.getText().toString()));
                    document.add(new Paragraph("suspect_gender " + waddress.getText().toString()));
                    document.add(new Paragraph("suspect_mobile " + wgender.getText().toString()));
                    document.add(new Paragraph("suspect_country " + wsub.getText().toString()));
                    document.add(new Paragraph("suspect_state " + email.getText().toString()));
                    document.add(new Paragraph("suspect_city " + city_name.getText().toString()));
                    document.add(new Paragraph("suspect_district " + state_name.getText().toString()));
                    document.add(new Paragraph("suspect_district " + country_name.getText().toString()));
                    document.add(new Paragraph("suspect_district " + district_name.getText().toString()));
                    document.add(new Paragraph("Note:It is Auto Downloadable format of Complaint . Please check the current state of the document."));

                    document.close();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(filePath), "application/pdf");
                    startActivity(intent);
                  //  Toast.makeText(getContext(), "PDF created successfully: " + filePath, Toast.LENGTH_SHORT).show();
                }

                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error creating PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}

