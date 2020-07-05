package com.qaguild.dbeaver.test.models;

import com.qaguild.dbeaver.annotations.TableName;

import java.lang.annotation.Target;

@TableName("public.\"user\"")
public class User {
    long id;
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
