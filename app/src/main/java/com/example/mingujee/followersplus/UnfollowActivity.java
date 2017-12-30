package com.example.mingujee.followersplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.mingujee.followersplus.Model.FollowingUsers;
import com.example.mingujee.followersplus.Model.User;
import com.example.mingujee.followersplus.View.ConfigurationView;
import com.example.mingujee.followersplus.View.ResultView;
import com.example.mingujee.followersplus.View.ToolView;
import com.example.mingujee.followersplus.View.UnfollowView;

/**
 * Created by mingu.jee on 2016-08-21.
 */
public class UnfollowActivity extends Activity {
    User user;
    FollowingUsers followingUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfollow);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("CurrentUser");
        followingUsers = (FollowingUsers) intent.getSerializableExtra("FollowingUsers");

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        ToolView vTool = new ToolView(this, user, followingUsers, false);
        ViewGroup v0 = (ViewGroup) findViewById(R.id.ToolView);
        v0.addView(vTool);

        UnfollowView vUnfollow = new UnfollowView(this, user.getAccessToken());
        ViewGroup v1 = (ViewGroup) findViewById(R.id.UnfollowView);
        v1.addView(vUnfollow);
    }
}
