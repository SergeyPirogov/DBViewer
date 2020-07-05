package com.qaguild.dbeaver.test.repo;

import com.qaguild.dbeaver.repositories.Repository;
import com.qaguild.dbeaver.test.models.User;

import java.util.List;

public interface UserRepo extends Repository<Integer, User> {

    List<User> findByName(String name);
}
