package com.example.mingujee.followersplus.View;

import com.example.mingujee.followersplus.Model.User;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.*;
import android.content.*;
import android.widget.ArrayAdapter;
import java.util.*;
import android.view.*;
import com.example.mingujee.followersplus.R;
import android.widget.*;
import com.example.mingujee.followersplus.AsyncController.GetProfilePic;
import com.example.mingujee.followersplus.AsyncController.RemoveUnfollower;
import com.example.mingujee.followersplus.AsyncController.FollowUser;

/**
 * Created by mingu.jee on 2016-12-05.
 */
public class UsersAdapter extends ArrayAdapter<User> {
    Context context;
    Boolean unfollowed;
    Boolean followed;
    String action;
    String path;

    public UsersAdapter(Context context, ArrayList<User> users, String action) {
        super(context, 0, users);
        this.context = context;
        unfollowed = false;
        this.action = action;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }

        //Setup surface touch event
        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    path = "http://instagram.com/_u/" + user.getUserName();
                    Uri uri = Uri.parse(path);
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.instagram.android");

                    try {
                        context.startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(path)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Lookup view for data population
        ImageView profileImageView = (ImageView) convertView.findViewById(R.id.profile_image);
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.userID);
        if(action.equals("follow")) {
            final Button followButton = (Button) convertView.findViewById(R.id.unfollow_button);
            followButton.setText("Follow");

            try {
                // Populate the data into the template view using the data object
                profileImageView.setImageBitmap(new GetProfilePic(context, user.getProfileURL()).execute().get());
                userNameTextView.setText(user.getFullName());
                followButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            followed = new FollowUser(context, user.getAccessToken(), user.getID()).execute().get();
                            if(followed) {
                                //Disable the button
                                followButton.setEnabled(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(action.equals("unfollow")) {
            final Button unfollowButton = (Button) convertView.findViewById(R.id.unfollow_button);
            unfollowButton.setText("Unfollow");

            try {
                // Populate the data into the template view using the data object
                profileImageView.setImageBitmap(new GetProfilePic(context, user.getProfileURL()).execute().get());
                userNameTextView.setText(user.getFullName());
                unfollowButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            unfollowed = new RemoveUnfollower(context, user.getAccessToken(), user.getID()).execute().get();
                            if(unfollowed) {
                                //Disable the button
                                unfollowButton.setEnabled(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
