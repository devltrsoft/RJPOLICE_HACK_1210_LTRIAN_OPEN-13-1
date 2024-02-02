package com.ltrsoft.rajashtanuserapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ltrsoft.rajashtanuserapplication.R;
import com.ltrsoft.rajashtanuserapplication.classes.MessageModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatBot extends Fragment {
    public ChatBot() {      }
    // widgets in xml file.
    private RecyclerView chatsRV;
    private ImageButton sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;

    // creating a variable for array list and adapter class.
    private ArrayList<MessageModal> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.chat_bot_page, container, false);

        chatsRV = view.findViewById(R.id.idRVChats);
        sendMsgIB = view.findViewById(R.id.send);
        userMsgEdt = view.findViewById(R.id.idedMsg);

        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModalArrayList = new ArrayList<>();
//        messageRVAdapter = new MessageRVAdapter(messageModalArrayList,getContext());
// on below line we are initializing our adapter class and passing our array list to it.
        messageRVAdapter = new MessageRVAdapter(messageModalArrayList, getContext());

        // below line we are creating a variable for our linear layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(messageRVAdapter);
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userMsgEdt.getText().toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(getContext(), "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(userMsgEdt.getText().toString());
                userMsgEdt.setText("");
            }
        });

        return view;
    }

    private void sendMessage(String userMsg) {

        Toast.makeText(getContext(), "in send message", Toast.LENGTH_SHORT).show();

        messageModalArrayList.add(new MessageModal(userMsg, USER_KEY));
        chatsRV.setAdapter(messageRVAdapter);
        messageRVAdapter.notifyDataSetChanged();


        String url = "https://rj.ltr-soft.com/public/police_api/message_chatbot/read_message_chatbot.php";

        //https://rj.ltr-soft.com/public/police_api/message_chatbot/read_message_chatbot.php
        // creating a variable for our request queue.

        //  Toast.makeText(MainActivity.this, "Message="+userMsg, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("resposne "+response.toString());
                Toast.makeText(getContext(), "."+response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    if (!TextUtils.isEmpty(response)) {
                        JSONArray json = new JSONArray(response);
                        if (json.length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject obj = json.getJSONObject(i);
                                String answer = obj.getString("answer");
                                Toast.makeText(getContext(), "ANSWER=" + answer, Toast.LENGTH_SHORT).show();

                                messageModalArrayList.add(new MessageModal(answer, BOT_KEY));
                                messageRVAdapter.notifyDataSetChanged();
                            }
                        } else {
                            messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                            messageRVAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Handle empty response
                        messageModalArrayList.add(new MessageModal("Sorry no response found!", BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                    }
                }  catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error "+error.toString());
                        messageModalArrayList.add(new MessageModal("Sorry no response found", BOT_KEY));
                        messageRVAdapter.notifyDataSetChanged();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("question",userMsg);
                return map;
            }
        };
        queue.add(stringRequest);

    }





    //classes
    public static class MessageRVAdapter extends RecyclerView.Adapter {

        // variable for our array list and context.
        private ArrayList<MessageModal> messageModalArrayList;
        private Context context;

        // constructor class.
        public MessageRVAdapter(ArrayList<MessageModal> messageModalArrayList, Context context) {
            this.messageModalArrayList = messageModalArrayList;
            this.context = context;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            // below code is to switch our
            // layout type along with view holder.
            switch (viewType) {
                case 0:
                    // below line we are inflating user message layout.
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item, parent, false);
                    return new UserViewHolder(view);
                case 1:
                    // below line we are inflating bot message layout.
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item, parent, false);
                    return new BotViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            // this method is use to set data to our layout file.
            MessageModal modal = messageModalArrayList.get(position);
            switch (modal.getSender()) {
                case "user":
                    // below line is to set the text to our text view of user layout
                    ((UserViewHolder) holder).userTV.setText(modal.getMessage());
                    break;
                case "bot":
                    // below line is to set the text to our text view of bot layout
                    ((BotViewHolder) holder).botTV.setText(modal.getMessage());
                    break;
            }
        }

        @Override
        public int getItemCount() {
            // return the size of array list
            return messageModalArrayList.size();
        }

        @Override
        public int getItemViewType(int position) {
            // below line of code is to set position.
            switch (messageModalArrayList.get(position).getSender()) {
                case "user":
                    return 0;
                case "bot":
                    return 1;
                default:
                    return -1;
            }
        }

        public static class UserViewHolder extends RecyclerView.ViewHolder {

            // creating a variable
            // for our text view.
            TextView userTV;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing with id.
                userTV = itemView.findViewById(R.id.idTvuser);
            }
        }

        public static class BotViewHolder extends RecyclerView.ViewHolder {

            // creating a variable
            // for our text view.
            TextView botTV;

            public BotViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing with id.
                botTV = itemView.findViewById(R.id.idTVbot);
            }
        }
    }

}