package com.example.mingujee.followersplus.View;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.*;

import com.example.mingujee.followersplus.Model.FollowingUsers;
import com.example.mingujee.followersplus.R;

import java.util.*;

/**
 * Created by mingu.jee on 2016-07-24.
 */
public class ResultView extends LinearLayout implements Observer {

    Context context;

    Button FollowingListButton;
    TextView ResultTextView;

    FollowingUsers followingUsers;

    Dialog followingList;

    public ResultView (Context context, FollowingUsers followingUsers) {
        super(context);
        //Set the result layout to this view
        View.inflate(context, R.layout.results, this);
        this.context = context;

        FollowingListButton = (Button) findViewById(R.id.followingListButton);
        FollowingListButton.setVisibility(View.INVISIBLE);
        ResultTextView = (TextView) findViewById(R.id.resultText);

        this.followingUsers = followingUsers;

        this.layoutView();
        this.registerControllers();
    }

    public void layoutView() {

    }

    public void registerControllers() {

    }

    public void update(Observable o, Object arg) {
        ResultTextView.setText("Result: " + followingUsers.getSize());
        FollowingListButton.setVisibility(View.VISIBLE);
    }
}
