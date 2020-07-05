package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.runners.jdbi.JDBIQueryRunner;
import org.jdbi.v3.core.Jdbi;

public class MyRunner extends JDBIQueryRunner {

    @Override
    protected void initProcessors(Jdbi jdbi) {
        super.initProcessors(jdbi);
    }
}
