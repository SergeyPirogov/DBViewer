package com.qaguild.dbeaver.repositories;

import com.qaguild.dbeaver.Order;

import java.util.List;

public interface Repository<S, T> {
    T findOne(S id);

    List<T> findAll();

    List<T> findAll(Order... order);

    void delete(S id);
}
