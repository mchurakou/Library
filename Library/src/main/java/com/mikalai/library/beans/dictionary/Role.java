package com.mikalai.library.beans.dictionary;

/**
 * Created by mikalai on 25.04.2016.
 */
public enum Role {
    NEW(1), USER(2), LIBRARIAN(3), ADMINISTRATOR(4);

    public int getId() {
        return id;
    }

    int id;

    Role(int id){
        this.id = id;
    }

    public static Role getById(int id){
        Role res = null;
        for (Role role : values()){
            if (role.id == id){
                res = role;
                break;

            }
        }

        return res;

    }
}
