package com.yanis.front_end_mobile;

public class Friend {

    String nameFriend;
    String familyNameFriend;
    String idUserAdmin;

    public Friend(String nameFriend, String familyNameFriend) {
        this.nameFriend = nameFriend;
        this.familyNameFriend = familyNameFriend;
    }

    public Friend(String nameFriend, String familyNameFriend, String idUserAdmin) {
        this.nameFriend = nameFriend;
        this.familyNameFriend = familyNameFriend;
        this.idUserAdmin = idUserAdmin;
    }

    public Friend() {
    }

    public String getIdUserAdmin() {
        return idUserAdmin;
    }

    public void setIdUserAdmin(String idUserAdmin) {
        this.idUserAdmin = idUserAdmin;
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
