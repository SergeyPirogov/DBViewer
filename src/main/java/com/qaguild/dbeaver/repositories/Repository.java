package com.qaguild.dbeaver.repositories;

import java.util.List;

public interface Repository<S, T> {
    T findOne(S id);

    List<T> findAll();

    void delete(S id);
}
