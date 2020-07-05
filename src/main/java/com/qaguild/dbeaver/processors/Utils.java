package com.qaguild.dbeaver.processors;

import com.qaguild.dbeaver.annotations.Id;
import com.qaguild.dbeaver.annotations.TableName;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;

public class Utils {

    public static String getTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        TableName annotation = clazz.getDeclaredAnnotation(TableName.class);
        if (annotation != null) {
            tableName = annotation.value();
        }
        return tableName;
    }

    public static String getIdFieldName(Class<?> clazz) {
        Optional<Field> field = Stream.of(clazz.getDeclaredFields())
                .filter(it -> it.getDeclaredAnnotation(Id.class) != null).findFirst();

        String fieldName = "id";
        if (field.isPresent()) {
            fieldName = field.get().getName();
        }
        return fieldName;
    }
}
