package com.yanis.front_end_mobile;


import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
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
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public TextView textView;
    public Activity context;
    public PreferenceUtils utils;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        PreferenceUtils utils = new PreferenceUtils();
        context=getActivity();
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView=v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getAllEvent(recyclerView);
        return v;
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.card_view,container,false));

            mCardView = itemView.findViewById(R.id.card_container);
            mTextView = itemView.findViewById(R.id.itemNameEvent);


        }
    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

        private List<String> mlist;
        public RecyclerViewAdapter(List<String> list) {
            this.mlist=list;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(context);
            return new RecyclerViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            recyclerViewHolder.mTextView.setText(mlist.get(i));

        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }


    private void getAllEvent(final RecyclerView recyclerView) {

        final String URL = "http://192.168.1.12:3000/events";
        final String Token = utils.getToken(context);

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
                                list.add(jsonObject.getString("name"));
                            }
                            recyclerView.setAdapter(new RecyclerViewAdapter(list));
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}
