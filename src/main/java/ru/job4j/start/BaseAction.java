package ru.job4j.start;

public abstract class BaseAction implements UserAction {
    private final String name;


    protected BaseAction(String name) {
        this.name = name;
    }

    @Override
    public String info() {
        return String.format("%s", this.name);
    }

}

