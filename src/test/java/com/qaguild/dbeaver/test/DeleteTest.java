package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.test.models.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteTest extends BaseTest {

    @Test
    void testCanDeleteRecordById() {
        User user = userRepo.findOne(0);

        assertThat(user.getName()).isEqualTo("Alice");

        userRepo.delete(0);
        user = userRepo.findOne(0);

        assertThat(user).isNull();
    }
}
