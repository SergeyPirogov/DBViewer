package com.qaguild.dbeaver.runners;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.spi.JdbiPlugin;

import javax.sql.DataSource;

import static com.qaguild.dbeaver.processors.Processors.*;

public class JDBIQueryRunner extends DefaultQueryRunner {

    public static JDBIQueryRunner create(DataSource dataSource, JdbiPlugin... plugin) {
        Jdbi jdbi = Jdbi.create(dataSource);
        for (JdbiPlugin jdbiPlugin : plugin) {
            jdbi.installPlugin(jdbiPlugin);
        }
        return create(jdbi);
    }

    public static JDBIQueryRunner create(Jdbi jdbi) {
        return new JDBIQueryRunner(jdbi);
    }

    private JDBIQueryRunner(Jdbi jdbi) {
        initProcessors(jdbi);
    }

    private void initProcessors(Jdbi jdbi) {
        addProcessor(findOneById(jdbi));
        addProcessor(findAll(jdbi));
        addProcessor(findByColumn(jdbi));
    }
}
