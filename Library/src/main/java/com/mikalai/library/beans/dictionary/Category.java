package com.mikalai.library.beans.dictionary;

/**
 * Created by mikalai on 25.04.2016.
 */
public enum Category {
    STUDENT(1), EMPLOYEE(2),GRADUATE(3),TEACHER(4);


    public int getId() {
        return id;
    }

    int id;

    Category(int id){
        this.id = id;
    }

    public static Category getById(int id){
        Category res = null;
        for (Category category : values()){
            if (category.id == id){
                res = category;
                break;

            }
        }

        return res;

    }

}
