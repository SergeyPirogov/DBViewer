package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.test.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SelectTest extends BaseTest {

    @Test
    public void testCanFindOneRecordById() {
        User user = userRepo.findOne(0);

        assertThat(user.getName()).isEqualTo("Alice");
    }

    @Test
    public void testNullIfRecordIsAbsent() {
        User user = userRepo.findOne(50);

        assertThat(user).isNull();
    }

    @Test
    void testCanGetAllRows() {
        List<User> user = userRepo.findAll();

        assertThat(user.size()).isEqualTo(2);
    }

    @Test
    void testCanGetRecordByName() {
        List<User> users = userRepo.findByName("Dima");

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getId()).isEqualTo(1);
    }
}