package com.example.mavexchange;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
 //   Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),loginActivity.class));
                finish();
            }
        });

         */
        defaultFragment (new HomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.NavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item){
        Fragment frag = null;

        switch ( (item.getItemId())){
            case R.id.home:
                frag = new HomeFragment();
                Toast toast1 = Toast.makeText(getApplicationContext(), "Welcome to Home page", Toast.LENGTH_SHORT);
                toast1.show();
                break;

            case R.id.message:
                frag = new MessageFragment();
                Toast toast2 = Toast.makeText(getApplicationContext(), "Welcome to Message page", Toast.LENGTH_SHORT);
                toast2.show();
                break;

            case R.id.add:
                frag = new addFragment();
                Toast toast3 = Toast.makeText(getApplicationContext(), "Welcome to add post page", Toast.LENGTH_SHORT);
                toast3.show();
                break;

            case R.id.favorite:
                frag = new FavoriteFragment();
                Toast toast4 = Toast.makeText(getApplicationContext(), "Welcome to Favorite page", Toast.LENGTH_SHORT);
                toast4.show();
                break;

            case R.id.profile:
                frag = new ProfileFragment();
                Toast toast5 = Toast.makeText(getApplicationContext(), "Welcome to Profile page", Toast.LENGTH_SHORT);
                toast5.show();
                break;

        }
        return defaultFragment(frag);
    }
    private boolean defaultFragment(Fragment fragment){
        if (fragment !=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.HomeActivity, fragment)
                    .commit();
            return true;
        }
        return false;

    }
}

/* <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".HomeActivity">


    <ImageView
        android:id="@+id/imageView"
        android:layout_width="91dp"
        android:layout_height="71dp"
        android:layout_weight="1"
        app:srcCompat="@drawable/exchange" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="386dp"
        android:layout_height="596dp"
        android:layout_weight="1"
        android:text="Home Page"
        android:textColor="#000"
        android:textSize="40sp" />

    <Button
        android:id="@+id/logout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Logout" />

    <TextView
        android:id="@+id/search"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:textSize="10dp"
        android:text="Search" />
</LinearLayout> */