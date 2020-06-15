package com.example.familymap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import frags.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment loginFrag = fm.findFragmentById(R.id.fragFrameLayout);
        if(loginFrag == null){
            loginFrag = LoginFragment.newInstance(this);
            fm.beginTransaction()
                    .add(R.id.fragFrameLayout, loginFrag)
                    .commit();
        }
    }
}