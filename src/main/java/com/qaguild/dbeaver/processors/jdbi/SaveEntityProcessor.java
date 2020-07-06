package com.qaguild.dbeaver.processors.jdbi;

import com.qaguild.dbeaver.processors.AbstractProcessor;
import com.qaguild.dbeaver.processors.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<String> fields = new ArrayList<>();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            fields.add(field.getName());
        }

        String f = String.join(",", fields);

        Object parameter = args[0];
        List<String> queryParams = new ArrayList<>();
        Field[] parameterFields = parameter.getClass().getDeclaredFields();
        for (Field field : parameterFields) {
            field.setAccessible(true);
            try {
                String value = field.get(parameter).toString();
                if (field.getType() == String.class) {
                    value = String.format("'%s'", value);
                }
                queryParams.add(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String arg = String.join(",", queryParams);
        String query = String.format("INSERT INTO %s(%s) VALUES(%s)", table, f, arg);

        return executor.withHandle(h -> h.execute(query));
    }
}
