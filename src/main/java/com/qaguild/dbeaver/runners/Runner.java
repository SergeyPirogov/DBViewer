package com.qaguild.dbeaver.runners;

import com.qaguild.dbeaver.processors.Processor;

import java.lang.reflect.Method;
import java.util.Map;

public interface Runner {

    void addProcessor(Processor processor);

    Map<String, Processor> getProcessors();
}
