package ru.job4j.start;

import ru.job4j.models.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class FindByName extends BaseAction {

    public FindByName(int key, String name) {
        super(key, name);
    }


    @Override
    public void execute(Input input, Store tracker) {
        System.out.println("----Поиск заявки по имени----");
        String name = input.ask("Введите имя заявки");
        List<Item> item = null;
        item = tracker.findByName(name);
        System.out.println("Найденная заявка:" + item);

    }

}
