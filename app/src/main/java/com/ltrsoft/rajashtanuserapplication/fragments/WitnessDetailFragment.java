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
import com.ltrsoft.rajashtanuserapplication.classes.WitnessClass;
import com.ltrsoft.rajashtanuserapplication.interfaces.UserCallBack;
import com.ltrsoft.rajashtanuserapplication.model.Witnessdeo;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.util.ArrayList;

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
            String witness_id = bundle.getString("witness_id");
//            Toast.makeText(getContext(), "witness id", Toast.LENGTH_SHORT).show();

            Witnessdeo witnessdeo = new Witnessdeo();
            witnessdeo.getWitness(witness_id, getContext(), new UserCallBack() {
                @Override
                public void userSuccess(Object object) {
                    ArrayList<WitnessClass>list = (ArrayList<WitnessClass>) object;
                    Toast.makeText(getContext(), "size = "+list.size(), Toast.LENGTH_SHORT).show();
                    for (int i = 0 ; i < list.size() ; i ++){
                        WitnessClass witnessClass = list.get(i);
                        wfname.setText(witnessClass.getComplaint_witness_fname());
                        wmname.setText(witnessClass.getComplaint_witness_mname());
                        wlname.setText( witnessClass.getComplaint_witness_lname());
                        waddress.setText( witnessClass.getComplaint_witness_address() );
                        wdob.setText( witnessClass.getComplaint_witness_dob());
                        wgender.setText( witnessClass.getComplaint_witness_gender());
                        wsub.setText( witnessClass.getComplaint_witness_mobile() );
                        email.setText(witnessClass.getComplaint_witness_email());
                        city_name.setText( witnessClass.getCity_name());
                        country_name.setText( witnessClass.getCountry_name());
                        state_name.setText( witnessClass.getState_name());
                        district_name.setText( witnessClass.getDistrict_name());
                    }
                }
                @Override
                public void userError(String error) {
                    Toast.makeText(getContext(), "error = "+error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
//            String imageUrl = bundle.getString("imag");
//            if (imageUrl != null) {
//                Picasso.get().load(imageUrl).into(imageView);
//            }
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

