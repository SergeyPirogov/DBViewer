package com.qaguild.dbeaver.processors;

abstract class AbstractProcessor<T> implements Processor {
    protected T executor;

    public AbstractProcessor(T executor) {
        this.executor = executor;
    }
}
