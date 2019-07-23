package com.yanis.front_end_mobile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class RefundFragment extends Fragment {
    public TextView textView;
    public Activity context;
    public PreferenceUtils utils;

    View view;
    public HashMap<String,String> hashMap;


    public RefundFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        PreferenceUtils utils = new PreferenceUtils();
        context=getActivity();


        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(context);
        String str= m.getString("Response", "");
        hashMap= new HashMap<>();
        JSONObject jsonObject ;


        try {
            jsonObject = new JSONObject(str);
            JSONArray friendResponse = (JSONArray)jsonObject.get("members");
            for (int i=0; i < friendResponse.length(); i++){
                JSONObject friendObject=friendResponse.getJSONObject(i);
                hashMap.put(friendObject.getString("user_id"), friendObject.getString("last_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        View v = inflater.inflate(R.layout.refund_fragment, container, false);
        RecyclerView recyclerView=v.findViewById(R.id.recycle_view_expense);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        getAllRefund(recyclerView);
        return v;

    }









    private class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mTextViewPerson1;
        private TextView mTextViewPerson2;
        private TextView mTextViewPrice;

        ItemEventClickListener itemEventClickListener;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

        RecyclerViewHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.card_view_refund_expense,container,false));

            mCardView = itemView.findViewById(R.id.card_container);
            mTextViewPerson1 = itemView.findViewById(R.id.textViewNamePerson1);
            mTextViewPerson2 = itemView.findViewById(R.id.textViewNamePerson2);
            mTextViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }













    public class RecyclerViewAdapter extends RecyclerView.Adapter<RefundFragment.RecyclerViewHolder>{

        private List<ExpenseRefund> mlist;
        public RecyclerViewAdapter(List<ExpenseRefund> list) {
            this.mlist=list;
        }

        @NonNull
        @Override
        public RefundFragment.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater=LayoutInflater.from(context);
            return new RefundFragment.RecyclerViewHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull RefundFragment.RecyclerViewHolder recyclerViewHolder, int i) {

            recyclerViewHolder.mTextViewPerson1.setText(mlist.get(i).getNamePerson1());
            recyclerViewHolder.mTextViewPerson2.setText(mlist.get(i).getNamePerson2());
            recyclerViewHolder.mTextViewPrice.setText(mlist.get(i).getPrice()+"€");
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }















    private void getAllRefund(final RecyclerView recyclerView) {

        SharedPreferences m = PreferenceManager.getDefaultSharedPreferences(context);
        String event_id= m.getString("event_id", "");
        Log.e("event", "getAllFriends: "+event_id );

        final String URL = "http://192.168.43.157:3000/events/"+event_id+"/transactions";
        final String Token = utils.getToken(context);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<ExpenseRefund> list=new ArrayList<>();
                            for (int i=0; i < response.length(); i++){
                                JSONObject jsonObject=response.getJSONObject(i);
                                list.add(new ExpenseRefund(jsonObject.getString("id"),hashMap.get(jsonObject.getString("from_id")),hashMap.get(jsonObject.getString("to_id")),jsonObject.getString("amount")));
                            }
                            recyclerView.setAdapter(new RefundFragment.RecyclerViewAdapter(list));
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
