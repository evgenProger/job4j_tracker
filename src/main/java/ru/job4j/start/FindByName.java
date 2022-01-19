package ru.job4j.start;

import ru.job4j.models.*;

import java.util.List;

public class FindByName extends BaseAction {
    private final Output out;

    public FindByName(String name, Output out) {
        super(name);
        this.out = out;
    }


    @Override
    public void execute(Input input, Store tracker) {
        out.println("----Поиск заявки по имени----");
        String name = input.ask("Введите имя заявки");
        List<Item> item = tracker.findByName(name);
        out.println("Найденная заявка:" + item);
    }
}
