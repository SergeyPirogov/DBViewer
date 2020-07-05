package com.qaguild.dbeaver.processors.jdbi;

import com.qaguild.dbeaver.Order;
import com.qaguild.dbeaver.processors.AbstractProcessor;
import com.qaguild.dbeaver.processors.Utils;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FindAllProcessor extends AbstractProcessor<Jdbi> {

    public FindAllProcessor(Jdbi executor) {
        super(executor);
    }

    @Override
    public String getName() {
        return "findAll";
    }

    @Override
    public Object process(Method method, Class<?> clazz, Object[] args) {
        String table = Utils.getTableName(clazz);

        if (args != null && args.length > 0) {
            List<String> orderBy = new ArrayList<>();
            Order[] arr = (Order[]) args[0];
            for (Order order : arr) {
                orderBy.add(order.toString());
            }

            String query = String.format("SELECT * FROM %s ORDER BY %s", table, String.join(",", orderBy));
            return executor.withHandle(h -> h.select(query).mapToBean(clazz).list());
        }

        String query = String.format("SELECT * FROM %s", table);
        return executor.withHandle(h -> h.select(query).mapToBean(clazz).list());
    }
}
