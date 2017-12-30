package com.example.mingujee.followersplus.View;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;

import com.example.mingujee.followersplus.ConfigActivity;
import com.example.mingujee.followersplus.Model.FollowingUsers;
import com.example.mingujee.followersplus.UnfollowActivity;
import com.example.mingujee.followersplus.R;

import com.example.mingujee.followersplus.Model.*;

/**
 * Created by mingu.jee on 2016-08-21.
 */
public class ToolView extends LinearLayout {
    Context context;

    User current_user;
    FollowingUsers following_users;

    ToggleButton toggleButton;

    boolean toggle;

    public ToolView(Context context, User current_user, FollowingUsers following_users, boolean toggle) {
        super(context);
        //Set the tool layout to this view
        View.inflate(context, R.layout.tool, this);
        this.context = context;

        this.current_user = current_user;
        this.following_users = following_users;

        this.toggle = toggle;

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        this.layoutView();
        this.registerControllers();
    }

    public void layoutView() {
        toggleButton.setChecked(toggle);
    }

    public void registerControllers() {

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Intent intent = new Intent(context, ConfigActivity.class);
                    intent.putExtra("CurrentUser", current_user);
                    intent.putExtra("FollowingUsers", following_users);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else {
                    // The toggle is disabled
                    Intent intent = new Intent(context, UnfollowActivity.class);
                    intent.putExtra("CurrentUser", current_user);
                    intent.putExtra("FollowingUsers", following_users);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
                toggleButton.setChecked(toggle);
            }
        });
    }
}
