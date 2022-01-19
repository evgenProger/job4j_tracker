package ru.job4j.start;

import ru.job4j.models.Item;

public class AddItem extends BaseAction {
    private final Output out;

    public AddItem(String name, Output out) {
        super(name);
        this.out = out;
    }


    @Override
    public void execute(Input input, Store tracker) {
        out.println("-----Добавление новой заявки-----");
        String name = input.ask("Введите имя заявки");
        String description = input.ask("Введите описание заявки");
        Item item = new Item(name, description);
        tracker.add(item);
        out.println("------Новая заявка с id:" + item.getId());
        out.println("------Новая заявка с именем:" + item.getName());
        out.println("------Новая заявка с описанием:" + item.getDescription());
    }
}
