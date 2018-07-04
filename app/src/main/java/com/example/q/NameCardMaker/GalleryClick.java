package com.example.q.NameCardMaker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


public class GalleryClick extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_image);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        String link = i.getExtras().getString("id");

        Log.d("position is here", link);
        Bitmap bm = BitmapFactory.decodeFile(link);

        ImageView imageView = (ImageView) findViewById(R.id.picture);
        imageView.setImageBitmap(bm);
    }
}
