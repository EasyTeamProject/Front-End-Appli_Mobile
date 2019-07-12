package com.yanis.front_end_mobile;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureEventActivity extends AppCompatActivity {

    PreferenceUtils utils;
    public RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_event);
        PreferenceUtils utils = new PreferenceUtils();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_picture);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        getAllPictures(recyclerView);

    }










    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private CardView mCardView;


        public RecyclerViewHolder(View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.card_container);
            imageView = itemView.findViewById(R.id.my_image);
        }
    }













    public class RecyclerViewAdapter extends RecyclerView.Adapter<PictureEventActivity.RecyclerViewHolder>{
        private Context mCtx;
        private List<String> list;

        public RecyclerViewAdapter(List<String> list,Context Ctx) {
            this.list=list;
            this.mCtx=Ctx;
        }

        @NonNull
        @Override
        public PictureEventActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(mCtx);
            View view=inflater.inflate(R.layout.row_layout,null);
            return new PictureEventActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, final int i) {
            Picasso.get().load(list.get(i)).resize(500, 500).centerCrop().into(recyclerViewHolder.imageView);


            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String image= list.get(i);

                    Intent intent=new Intent(PictureEventActivity.this,FullImageActivity.class);
                    intent.putExtra("image",image);

                    PictureEventActivity.this.startActivity(intent);
                }

            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }














    public void getAllPictures(final RecyclerView recyclerView) {

        final String URL = "http://192.168.43.157:3000/events/5/medias";
        final String Token = utils.getToken(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<String> list=new ArrayList<>();
                            for (int i=0; i < response.length(); i++){
                                JSONObject jsonObject=response.getJSONObject(i);
                                list.add("http://192.168.43.157:3000/"+jsonObject.getString("storage"));

                            }
                            recyclerView.setAdapter(new RecyclerViewAdapter(list,PictureEventActivity.this));

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
