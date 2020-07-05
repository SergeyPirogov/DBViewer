package com.qaguild.dbeaver.processors;

import org.jdbi.v3.core.Jdbi;


public class Processors {

    public static Processor findOneById(Jdbi jdbi) {
        return new FindOneByIdProcessor(jdbi);
    }

    public static Processor findAll(Jdbi jdbi) {
        return new FindAllProcessor(jdbi);
    }

    public static Processor findByColumn(Jdbi jdbi) {
        return new FindByColumnProcessor(jdbi);
    }
}
