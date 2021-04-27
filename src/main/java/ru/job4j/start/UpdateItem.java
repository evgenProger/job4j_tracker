package ru.job4j.start;

import ru.job4j.models.*;
import ru.job4j.start.*;

public class UpdateItem extends BaseAction {
    public UpdateItem(int key, String name) {
        super(key, name);
    }
    @Override
    public void execute(Input input, Store tracker) {
        System.out.println("------Редактирование заявки------");
        String id = input.ask("Введите id заявки");
        String name = input.ask("Введите новое имя заявки");
        String desc = input.ask("Введите новое описание заявки");
        Item item = new Item(name, desc);
        if (tracker.replace(id, item)) {
            System.out.println("------Новая заявка с id:" + item.getId());
        } else {
            System.out.println("----Заявка не найдена----");
        }

    }


}
