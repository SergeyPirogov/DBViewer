package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.Order;
import com.qaguild.dbeaver.test.models.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.qaguild.dbeaver.Order.orderBy;
import static com.qaguild.dbeaver.SortType.DESC;
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

    @Test
    void testCanGetAllWithOrderDesc() {
        List<String> users = userRepo.findAll(orderBy("name", DESC))
                .stream()
                .map(User::getName)
                .collect(Collectors.toList());

        assertThat(users).isEqualTo(Arrays.asList("Dima", "Alice"));
    }

    @Test
    void testCanGetAllWithOrderAsc() {
        List<String> users = userRepo.findAll(orderBy("name"))
                .stream()
                .map(User::getName)
                .collect(Collectors.toList());

        assertThat(users).isEqualTo(Arrays.asList("Alice", "Dima"));
    }
}