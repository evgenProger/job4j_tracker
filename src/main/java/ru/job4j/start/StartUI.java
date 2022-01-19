package ru.job4j.start;

import java.util.ArrayList;
import java.util.List;

/**
 * @version $ID$
 * @since 26.04.2019
 */

public class StartUI {

    private Output output;

    private boolean working = true;
    /**
     * Получение данных от пользователя
     */
    private Input input;
    /**
     * Хранилище заявок
     */
    private Store tracker;
    /**
     * Конструктор инициализирующий поля
     *
     * @param output
     * @param input   ввод данных
     * @param tracker хранилище заявок
     */

    public StartUI(Output output, Input input, Store tracker) {
        this.output = output;
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основной цикл программы
     */

    public void init() {
        MenuTracker menu = new MenuTracker(output, this.input, this.tracker);
        List<Integer> range = new ArrayList<>();
        menu.fillActions(this);
        for (int i = 0; i < menu.getActionsLentgh(); i++) {
            range.add(i);
        }
        do {
            menu.show();
            menu.select(input.ask("select:", range));
        }
        while (this.working);
    }

    public void stop() {
        this.working = false;
    }

    /**
     * Запуск программы
     *
     * @param args
     */

    public static void main(String[] args) {
           try (Store tracker = new SqlTracker()) {
            tracker.init();
            new StartUI(new ConsolOutput(), new ValidateInput(new ConsoleInput(), new ConsolOutput()), tracker).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
