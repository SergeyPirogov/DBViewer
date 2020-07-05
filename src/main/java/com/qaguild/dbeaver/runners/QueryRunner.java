package com.qaguild.dbeaver.runners;

import com.qaguild.dbeaver.processors.Processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class QueryRunner implements Runner {
    private final Map<String, Processor> processors = new HashMap<>();

    @Override
    public final void addProcessor(Processor processor) {
        processors.put(processor.getName(), processor);
    }

    @Override
    public final Map<String, Processor> getProcessors() {
        return processors;
    }

    protected abstract Processor getProcessor(Method method);

    protected Object process(Method method, Class<?> clazz, Object[] args) {
        Processor processor = getProcessor(method);
        return processor.process(method, clazz, args);
    }
}
