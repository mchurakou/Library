package com.mikalai.library.beans;

import com.mikalai.library.beans.base.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mikalai on 28.10.2016.
 */

@Entity
@Table(name="user_categories")
public class Category extends NamedEntity {
    public Category(int id) {
        setId(id);
    }

    public Category() {
    }
}
