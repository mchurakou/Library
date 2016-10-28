package com.mikalai.library.beans;

import com.mikalai.library.beans.base.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mikalai on 29.10.2016.
 */

@Entity
@Table(name="languages")
public class Language extends NamedEntity {
}
