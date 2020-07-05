package com.qaguild.dbeaver.processors;

import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

class FindAllProcessor extends AbstractProcessor<Jdbi> {

    public FindAllProcessor(Jdbi executor) {
        super(executor);
    }

    @Override
    public String getName() {
        return "findAll";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String table = Utils.getTableName(clazz);
        String query = "SELECT * FROM ?";
        return executor.withHandle(h -> h.select(query, table).mapToBean(clazz).one());
    }
}
