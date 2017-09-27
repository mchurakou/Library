package com.mikalai.library.beans;

import javax.persistence.*;

/**
 * Created by mikalai on 24.04.2016.
 */

@MappedSuperclass
@Access(value = AccessType.FIELD)
public class BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



}
