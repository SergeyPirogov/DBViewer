package com.qaguild.dbeaver.test;

import com.qaguild.dbeaver.DBeaver;
import com.qaguild.dbeaver.runners.QueryRunner;
import com.qaguild.dbeaver.test.models.User;
import com.qaguild.dbeaver.test.repo.UserRepo;
import com.qaguild.dbeaver.test.runner.MyRunner;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseTest {

    @Container
    private static final PostgreSQLContainer db = new PostgreSQLContainer();

    protected static UserRepo userRepo;

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

        QueryRunner queryRunner = MyRunner.create(jdbi);
        DBeaver dBeaver = new DBeaver(queryRunner);
        userRepo = dBeaver.init(UserRepo.class);
    }
}
