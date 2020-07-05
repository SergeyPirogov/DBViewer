package com.qaguild.dbeaver.runners;

import com.qaguild.dbeaver.processors.jdbi.FindAllProcessor;
import com.qaguild.dbeaver.processors.jdbi.FindByColumnProcessor;
import com.qaguild.dbeaver.processors.jdbi.FindOneByIdProcessor;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.spi.JdbiPlugin;

import javax.sql.DataSource;

public class JDBIQueryRunner extends DefaultQueryRunner {

    public static JDBIQueryRunner create(DataSource dataSource, JdbiPlugin... plugin) {
        Jdbi jdbi = Jdbi.create(dataSource);
        for (JdbiPlugin jdbiPlugin : plugin) {
            jdbi.installPlugin(jdbiPlugin);
        }
        return create(jdbi);
    }

    public static JDBIQueryRunner create(Jdbi jdbi) {
        JDBIQueryRunner runner = new JDBIQueryRunner();
        runner.initProcessors(jdbi);
        return runner;
    }

    protected void initProcessors(Jdbi jdbi) {
        addProcessor(new FindOneByIdProcessor(jdbi));
        addProcessor(new FindAllProcessor(jdbi));
        addProcessor(new FindByColumnProcessor(jdbi));
    }
}
