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
import com.ltrsoft.rajashtanuserapplication.classes.FirClass;
import com.ltrsoft.rajashtanuserapplication.fragments.Fir_History_Detail;

import java.util.ArrayList;

public class FirAdpter extends RecyclerView.Adapter<FirAdpter.ViewHolder> {
    public ArrayList<FirClass>list;

    private AdapterView.OnItemClickListener mListener;
    public FirAdpter(ArrayList<FirClass> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fircard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FirClass abc = list.get(position);
            holder.firno.setText(abc.getFir_id());
            holder.icrime_type.setText(abc.getComplaint_type_name());
            holder.icomplain_name.setText(abc.getComplaint_subject());
            holder.status.setText(abc.getStatus_name());
            holder.category.setText(abc.getComplaintORfir_name());
        holder.investigation_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                Fir_History_Detail fh=new Fir_History_Detail();
                Bundle bundle = new Bundle();
                bundle.putString("fired",abc.getFir_id());
                fh.setArguments(bundle);
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.containermain,fh).addToBackStack(null).commit();
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
            investigation_card = itemView.findViewById(R.id.fircard);
        }
    }
}
