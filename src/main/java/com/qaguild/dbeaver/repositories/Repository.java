package com.qaguild.dbeaver.repositories;

public interface Repository<S, T> {
    T findOne(S id);
}
