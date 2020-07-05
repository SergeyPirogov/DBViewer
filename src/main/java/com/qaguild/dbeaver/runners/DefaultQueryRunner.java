package com.qaguild.dbeaver.runners;

import com.qaguild.dbeaver.exceptions.DBeaverException;
import com.qaguild.dbeaver.processors.Processor;

import java.lang.reflect.Method;
import java.util.Map;

public class DefaultQueryRunner extends QueryRunner {

    private static final String FALLBACK_METHOD_NAME = "findBy";

    @Override
    protected Processor getProcessor(Method method) {
        String methodName = method.getName();
        Map<String, Processor> processors = getProcessors();

        Processor processor = processors.get(methodName);
        if (processor != null) {
            return processor;
        }

        if (method.getName().startsWith(FALLBACK_METHOD_NAME)) {
            processor = processors.get(FALLBACK_METHOD_NAME);
            if (processor != null) {
                return processor;
            }
        }

        throw new DBeaverException(String.format("Processor for %s not found", methodName));
    }
}
