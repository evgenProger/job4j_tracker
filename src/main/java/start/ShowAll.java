package ru.job4j.start;

        import ru.job4j.models.*;

        import java.util.List;

public class ShowAll extends BaseAction {

    public ShowAll(int key, String name) {
        super(key, name);
    }


    @Override
    public void execute(Input input, Store tracker) {
        System.out.println("------Отображение всех заявок------");
        List<Item> items = tracker.findAll();
        for (Item item : items) {
            System.out.println(item);
        }

    }
}
