package com.qaguild.dbeaver;

public class Order {
    private String field;
    private SortType sortType;

    private Order(String field, SortType sortType) {
        this.field = field;
        this.sortType = sortType;
    }

    public static Order orderBy(String name, SortType sortType) {
        return new Order(name, sortType);
    }

    public static Order orderBy(String name) {
        return orderBy(name, SortType.ASC);
    }

    @Override
    public String toString() {
        return field + " " + sortType;
    }
}
