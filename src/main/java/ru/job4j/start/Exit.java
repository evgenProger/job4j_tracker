package ru.job4j.start;

public class Exit extends BaseAction {

    private final StartUI ui;
    private final Output out;

    public Exit(StartUI input, String name, Output out) {
        super(name);
        this.ui = input;
        this.out = out;
    }

    @Override
    public void execute(Input input, Store tracker) {
        this.ui.stop();
        out.println("");
    }
}



