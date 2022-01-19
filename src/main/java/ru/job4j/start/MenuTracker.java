package ru.job4j.start;

import java.util.ArrayList;

public class MenuTracker  {

    private final Output output;
    /**
     * @param хранит ссылку на объект
     */
    private final Input input;
    /**
     * @param хранит ссылку на объект
     */
    private final Store tracker;
    /**
     * @param хранит ссылку на массив UserAction
     */
    private ArrayList<UserAction> actions = new ArrayList<>();
    /**
     * Конструктор
     * @param output
     * @param input объект типа Input
     * @param tracker объект типа Tracker
     */
    public MenuTracker(Output output, Input input, Store tracker) {
        this.output = output;
        this.input = input;
        this.tracker = tracker;
    }
    /**
     * Метод для получения массива меню
     *
     * @return длинну массива
     */

    public int getActionsLentgh() {
        return this.actions.size();
    }

    /**
     * Метод заполняет массив
     */
     public void fillActions(StartUI input) {
        this.actions.add(new AddItem("Добавить новую заявку", output));
        this.actions.add(new UpdateItem(output));
        this.actions.add(new ShowAll("Показать все заявки", output));
        this.actions.add(new DeleteItem("Удалить заявку", output));
        this.actions.add(new FindById("Найти заявку по id", output));
        this.actions.add(new FindByName("Найти заявку по имени", output));
        this.actions.add(new Exit(input, "Выйти из программы", output));
    }
    /**
     * Метод в зависимости от указанного ключа, выполняет соответствующее действие
     *
     * @param key ключ операции
     */

    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }
    /**
     * Метод выводит на экран меню
     */

    public void show() {
        int key = 0;
        for (UserAction action: this.actions) {
            if (action != null) {
                output.println(key++ + " " + action.info());
            }
        }
    }
}

