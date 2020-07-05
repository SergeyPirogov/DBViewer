package com.qaguild.dbeaver.processors;

import org.apache.commons.lang3.StringUtils;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

class FindByColumnProcessor extends AbstractProcessor<Jdbi> {

    public FindByColumnProcessor(Jdbi executor) {
        super(executor);
    }

    @Override
    public String getName() {
        return "findBy";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String table = Utils.getTableName(clazz);
        Annotation[][] params = method.getParameterAnnotations();

        String field_name = method.getName().replace("findBy", "");
        ArrayList<String> fields = new ArrayList<>();

        for (Annotation[] param : params) {
            for (Annotation annotation : param) {
                if (annotation.annotationType() == ColumnName.class) {
                    ColumnName field = (ColumnName) annotation;
                    fields.add(field.value());
                }
            }
        }


        StringBuilder condition = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                condition.append(" AND ");
            }
            String name = fields.get(i);
            Object value = args[i];
            if (value instanceof String) {
                value = String.format("'%s'", value);
            }

            String s = String.format("%s = %s", name, value);
            condition.append(s);
        }

        if (StringUtils.isEmpty(condition)) {
            Object value = args[0];
            if (value instanceof String) {
                value = String.format("'%s'", value);
            }
            String s = String.format("%s = %s", field_name, value);
            condition.append(s);
        }

        String query = String.format("SELECT * FROM %s WHERE %s", table, condition.toString());
        return executor.withHandle(h -> h.createQuery(query)
                .mapToBean(clazz).list());
    }
}
