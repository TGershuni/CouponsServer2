package com.tehila.coupons.dto;

public class Category {

    private int id;

    private String name;

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "\n" + "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

