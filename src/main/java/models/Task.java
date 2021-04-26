package ru.job4j.models;

public class Task extends Item {
    public Task(String name, String desc) {
        super(name, desc, System.currentTimeMillis());
    }
}
