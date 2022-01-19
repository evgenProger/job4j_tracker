package ru.job4j.start;

import ru.job4j.models.*;

public class UpdateItem extends BaseAction {
    private final Output out;
    public UpdateItem(Output out) {
        super("Редактировать заявку");
        this.out = out;
    }
    @Override
    public void execute(Input input, Store tracker) {
        out.println("------Редактирование заявки------");
        String id = input.ask("Введите id заявки");
        String name = input.ask("Введите новое имя заявки");
        String desc = input.ask("Введите новое описание заявки");
        Item item = new Item(name, desc);
        if (tracker.replace(id, item)) {
            out.println("------Новая заявка с id:" + item.getId());
        } else {
            out.println("----Заявка не найдена----");
        }
    }
}
