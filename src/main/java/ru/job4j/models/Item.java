package ru.job4j.models;

import lombok.Data;

@Data
public class Item {

    private String id;
    private String name;
    private String description;
    private long created;

    public Item() {
    }

    public Item(String name, String description, long created) {
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}



