package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.DBeaver;
import com.qaguild.dbeaver.runners.QueryRunner;
import com.qaguild.dbeaver.runners.jdbi.JDBIQueryRunner;
import com.qaguild.dbeaver.test.models.User;
import com.qaguild.dbeaver.test.repo.UserRepo;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
public class SelectTest {
    @Container
    private static final PostgreSQLContainer db = new PostgreSQLContainer();
    private static QueryRunner queryRunner;

    private DBeaver dBeaver = new DBeaver(queryRunner);

    @BeforeAll
    static void beforeAll() {
        Jdbi jdbi = Jdbi.create(db.getJdbcUrl(), db.getUsername(), db.getPassword());

        jdbi.withHandle(handle -> {
            handle.execute("CREATE TABLE public.\"user\" (id integer PRIMARY KEY, name VARCHAR(50))");

            handle.execute("INSERT INTO public.\"user\"(id, name) VALUES (?, ?)", 0, "Alice");
            handle.execute("INSERT INTO public.\"user\"(id, name) VALUES (?, ?)", 1, "Dima");

            return handle.createQuery("SELECT * FROM public.\"user\" WHERE id  = 0")
                    .mapToBean(User.class)
                    .list();
        });

        queryRunner = MyRunner.create(jdbi);
    }

    @Test
    public void testCanFindOneRecordById() {
        UserRepo userRepo = dBeaver.init(UserRepo.class);
        User user = userRepo.findOne(0);

        assertThat(user.getName()).isEqualTo("Alice");
    }

    @Test
    public void testNullIfRecordIsAbsent() {
        UserRepo userRepo = dBeaver.init(UserRepo.class);
        User user = userRepo.findOne(50);

        assertThat(user).isNull();
    }

    @Test
    void testCanGetAllRows() {
        UserRepo userRepo = dBeaver.init(UserRepo.class);
        List<User> user = userRepo.findAll();

        assertThat(user.size()).isEqualTo(2);
    }

    @Test
    void testCanGetRecordByName() {
        UserRepo userRepo = dBeaver.init(UserRepo.class);
        List<User> users = userRepo.findByName("Dima");

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getId()).isEqualTo(1);
    }
}


class MyRunner extends JDBIQueryRunner {

    @Override
    protected void initProcessors(Jdbi jdbi) {
        super.initProcessors(jdbi);
    }
}