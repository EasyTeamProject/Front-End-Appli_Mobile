package com.yanis.front_end_mobile;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
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
        public Button button;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_container_friend_to_add);
            mTextView = itemView.findViewById(R.id.itemNameFriendToAdd);
            button = itemView.findViewById(R.id.buttonAddFriend);

        }
    }



    public class RecyclerViewAdapter extends RecyclerView.Adapter<ListOfUsersToAddActivity.RecyclerViewHolder>{
        private Context mCtx;
        private List<User> mlist;
        public RecyclerViewAdapter(List<User> list,Context Ctx) {
            this.mlist=list;
            this.mCtx=Ctx;
        }

        @NonNull
        @Override
        public ListOfUsersToAddActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mCtx);
            View view=inflater.inflate(R.layout.card_view_friends_to_add,null);
            return new ListOfUsersToAddActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListOfUsersToAddActivity.RecyclerViewHolder recyclerViewHolder, final int i) {
            recyclerViewHolder.mTextView.setText(mlist.get(i).getName());
            recyclerViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inviteUser(mlist.get(i).getId());
                }
            });
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
                            List<User> list=new ArrayList<>();
                            for (int i=0; i < response.length(); i++){
                                JSONObject jsonObject=response.getJSONObject(i);
                                list.add(new User(Integer.parseInt(jsonObject.getString("id")),jsonObject.getString("name")));
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








    public void inviteUser(Integer id_user) {

        final String URL = "http://192.168.43.157:3000/friends?friend_id="+id_user;
        final String Token = utils.getToken(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Toast.makeText(ListOfUsersToAddActivity.this, "User added successfuly", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(ListOfUsersToAddActivity.this,FriendsActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListOfUsersToAddActivity.this, "User not added successfuly", Toast.LENGTH_SHORT).show();
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
