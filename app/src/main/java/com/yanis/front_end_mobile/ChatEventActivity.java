package com.yanis.front_end_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatEventActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    RelativeLayout chat_activity;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseReference firebaseDatabase;
    ArrayList<ChatMessage> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_event);

        list=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("chat");

        recyclerView = (RecyclerView) findViewById(R.id.list_of_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayChatMessage(recyclerView);

        Intent intent=getIntent();
        final String event_id = intent.getStringExtra("event_id");


        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input= (EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().child("chat").push().setValue(new ChatMessage(input.getText().toString(), "Yanis",event_id));
                input.setText("");
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        displayChatMessage(recyclerView);
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView message_text;
        private TextView message_user;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_container_message);
            message_text = itemView.findViewById(R.id.message_text);
            message_user = itemView.findViewById(R.id.message_user);
        }
    }






    public class RecyclerViewAdapter extends RecyclerView.Adapter<ChatEventActivity.RecyclerViewHolder>{
        private Context mCtx;
        private ArrayList<ChatMessage> mlist;
        public RecyclerViewAdapter(ArrayList<ChatMessage> list,Context Ctx) {
            this.mlist=list;
            this.mCtx=Ctx;
        }


        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mCtx);
            View view=inflater.inflate(R.layout.card_view_chat_message,null);
            return new RecyclerViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.message_user.setText(mlist.get(i).getMessageUser());
            recyclerViewHolder.message_text.setText(mlist.get(i).getMessageText());
        }


        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }








    public void displayChatMessage(final RecyclerView recyclerView){
        Intent intent=getIntent();
        final String event_id = intent.getStringExtra("event_id");

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ChatMessage> list=new ArrayList<>();
                for(DataSnapshot chatMessage :dataSnapshot.getChildren()){
                    ChatMessage chatMsg = chatMessage.getValue(ChatMessage.class);
                    System.out.println("event number"+ event_id);
                    if(chatMsg.getEvent_id().equals(event_id)) {
                        list.add(chatMsg);
                    }

                }
                recyclerView.setAdapter(new RecyclerViewAdapter(list,ChatEventActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
