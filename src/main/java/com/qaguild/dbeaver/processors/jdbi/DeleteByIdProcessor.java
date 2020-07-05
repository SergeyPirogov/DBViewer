package com.qaguild.dbeaver.processors.jdbi;

import com.qaguild.dbeaver.processors.AbstractProcessor;
import com.qaguild.dbeaver.processors.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;

public class DeleteByIdProcessor extends AbstractProcessor<Jdbi> {

    public DeleteByIdProcessor(Jdbi executor) {
        super(executor);
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String table = Utils.getTableName(clazz);
        String fieldName = Utils.getIdFieldName(clazz);
        String query = String.format("DELETE FROM %s WHERE %s = ?", table, fieldName);
        return executor.withHandle(h -> h.execute(query, args[0]));
    }
}
