package ru.job4j.start;

public class Exit extends BaseAction {

    private final StartUI ui;

    public Exit(StartUI input, int key, String name) {
        super(key, name);
        this.ui = input;
    }

    @Override
    public void execute(Input input, Store tracker) {
        this.ui.stop();
        System.out.println();
    }
}



