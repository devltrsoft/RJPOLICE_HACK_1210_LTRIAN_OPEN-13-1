package com.ltrsoft.rajashtanuserapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.EvidenceClass;

import java.util.List;

public class EvidenceAdapter extends RecyclerView.Adapter<EvidenceAdapter.ViewHolder> {
    private Context context;
    private List<EvidenceClass> items;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public EvidenceAdapter(Context context, List<EvidenceClass> items, RequestQueue requestQueue) {
        this.context = context;
        this.items = items;
        this.requestQueue = this.requestQueue;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.evidencecard, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EvidenceClass item = items.get(position);
        int i=position;

       holder.textView.setText(item.getId());
       holder.imageView.setImageResource(R.drawable.evidenc3);
//        String imageUrl = item.getImagerl();
//        Picasso.get().load(imageUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.idd);
            imageView=itemView.findViewById(R.id.img);
        }
    }
}
