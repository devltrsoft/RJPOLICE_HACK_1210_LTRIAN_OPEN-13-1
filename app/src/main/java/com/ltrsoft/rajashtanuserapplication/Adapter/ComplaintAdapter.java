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
import com.ltrsoft.rajashtanuserapplication.classes.ComplaintClass;
import com.ltrsoft.rajashtanuserapplication.fragments.ComplaintDetailFragment;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {


    public ArrayList<ComplaintClass> list;

    private AdapterView.OnItemClickListener mListener;
    public ComplaintAdapter(ArrayList<ComplaintClass> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaincard,parent,false);
        return new ViewHolder(view);

      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComplaintClass invstclass = list.get(position);
        if (!list.isEmpty()) {
            System.out.println(invstclass.toString() + invstclass.getComplaint_subject());
        }
            holder.firno.setText(invstclass.getCmpid());
            holder.icrime_type.setText(invstclass.getComplaint_type_name());
            holder.icomplain_name.setText(invstclass.getComplaint_subject()+invstclass.getComplaint_description());
            holder.status.setText(invstclass.getStatus_name());
            holder.category.setText(invstclass.getComplaintORfir_name());

        holder.investigation_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComplaintDetailFragment cn=new ComplaintDetailFragment();
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
               Bundle bundle = new Bundle();
                bundle.putString("complain_id", invstclass.getComplaint_id());
//                bundle.putString("complain_name", invstclass.getComplaint_subject());
//                bundle.putString("crime_type", invstclass.getUser_address());
//                bundle.putString("cid",invstclass.getComplaint_id());
//                bundle.putString("description",invstclass.getComplaint_description());
//                bundle.putString("place",invstclass.getStatus_name());
                 cn.setArguments(bundle);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.containermain, cn
                ).addToBackStack(null).commit();


            }

        });
    }
    @Override
    public int getItemCount() {return list.size();    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView category,firno,status,icrime_type,icomplain_name;

        private CardView investigation_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.iwitness);
            status = itemView.findViewById(R.id.ivictim);
            icrime_type = itemView.findViewById(R.id.isuspect);
            icomplain_name = itemView.findViewById(R.id.icrime_type);
            firno = itemView.findViewById(R.id.icomplain_name);
            investigation_card = itemView.findViewById(R.id.investigation_card);
        }
    }
}
