package com.yanis.front_end_mobile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    public TextView textView;
    public Activity context;
    public PreferenceUtils utils;
    public DatabaseReference firebaseDatabase;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        PreferenceUtils utils = new PreferenceUtils();
        context=getActivity();

        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("notification");

        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        RecyclerView recyclerView=v.findViewById(R.id.recycle_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        getAllNotification(recyclerView);
        return v;
    }




    private class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.card_view_notification,container,false));

            mCardView = itemView.findViewById(R.id.card_container_notifications);
            mTextView = itemView.findViewById(R.id.itemNotification);
        }

    }













    public class RecyclerViewAdapter extends RecyclerView.Adapter<NotificationFragment.RecyclerViewHolder>{

        private ArrayList<Notification> mlist;
        public RecyclerViewAdapter(ArrayList<Notification> list) {
            this.mlist=list;
        }

        @NonNull
        @Override
        public NotificationFragment.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(context);
            return new NotificationFragment.RecyclerViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationFragment.RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.mTextView.setText("You have been invited to " +mlist.get(i).getEvent_name());
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }















    private void getAllNotification(final RecyclerView recyclerView) {

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Notification> list=new ArrayList<>();
                for(DataSnapshot s :dataSnapshot.getChildren()){

                    Notification notification = s.getValue(Notification.class);

                    list.add(new Notification(notification.getUser_id(),notification.getEvent_name()));

                }
                recyclerView.setAdapter(new NotificationFragment.RecyclerViewAdapter(list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
