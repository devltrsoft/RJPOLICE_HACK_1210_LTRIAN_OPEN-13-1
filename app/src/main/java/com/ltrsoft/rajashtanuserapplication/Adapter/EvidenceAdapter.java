package com.ltrsoft.rajashtanuserapplication.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.Complaint_Photo;
import java.util.List;

public class EvidenceAdapter extends RecyclerView.Adapter<EvidenceAdapter.ViewHolder> {
    private List<Complaint_Photo> items;

    public EvidenceAdapter(List<Complaint_Photo> items) {
        this.items = items;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evidencecard, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint_Photo item = items.get(position);
        int i=position;
       holder.textView.setText(item.getComplaint_photo_id());
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
