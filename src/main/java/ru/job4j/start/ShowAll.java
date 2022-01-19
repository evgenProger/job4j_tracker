package ru.job4j.start;

        import ru.job4j.models.*;

        import java.util.List;

public class ShowAll extends BaseAction {

    private final Output out;

    public ShowAll(String name, Output out) {
        super(name);
        this.out = out;
    }


    @Override
    public void execute(Input input, Store tracker) {
        out.println("------Отображение всех заявок------");
        List<Item> items = tracker.findAll();
        for (Item item : items) {
            out.println(item);
        }
    }
}
