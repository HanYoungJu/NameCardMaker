package com.example.q.NameCardMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class ContactClick extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_contact);
    }
    @Override
    public void onResume(){
        super.onResume();
        Intent i = getIntent();
        // Selected image id
        final String photo = i.getExtras().getString("photo");
        final String name = i.getExtras().getString("name");
        final String mobile_num = i.getExtras().getString("mobile_num");
        final String home_num = i.getExtras().getString("home_num");
        final String email = i.getExtras().getString("email");
        Log.d("position is here", name);

        ImageView id_view = (ImageView) findViewById(R.id.big_contact_image) ;
        InputStream photo_is = null;
        if(Build.VERSION.SDK_INT >= 19) {
            photo_is = new ByteArrayInputStream(photo.getBytes(StandardCharsets.UTF_8));
        }
        Bitmap contactPhoto = BitmapFactory.decodeStream(photo_is);
        id_view.setImageBitmap(contactPhoto);

        if(Build.VERSION.SDK_INT >= 21) {
        id_view.setBackground(new ShapeDrawable(new OvalShape()));
        id_view.setClipToOutline(true);}

        TextView name_view = (TextView) findViewById(R.id.big_contact_name) ;
        name_view.setText(name);

        TextView mobile_num_view = (TextView) findViewById(R.id.big_contact_mobile_num);
        mobile_num_view.setText(mobile_num);

        TextView home_num_view = (TextView) findViewById(R.id.big_contact_home_num);
        home_num_view.setText(home_num);

        TextView email_view = (TextView) findViewById(R.id.big_contact_email);
        email_view.setText(email);

        Button button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences pref = getSharedPreferences("Variable", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("name", name);
                editor.putString("mobile_num", mobile_num);
                editor.putString("home_num", home_num);
                editor.putString("email", email);
                editor.commit();
            }
        });
    }
}

