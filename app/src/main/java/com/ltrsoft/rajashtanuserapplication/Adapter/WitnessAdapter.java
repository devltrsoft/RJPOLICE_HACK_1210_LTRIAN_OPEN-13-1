package com.ltrsoft.rajashtanuserapplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.WitnessClass;
import com.ltrsoft.rajashtanuserapplication.fragments.WitnessDetailFragment;

import java.util.List;

public class WitnessAdapter extends RecyclerView.Adapter<WitnessAdapter.WitnessViewHolder> {
    private Context context;
    private List<WitnessClass> witnessList; // Change the type to WitnessClass

    public WitnessAdapter(List<WitnessClass> witnessList) {
        this.witnessList = witnessList;
    }

    @NonNull
    @Override
    public WitnessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.witnesscard, parent, false);
        return new WitnessViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WitnessViewHolder holder, int position) {
        WitnessClass model = witnessList.get(position);
        holder.name.setText(model.getComplaint_witness_fname()+model.getComplaint_witness_mname()+model.getComplaint_witness_lname());
        holder.txt4.setText(model.getComplaint_witness_gender());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked on item", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle args = new Bundle();

                WitnessDetailFragment fragment = new WitnessDetailFragment();

                args.putString("witness_id", model.getComplaint_witness_id());
//                args.putString("witnessmame", model.getComplaint_witness_mname());
//                args.putString("witnesslame", model.getComplaint_witness_lname());
//                args.putString("complaint_witness_gender", model.getComplaint_witness_gender());
//                args.putString("complaint_witness_mobile", model.getComplaint_witness_mobile());
//                args.putString("complaint_witness_email", model.getComplaint_witness_email());
//                args.putString("complaint_witness_adhar", model.getComplaint_witness_adhar());
//                args.putString("complaint_witness_address", model.getComplaint_witness_address());
//                args.putString("city_name", model.getCity_name());
//                args.putString("country_name", model.getCountry_name());
//                args.putString("state_name", model.getState_name());
//                args.putString("district_name", model.getDistrict_name());

                fragment.setArguments(args);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containermain, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return witnessList.size();
    }

    public static class WitnessViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, location,txt4; // Change the TextView names

        CardView cardView;

        public WitnessViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.AI);
            name = itemView.findViewById(R.id.txt2);
            location = itemView.findViewById(R.id.txt5);
            txt4 = itemView.findViewById(R.id.txt4);
            cardView = itemView.findViewById(R.id.c);
        }
    }
}
