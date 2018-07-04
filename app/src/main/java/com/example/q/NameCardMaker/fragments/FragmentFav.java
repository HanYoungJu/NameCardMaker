package com.example.q.NameCardMaker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.NameCardMaker.R;


public class FragmentFav extends Fragment {
    private static final String name_key = "name";
    private static final String number_key = "number";

    private View v;
    private String name;
    private String number;

    public FragmentFav() {

    }
    public static Fragment newInstance(String name_parameter, String number_parameter) {
        FragmentFav fragment = new FragmentFav();
        Bundle args = new Bundle();
        args.putString(name_key, name_parameter);
        args.putString(number_key, number_parameter);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(name_key);
            number = getArguments().getString(number_key);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_fav, container, false);
        TextView view_name =(TextView) v.findViewById(R.id.name_fav);
        TextView view_number =(TextView) v.findViewById(R.id.number_fav);
        view_name.setText(name);
        view_number.setText(number);
        return v;
    }

}
