package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.test.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SaveTest extends BaseTest {

    @Test
    void testCanSaveEntity() {
        User user = new User();
        user.setId(3);
        user.setName("Kolya");

        Integer value = userRepo.save(user);

        assertThat(value).isEqualTo(1);

        User newUser = userRepo.findOne(3);

        assertThat(newUser.getName()).isEqualTo(user.getName());
    }
}
