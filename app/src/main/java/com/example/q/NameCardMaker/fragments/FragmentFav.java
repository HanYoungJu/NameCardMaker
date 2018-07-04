package com.example.q.NameCardMaker.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.NameCardMaker.R;


public class FragmentFav extends Fragment {
    private static final String name_key = "name";
    private static final String number_key = "number";
    private static final String link_key = "link";

    private View v;
    private String name;
    private String number;
    private String link;

    public FragmentFav() {

    }
    public static Fragment newInstance(String link_parameter,String name_parameter,String number_parameter) {
        FragmentFav fragment = new FragmentFav();
        Bundle args = new Bundle();
        args.putString(link_key, link_parameter);
        args.putString(name_key, name_parameter);
        args.putString(number_key,number_parameter);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            link = getArguments().getString(link_key);
            name = getArguments().getString(name_key);
            number = getArguments().getString(number_key);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_fav, container, false);
        TextView view_name =(TextView) v.findViewById(R.id.name);
        TextView view_number =(TextView) v.findViewById(R.id.number);
        Bitmap bm = BitmapFactory.decodeFile(link);
        ImageView imageView = (ImageView) v.findViewById(R.id.picture);
        imageView.setImageBitmap(bm);
        view_name.setText(name);
        view_number.setText(number);
        return v;
    }

}
