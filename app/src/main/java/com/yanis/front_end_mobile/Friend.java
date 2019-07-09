package com.yanis.front_end_mobile;

public class Friend {

    String nameFriend;
    String familyNameFriend;

    public Friend(String nameFriend, String familyNameFriend) {
        this.nameFriend = nameFriend;
        this.familyNameFriend = familyNameFriend;
    }

    public String getNameFriend() {
        return nameFriend;
    }

    public void setNameFriend(String nameFriend) {
        this.nameFriend = nameFriend;
    }

    public String getFamilyNameFriend() {
        return familyNameFriend;
    }

    public void setFamilyNameFriend(String familyNameFriend) {
        this.familyNameFriend = familyNameFriend;
    }
}
