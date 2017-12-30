package com.example.mingujee.followersplus.View;

import android.content.Context;
import android.view.View;
import android.widget.*;

import com.example.mingujee.followersplus.AsyncController.GetUnfollowers;
import com.example.mingujee.followersplus.R;

/**
 * Created by mingu.jee on 2016-08-21.
 */
public class UnfollowView extends LinearLayout {
    Context context;

    Button unfollowButton;

    String access_token;

    public UnfollowView(Context context, String access_token) {
        super(context);
        //Set the unfollow layout to this view
        View.inflate(context, R.layout.unfollow, this);
        this.context = context;

        this.access_token = access_token;

        unfollowButton = (Button) findViewById(R.id.unfollowButton);

        this.layoutView();
        this.registerControllers();
    }

    public void layoutView() {

    }

    public void registerControllers() {

        unfollowButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetUnfollowers(context, access_token).execute();
            }
        });
    }
}
