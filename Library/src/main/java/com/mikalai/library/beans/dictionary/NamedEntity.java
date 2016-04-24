package com.mikalai.library.beans.dictionary;

import com.mikalai.library.beans.BasicEntity;

import javax.persistence.MappedSuperclass;

/**
 * Created by mikalai on 25.04.2016.
 */
@MappedSuperclass
public class NamedEntity extends BasicEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
