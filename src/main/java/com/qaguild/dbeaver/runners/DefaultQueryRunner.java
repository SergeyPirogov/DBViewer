package com.qaguild.dbeaver.runners;

import com.qaguild.dbeaver.QueryRunner;
import com.qaguild.dbeaver.exceptions.DBeaverException;
import com.qaguild.dbeaver.processors.Processor;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.qaguild.dbeaver.processors.Processors.*;

public class DefaultQueryRunner implements QueryRunner {

    public static final String FALLBACK_METHOD_NAME = "findBy";
    private final Map<String, Processor> processors = new HashMap<>();

    public DefaultQueryRunner(Jdbi jdbi) {
        addProcessor(findOneById(jdbi));
        addProcessor(findAll(jdbi));
        addProcessor(findByColumn(jdbi));
    }

    public final void addProcessor(Processor processor) {
        processors.put(processor.getName(), processor);
    }

    public final Map<String, Processor> getProcessors() {
        return processors;
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        Processor processor = getProcessor(method);
        return processor.process(method, clazz, args);
    }

    public Processor getProcessor(Method method) {
        String methodName = method.getName();
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
