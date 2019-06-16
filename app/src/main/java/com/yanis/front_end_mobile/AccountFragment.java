package com.yanis.front_end_mobile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    Activity context;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        return inflater.inflate(R.layout.fragment_account, container, false);
    }


    public void onStart(){
        super.onStart();
        Button btAboutUs=(Button)context.findViewById(R.id.btnAboutUs);
        btAboutUs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //create an Intent object
                Intent intent=new Intent(context, AboutUs.class);
                //add data to the Intent object
                //start the second activity
                startActivity(intent);
            }

        });

        Button btPrivacy=(Button)context.findViewById(R.id.btnPrivacy);
        btPrivacy.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //create an Intent object
                Intent intent=new Intent(context, PrivacyActivity.class);
                //add data to the Intent object
                //start the second activity
                startActivity(intent);
            }

        });

        Button btLogOut=(Button)context.findViewById(R.id.btnLogOut);
        btLogOut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                PreferenceUtils.savePassword(null, context);
                PreferenceUtils.saveEmail(null, context);
                PreferenceUtils.saveToken(null, context);
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }

        });
    }

}
