package com.qaguild.dbeaver.handlers;

import com.qaguild.dbeaver.QueryRunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Handler implements InvocationHandler {

    private final Class<?> clazz;
    private final QueryRunner queryRunner;

    public Handler(Class<?> clazz, QueryRunner queryRunner) {
        this.clazz = clazz;
        this.queryRunner = queryRunner;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalArgumentException, ClassNotFoundException {

        Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (genericInterfaces[0] instanceof ParameterizedType) {
            Type[] genericTypes = ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments();
            String className = genericTypes[1].toString().split(" ")[1];
            Class<?> clazz = Class.forName(className);
            return queryRunner.process(method, clazz, args);
        }

        return null;
    }
}
