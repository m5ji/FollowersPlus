package com.example.mingujee.followersplus;

import android.os.Bundle;
import android.view.ViewGroup;

import com.example.mingujee.followersplus.Model.FollowingUsers;
import com.example.mingujee.followersplus.Model.User;
import com.example.mingujee.followersplus.View.ConfigurationView;
import com.example.mingujee.followersplus.View.ResultView;
import com.example.mingujee.followersplus.View.ToolView;

import android.view.*;
import android.app.*;
import android.content.*;

/**
 * Created by mingu.jee on 2016-08-06.
 */
public class ConfigActivity extends Activity{
    User user;
    FollowingUsers followingUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("CurrentUser");

        followingUsers = (FollowingUsers) intent.getSerializableExtra("FollowingUsers") ;
        if(followingUsers == null) {
            followingUsers = new FollowingUsers();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ToolView vTool = new ToolView(this, user, followingUsers ,true);
        ViewGroup v0 = (ViewGroup) findViewById(R.id.ToolView);
        v0.addView(vTool);

        ConfigurationView vConfig = new ConfigurationView(this, user.getAccessToken(), followingUsers);
        ViewGroup v1 = (ViewGroup) findViewById(R.id.ConfView);
        v1.addView(vConfig);

        ResultView vResult = new ResultView(this, followingUsers);
        ViewGroup v2 = (ViewGroup) findViewById(R.id.ResultView);
        v2.addView(vResult);

        followingUsers.addObserver(vResult);
    }
}
