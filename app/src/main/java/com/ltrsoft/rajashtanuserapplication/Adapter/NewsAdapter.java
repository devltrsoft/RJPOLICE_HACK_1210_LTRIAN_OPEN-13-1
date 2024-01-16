package com.ltrsoft.rajashtanuserapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.News;
import com.ltrsoft.rajashtanuserapplication.fragments.NewsViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    public ArrayList <News>list;
    boolean b=false;
    int likeCount=0;
    public NewsAdapter(ArrayList<News> list) {
        this.list = list;
    }
    Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycle_layout,parent,false);
        context=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = list.get(position);
       // String imgurl ="https://institute.ltr-soft.com/company_details/"+news.getNews_photo_path();
        //Picasso.get().load(imgurl).into(holder.newsImg);
        holder.newsImg.setImageResource(R.drawable.news1);
        holder.title.setText(news.getNews_description());
        holder.newsDate.setText(news.getNews_date());

        holder.newsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                 NewsViewFragment n = new NewsViewFragment();
                Bundle b = new Bundle();


                b.putString("news_img", news.getNews_photo_path());

                b.putString("news_description", news.getNews_category_name());
                b.putString("news_title", news.getNews_title());

                n.setArguments(b);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containermain,n)
                        .addToBackStack(null)
                        .commit();
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newsId = list.get(position).getNew_Id();
                if (b) {
                    likeCount++;
                } else {
                    likeCount--;
                }
                Toast.makeText(context, ""+likeCount, Toast.LENGTH_SHORT).show();
                sendLikeCountToServer(Integer.parseInt(newsId), likeCount);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri imageUri = (Uri) view.getTag();
                Intent intent=new Intent();
                intent.setAction(intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"Hello");
                intent.setType("text/plain");
                if (intent.resolveActivity(context.getPackageManager())!=null){
                }
                context.startActivity(intent);
            }
        });
    }



    private void sendLikeCountToServer(int newsId, int likeCount) {


        String url = "https://your_server/insert_like_count.php";

        // Create a request
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Handle the response from the server if needed
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error if the request fails
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Parameters to send to the server
                Map<String, String> params = new HashMap<>();
                params.put("news_id", String.valueOf(newsId));
                params.put("like_count", String.valueOf(likeCount));
                return params;
            }
        };

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(request);
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView newsImg,comment,like,share;
        public TextView title,newsDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImg = itemView.findViewById(R.id.newsimg);
            comment = itemView.findViewById(R.id.comment);
            like = itemView.findViewById(R.id.like);
            share = itemView.findViewById(R.id.share);
            title = itemView.findViewById(R.id.title);
            newsDate = itemView.findViewById(R.id.news_date);

        }
    }
}
