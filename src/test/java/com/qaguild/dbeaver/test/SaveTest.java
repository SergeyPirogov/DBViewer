package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.test.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SaveTest extends BaseTest {

    @Test
    void testCanSaveEntity() {
        User user = new User();
        user.setId(3);
        user.setName("Ivan");

        Integer value = userRepo.save(user);

        assertThat(value).isEqualTo(1);

        User newUser = userRepo.findOne(3);

        assertThat(newUser.getName()).isEqualTo(user.getName());
    }

    @Test
    void testCanUpdateEntity() {
        User user = new User();
        user.setId(3);
        user.setName("Petya");

        Integer value = userRepo.save(user);

        User oldUser = userRepo.findOne(3);

        assertThat(value).isEqualTo(1);
        assertThat(oldUser.getName()).isEqualTo(user.getName());

        User newUser = userRepo.findOne(3);
        newUser.setName("Mykola");

        value = userRepo.save(newUser);

        User updatedUser = userRepo.findOne(3);

        assertThat(value).isEqualTo(1);
        assertThat(updatedUser.getName()).isEqualTo(newUser.getName());

    }
}
