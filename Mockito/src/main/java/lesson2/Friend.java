package lesson2;

import java.util.List;

public class Friend {

    List<String> friends;

    public Friend(List<String> friends) {
        this.friends = friends;
    }

    public int getFriendsCount() {
        return friends.size();
    }
}