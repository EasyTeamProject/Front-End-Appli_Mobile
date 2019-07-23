package com.yanis.front_end_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendEventActivity extends AppCompatActivity {

    public TextView textView;
    public PreferenceUtils utils;
    public RecyclerView recyclerView;
    public long idUserAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_event);
        PreferenceUtils utils = new PreferenceUtils();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_event_friends);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllFriends(recyclerView);
    }






    public void onAddFriendsPressed(View view){
        if(PreferenceUtils.getId(this)==idUserAdmin) {
            Intent intent = new Intent(this, FriendsToAddToEventActivity.class);
            Intent i = getIntent();
            String event_name = i.getStringExtra("event_name");
            intent.putExtra("event_name", event_name);
            startActivity(intent);
        }else {
            Toast.makeText(FriendEventActivity.this, "You are not admin!!! you can't invite your friends... please contact the admin", Toast.LENGTH_SHORT).show();

        }
    }








    private class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_container_friend);
            mTextView = itemView.findViewById(R.id.itemNameFriend);
        }
    }



    public class RecyclerViewAdapter extends RecyclerView.Adapter<FriendEventActivity.RecyclerViewHolder>{
        private Context mCtx;
        private List<Friend> mlist;
        public RecyclerViewAdapter(List<Friend> list,Context Ctx) {
            this.mlist=list;
            this.mCtx=Ctx;
            idUserAdmin =Long.parseLong(list.get(0).getIdUserAdmin());
        }

        @NonNull
        @Override
        public FriendEventActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mCtx);
            View view=inflater.inflate(R.layout.card_view_friends,null);
            return new FriendEventActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FriendEventActivity.RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.mTextView.setText(mlist.get(i).getFamilyNameFriend()+mlist.get(i).getNameFriend());
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }





    private void getAllFriends(final RecyclerView recyclerView) {
        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(this);
        String event_id= m.getString("event_id", "");

        final String URL = "http://192.168.43.157:3000/events/"+event_id;
        final String Token = utils.getToken(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<Friend> list=new ArrayList<>();
                            JSONArray friendResponse = (JSONArray)response.get("members");
                            for (int i=0; i < friendResponse.length(); i++){
                                JSONObject jsonObject=friendResponse.getJSONObject(i);
                                list.add(new Friend(jsonObject.getString("first_name"), jsonObject.getString("last_name"),jsonObject.getString("user_id")));
                            }
                            recyclerView.setAdapter(new FriendEventActivity.RecyclerViewAdapter(list,FriendEventActivity.this));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("info", "onResponse: OOOK dans l'exception");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i("info", "onResponse: KOOO ");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer");
                headers.put("Content-Type", "application/json");
                headers.put("JWT",Token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
