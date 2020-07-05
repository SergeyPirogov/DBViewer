package com.qaguild.dbeaver;


import com.qaguild.dbeaver.runners.Handler;
import com.qaguild.dbeaver.runners.QueryRunner;

import java.lang.reflect.Proxy;


public class DBeaver {

    private final QueryRunner queryRunner;

    public DBeaver(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public <T> T init(Class<? extends T> tClass) {
        Handler handler = new Handler(tClass, queryRunner);

        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),
                new Class[]{tClass},
                handler);
    }
}



