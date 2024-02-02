package com.ltrsoft.rajashtanuserapplication.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.SuspectClass;
import com.ltrsoft.rajashtanuserapplication.fragments.Suspect_History_Detail;

import java.util.ArrayList;

public class SuspectAdapter extends RecyclerView.Adapter<SuspectAdapter.ViewHolder> {
    public ArrayList<SuspectClass> list;

    private AdapterView.OnItemClickListener mListener;

    public SuspectAdapter(ArrayList<SuspectClass> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suspectcard, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = 0;
        SuspectClass suspectClass = list.get(position);
        holder.name.setText(suspectClass.getFname()+suspectClass.getMname()+suspectClass.getLname());
        holder.date.setText(suspectClass.getDob());
        System.out.println("id = "+suspectClass.getCaseid());
        holder.id.setText("1");

        holder.suspectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                Suspect_History_Detail s=new Suspect_History_Detail();
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                bundle.putInt("cid", suspectClass.getSuspectid());
//                bundle.putString("name", suspectClass.getFname());
//                bundle.putString("gender",suspectClass.getGender());
//              bundle.putString("mobile",suspectClass.getMobile());
//                bundle.putString("email",suspectClass.getEmail());
//                bundle.putString("adhar",suspectClass.getAdharno());
//                bundle.putString("cname",suspectClass.getCountyname());
//                bundle.putString("dname",suspectClass.getDistname());
//                bundle.putString("sname",suspectClass.getStname());
                s.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.containermain, s
                ).addToBackStack(null).commit();
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
