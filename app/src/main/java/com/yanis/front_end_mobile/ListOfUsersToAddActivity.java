package com.yanis.front_end_mobile;

import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOfUsersToAddActivity extends AppCompatActivity {

    public TextView textView;
    public PreferenceUtils utils;
    public RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users_to_add);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_friends_to_add);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllUsers(recyclerView);
    }






    private class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_container_friend_to_add);
            mTextView = itemView.findViewById(R.id.itemNameFriendToAdd);
        }
    }



    public class RecyclerViewAdapter extends RecyclerView.Adapter<ListOfUsersToAddActivity.RecyclerViewHolder>{
        private Context mCtx;
        private List<String> mlist;
        public RecyclerViewAdapter(List<String> list,Context Ctx) {
            this.mlist=list;
            this.mCtx=Ctx;
        }

        @NonNull
        @Override
        public ListOfUsersToAddActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mCtx);
            View view=inflater.inflate(R.layout.card_view_friends,null);
            return new ListOfUsersToAddActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListOfUsersToAddActivity.RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.mTextView.setText(mlist.get(i));
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }


    private void getAllUsers(final RecyclerView recyclerView) {

        final String URL = "http://192.168.43.157:3000/users";
        final String Token = utils.getToken(this);


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<String> list=new ArrayList<>();
                            for (int i=0; i < response.length(); i++){
                                JSONObject jsonObject=response.getJSONObject(i);
                                list.add(jsonObject.getString("email"));
                            }
                            recyclerView.setAdapter(new ListOfUsersToAddActivity.RecyclerViewAdapter(list,ListOfUsersToAddActivity.this));
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
                        Log.e("info", "onResponse: KOOO ");
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
