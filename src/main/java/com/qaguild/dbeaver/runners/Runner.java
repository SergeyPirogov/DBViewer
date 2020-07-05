package com.qaguild.dbeaver.runners;

import com.qaguild.dbeaver.processors.Processor;

import java.util.Map;

public interface Runner {

    Map<String, Processor> getProcessors();
}
