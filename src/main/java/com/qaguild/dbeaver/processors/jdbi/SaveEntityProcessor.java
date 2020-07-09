package com.qaguild.dbeaver.processors.jdbi;

import com.qaguild.dbeaver.annotations.Column;
import com.qaguild.dbeaver.annotations.Id;
import com.qaguild.dbeaver.processors.AbstractProcessor;
import com.qaguild.dbeaver.processors.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SaveEntityProcessor extends AbstractProcessor<Jdbi> {

    public SaveEntityProcessor(Jdbi executor) {
        super(executor);
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String table = Utils.getTableName(clazz);

        List<String> fields = getFields(clazz);

        String f = String.join(",", fields);
        //TODO use args[0] for fields and params because it's the same object
        List<String> queryParams = getQueryParams(args[0]);

        String arg = String.join(",", queryParams);
        String query = String.format("INSERT INTO %s(%s) VALUES(%s)", table, f, arg);

        return executor.withHandle(h -> h.execute(query));
    }

    private List<String> getQueryParams(Object parameter) {
        List<String> queryParams = new ArrayList<>();
        Field[] parameterFields = parameter.getClass().getDeclaredFields();
        for (Field field : parameterFields) {
            Id id = field.getDeclaredAnnotation(Id.class);
            if (id != null && id.generated()) {
                continue;
            }

            field.setAccessible(true);
            try {
                String value = field.get(parameter).toString();
                if (field.getType() == String.class) {
                    value = String.format("'%s'", value);
                }
                queryParams.add(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return queryParams;
    }

    private List<String> getFields(Class<?> clazz) {
        List<String> fields = new ArrayList<>();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Id id = field.getDeclaredAnnotation(Id.class);
            if (id != null && id.generated()) {
                continue;
            }

            String fieldName = field.getName();
            Column annotation = field.getDeclaredAnnotation(Column.class);
            if (annotation != null) {
                fieldName = annotation.value();
            }
            fields.add(fieldName);
        }
        return fields;
    }
}
