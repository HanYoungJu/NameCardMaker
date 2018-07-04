package com.example.q.NameCardMaker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        final String name = i.getExtras().getString("name");
        final String number = i.getExtras().getString("number");
        Log.d("position is here", name);
        TextView name_view = (TextView) findViewById(R.id.name) ;
        name_view.setText(name);
        TextView number_view = (TextView) findViewById(R.id.number) ;
        number_view.setText(number);
        Button button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences pref = getSharedPreferences("Variable", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("name", name);
                editor.putString("number", number);
                editor.commit();
            }
        });
    }
}

