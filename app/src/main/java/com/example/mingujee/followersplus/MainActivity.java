package com.example.mingujee.followersplus;

import android.os.Bundle;
import com.example.mingujee.followersplus.Model.User;
import com.example.mingujee.followersplus.View.*;

import android.view.*;
import android.app.*;

public class MainActivity extends Activity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        LoginView vLogin = new LoginView(this, user);
        ViewGroup v1 = (ViewGroup) findViewById(R.id.LoginView);
        v1.addView(vLogin);
    }
}
