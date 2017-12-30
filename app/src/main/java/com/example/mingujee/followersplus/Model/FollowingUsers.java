package com.example.mingujee.followersplus.Model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by mingu.jee on 2016-08-06.
 */
public class FollowingUsers extends Observable implements Serializable {
    private LinkedList<User> users = new LinkedList<User>();

    public void addUser(User user) {
        users.add(user);
    }
    public User getUser(int index) {
        return users.get(index);
    }

    public int getSize() {
        return users.size();
    }
}
