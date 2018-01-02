package com.gdtech.core.base.entity;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author zhucy
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
