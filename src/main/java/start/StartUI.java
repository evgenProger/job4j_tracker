package ru.job4j.start;

import ru.job4j.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version $ID$
 * @since 26.04.2019
 */

public class StartUI {

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
     * @param input   ввод данных
     * @param tracker хранилище заявок
     */

    public StartUI(Input input, Store tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основной цикл программы
     */

    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
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
            new StartUI(new ValidateInput(new ConsoleInput()), tracker).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
