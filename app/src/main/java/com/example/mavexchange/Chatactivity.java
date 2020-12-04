package com.example.mavexchange;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mavexchange.Fragments.UsersFragment;
import com.example.mavexchange.Fragments.chatsFragment;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatactivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView full_name;

    FirebaseUser fAuth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatactivity);

        profile_image = findViewById(R.id.profile_image);
        full_name = findViewById(R.id.full_name);
        fAuth = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Model.User user = dataSnapshot.getValue(Model.User.class);

                full_name.setText(user.getFullname());
                profile_image.setImageResource(R.mipmap.ic_launcher);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });



        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager= findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new chatsFragment(), "Chats");
        viewPagerAdapter.addFragment(new UsersFragment(), "Users");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment>fragments;
        private final ArrayList<String>titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }

}

