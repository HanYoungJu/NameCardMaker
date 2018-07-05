package com.example.q.NameCardMaker;

import android.Manifest;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.q.NameCardMaker.adapters.ViewPagerAdapter;
import com.example.q.NameCardMaker.fragments.FragmentContacts;
import com.example.q.NameCardMaker.fragments.FragmentFav;
import com.example.q.NameCardMaker.fragments.FragmentGallery;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_CALL_LOG};
    private static int REQUEST_ALL = 3;
    private final int[] ICONS = {R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground};

    public void refresh(){
        SharedPreferences pref = getSharedPreferences("Variable", 0);
        String link = pref.getString("link", null);
        String name = pref.getString("name", null);
        String mobile_num = pref.getString("mobile_num", null);
        String home_num = pref.getString("home_num", null);
        String email = pref.getString("email", null);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_fav, FragmentFav.newInstance(link, name, mobile_num, home_num, email));
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_ALL);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for( String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                tabLayout = findViewById(R.id.tablayout);
                viewPager = findViewById(R.id.viewpager);

                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                adapter.addFragment(new FragmentContacts(), "연락처");
                adapter.addFragment(new FragmentGallery(), "사진");
                adapter.addFragment(new FragmentFav(), "명함");

                viewPager.setAdapter(adapter);

                tabLayout.setupWithViewPager(viewPager);

                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    tabLayout.getTabAt(i).setIcon(ICONS[i]);
                }
            }
        }
    }
}
