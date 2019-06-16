package com.yanis.front_end_mobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public TextView textView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        PreferenceUtils utils = new PreferenceUtils();
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        textView= (TextView)v.findViewById(R.id.textViewHomeFragment);
        textView.setText(utils.getEmail(getActivity()));
        return v;
    }

}
