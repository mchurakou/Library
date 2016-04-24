package com.mikalai.library.beans;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Created by mikalai on 24.04.2016.
 */

@MappedSuperclass
public class BasicEntity {

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



}
