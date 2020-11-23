package com.example.mavexchange;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private Button logout;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), loginActivity.class));
                finish();
            }


        });
        return v;
    }

    private void finish() {
    return;
    }
}

