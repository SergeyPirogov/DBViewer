package com.qaguild.dbeaver;


import com.qaguild.dbeaver.QueryRunner;
import com.qaguild.dbeaver.handlers.Handler;
import com.qaguild.dbeaver.processors.Processor;
import com.qaguild.dbeaver.runners.DefaultQueryRunner;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.lang.reflect.*;


public class DBeaver {

    private final QueryRunner queryRunner;

    public DBeaver(Jdbi jdbi) {
        this(new DefaultQueryRunner(jdbi));
    }

    public DBeaver(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public void addProcessor(Processor processor) {
        queryRunner.addProcessor(processor);
    }

    public <T> T init(Class<? extends T> tClass) {
        Handler handler = new Handler(tClass, queryRunner);

        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),
                new Class[]{tClass},
                handler);
    }
}



