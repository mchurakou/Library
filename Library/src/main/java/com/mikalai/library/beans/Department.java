package com.mikalai.library.beans;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mikalai on 25.04.2016.
 */

@Entity
@Table(name="departments")
public class Department extends NamedEntity {



    public Department(int id) {
        setId(id);
    }
    public Department() {
    }
}
