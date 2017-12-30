package com.example.mingujee.followersplus.View;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.net.*;
import java.io.*;

import com.example.mingujee.followersplus.AsyncController.GetUsersToFollow;
import com.example.mingujee.followersplus.Helper.Utilities;
import com.example.mingujee.followersplus.Model.Authentication;
import com.example.mingujee.followersplus.Model.BasePaths;
import com.example.mingujee.followersplus.Model.FollowingUsers;
import com.example.mingujee.followersplus.Model.User;
import com.example.mingujee.followersplus.R;

import org.json.*;

/**
 * Created by mingu.jee on 2016-07-24.
 */
public class ConfigurationView extends LinearLayout {

    Context context;

    String access_token;

    EditText hashtags;
    EditText followers;
    Spinner followers_spinner;
    EditText following;
    Spinner following_spinner;
    ArrayAdapter<CharSequence> adapter;
    Button SubmitButton;

    FollowingUsers followingUsers;

    public ConfigurationView(Context context, String access_token, FollowingUsers followingUsers) {
        super(context);
        //Set the configuration layout to this view
        View.inflate(context, R.layout.configuration, this);
        this.context = context;

        this.access_token = access_token;

        hashtags = (EditText) findViewById(R.id.hashtagEdit);

        adapter = ArrayAdapter.createFromResource(context,
                R.array.string_array, android.R.layout.simple_spinner_item);

        followers = (EditText) findViewById(R.id.followersEdit);
        followers_spinner = (Spinner) findViewById(R.id.followersSpinner);
        followers_spinner.setAdapter(adapter);

        following = (EditText) findViewById(R.id.followingEdit);
        following_spinner = (Spinner) findViewById(R.id.followingSpinner);
        following_spinner.setAdapter(adapter);

        this.followingUsers = followingUsers;

        SubmitButton = (Button) findViewById(R.id.submitButton);

        this.layoutView();
        this.registerControllers();
    }

    public void layoutView() {

    }

    public void registerControllers() {

        SubmitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetUsersToFollow(context,
                        followingUsers,
                        Double.parseDouble(followers.getText().toString()),
                        Double.parseDouble(following.getText().toString()),
                        followers_spinner.getSelectedItem().toString(),
                        following_spinner.getSelectedItem().toString(),
                        access_token, hashtags.getText().toString()).execute();
            }
        });
    }
}
