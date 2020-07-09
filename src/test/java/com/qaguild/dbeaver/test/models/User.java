package com.qaguild.dbeaver.test.models;

import com.qaguild.dbeaver.annotations.Column;
import com.qaguild.dbeaver.annotations.Id;
import com.qaguild.dbeaver.annotations.TableName;

@TableName("public.\"user\"")
public class User {

    @Id(generated = false)
    private long id;
    private String name;

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
