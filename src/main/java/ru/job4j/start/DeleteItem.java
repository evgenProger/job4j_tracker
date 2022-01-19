package ru.job4j.start;

public class DeleteItem extends BaseAction {
    private final Output out;

    public DeleteItem(String name, Output out) {
        super(name);
        this.out = out;
    }


    @Override
    public void execute(Input input, Store tracker) {
        out.println("-------Удаление заявки------");
        String id = input.ask("Введите id заявки");
        if (tracker.delete(id)) {
            out.println("---- Удаление заявки прошло успешно ----");
        } else {
            out.println("----- Заявка с таким id не найдена");
        }
    }
}
