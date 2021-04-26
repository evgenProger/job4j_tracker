package ru.job4j.start;

import ru.job4j.models.*;

public class FindById extends BaseAction {

    public FindById(int key, String name) {
        super(key, name);
    }


    @Override
    public void execute(Input input, Store tracker) {
        System.out.println("-----Поиск заявки по id-----");
        String id = input.ask("Введите id заявки");
        Item item = tracker.findById(id);
        if (item != null) {
            System.out.println("Найденная заявка" + item);
        } else {
            System.out.println("---- Заявка с таким id не найдена -----");
        }
    }


}
