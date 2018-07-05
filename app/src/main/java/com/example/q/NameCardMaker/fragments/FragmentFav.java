package com.example.q.NameCardMaker.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.NameCardMaker.GalleryClick;
import com.example.q.NameCardMaker.MainActivity;
import com.example.q.NameCardMaker.R;
import com.example.q.NameCardMaker.models.CaptureUtil;
import com.example.q.NameCardMaker.models.ExifUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class FragmentFav extends Fragment {
    private View v;
    private View mv;
    private String link;
    private String name;
    private String mobile_num;
    private String home_num;
    private String email;

    public FragmentFav() {

    }
    private void SaveBitmapToFileCache(Bitmap bitmap, String strFilePath, String strFileName) {
        File path = new File(strFilePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        File fileCacheItem = new File(strFilePath+strFileName);
        Log.i("Tag", strFilePath+strFileName);
        OutputStream out = null;
        try
        {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }




    public File ScreenShot(View view){
        view.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용하게 한다

        Bitmap screenBitmap = view.getDrawingCache();   //캐시를 비트맵으로 변환

        String filename = "screenshot.png";
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);  //Pictures폴더 screenshot.png 파일
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //비트맵을 PNG파일로 변환
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        view.setDrawingCacheEnabled(false);
        return file;
    }


    public static Fragment newInstance(String link,String name,String mobile_num, String home_num, String email) {
        FragmentFav fragment = new FragmentFav();
        Bundle args = new Bundle();
        args.putString("link", link);
        args.putString("name", name);
        args.putString("mobile_num", mobile_num);
        args.putString("home_num",home_num);
        args.putString("email",email);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            link = getArguments().getString("link");
            name = getArguments().getString("name");
            mobile_num = getArguments().getString("mobile_num");
            home_num = getArguments().getString("home_num");
            email = getArguments().getString("email");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_fav, container, false);
        v.setDrawingCacheEnabled(true);
        v.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);




        TextView view_name =(TextView) v.findViewById(R.id.name);
        view_name.setText(name);

        TextView view_number =(TextView) v.findViewById(R.id.number);
        view_number.setText(mobile_num);

        Bitmap bm = BitmapFactory.decodeFile(link);
        bm = ExifUtils.rotateBitmap(link,bm);

        Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bm,500,500);
        ImageView imageView = (ImageView) v.findViewById(R.id.picture);
        if(Build.VERSION.SDK_INT >= 21) {
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            imageView.setClipToOutline(true);
        }
        imageView.setImageBitmap(thumbnail);
        ImageView setIcon = (ImageView) v.findViewById(R.id.icon);
        setIcon.setImageResource(R.drawable.android3);

        Button button = (Button)v.findViewById(R.id.button3);
        Button capture = (Button)v.findViewById(R.id.capture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity)getActivity();


                activity.refresh();

            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View capture = v.findViewById(R.id.frag_fav);
                File screenShot = ScreenShot(capture);

                if(screenShot!=null){
                    //갤러리에 추가
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "사진이 저장되었습니다",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return v;
    }

}
