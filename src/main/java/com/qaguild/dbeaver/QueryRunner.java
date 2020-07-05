package com.qaguild.dbeaver;

import com.qaguild.dbeaver.processors.Processor;

import java.lang.reflect.Method;

public interface QueryRunner {

    Object process(Method method, Class<?> clazz, Object[] args);

    void addProcessor(Processor processor);
}
