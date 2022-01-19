package ru.job4j.start;

import ru.job4j.models.*;

public class FindById extends BaseAction {
    private final Output out;

    public FindById(String name, Output out) {
        super(name);
        this.out = out;
    }


    @Override
    public void execute(Input input, Store tracker) {
        out.println("-----Поиск заявки по id-----");
        String id = input.ask("Введите id заявки");
        Item item = tracker.findById(id);
        if (item != null) {
            out.println("Найденная заявка" + item);
        } else {
            out.println("---- Заявка с таким id не найдена -----");
        }
    }


}
