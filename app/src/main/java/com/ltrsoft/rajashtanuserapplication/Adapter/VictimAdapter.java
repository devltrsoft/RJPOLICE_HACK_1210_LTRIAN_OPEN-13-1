package com.ltrsoft.rajashtanuserapplication.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.VictimClass;
import com.ltrsoft.rajashtanuserapplication.fragments.Victim_History_Detail;

import java.util.ArrayList;

public class VictimAdapter extends RecyclerView.Adapter<VictimAdapter.ViewHolder> {
    public ArrayList<VictimClass> list;
    private AdapterView.OnItemClickListener mListener;
    public VictimAdapter(ArrayList<VictimClass> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.victimecard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VictimClass victimClass = list.get(position);
        holder.name.setText(victimClass.getComplaint_victim_fname()+victimClass.getComplaint_victim_mname()+victimClass.getComplaint_victim_lname());
        holder.date.setText(victimClass.getGender());
        holder.id.setText(victimClass.getCmp_id());

        holder.suspectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
//
                Victim_History_Detail v1=new Victim_History_Detail();
                AppCompatActivity activity=(AppCompatActivity)v.getContext();

                bundle.putString("cmp_id", victimClass.getCmp_id());
//                bundle.putString("complaint_victim_fname", victimClass.getComplaint_victim_fname());
//                bundle.putString("complaint_victim_mname", victimClass.getComplaint_victim_mname());
//                bundle.putString("complaint_victim_lname", victimClass.getComplaint_victim_lname());
//                bundle.putString("address",victimClass.getAddress());
//                bundle.putString("gender",victimClass.getGender());
//                bundle.putString("aadhar",victimClass.getAadhar());
//                bundle.putString("photo",victimClass.getPhoto());
//                bundle.putString("dob",victimClass.getDob());
//                bundle.putString("mobile",victimClass.getMobile());
//                bundle.putString("state_name",victimClass.getState_name());
//                bundle.putString("district_name",victimClass.getDistrict_name());
//                bundle.putString("country_name",victimClass.getCountry_name());
//                bundle.putString("city_name",victimClass.getCity_name());
                v1.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.containermain, v1).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, id;
        private CardView suspectCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.dob);
            id = itemView.findViewById(R.id.idd);

            suspectCard = itemView.findViewById(R.id.crd);
        }
    }
}
