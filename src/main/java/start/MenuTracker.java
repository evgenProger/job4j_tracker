package ru.job4j.start;

import java.util.ArrayList;

public class MenuTracker  {
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
     * @param input объект типа Input
     * @param tracker объект типа Tracker
     */
    public MenuTracker(Input input, Store tracker) {
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
        this.actions.add(new AddItem(0, "Добавить новую заявку"));
        this.actions.add(new UpdateItem(1, "Редактировать заявку"));
        this.actions.add(new ShowAll(2, "Показать все заявки"));
        this.actions.add(new DeleteItem(3, "Удалить заявку"));
        this.actions.add(new FindById(4, "Найти заявку по id"));
        this.actions.add(new FindByName(5, "Найти заявку по имени"));
        this.actions.add(new Exit(input, 6, "Выйти из программы"));
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
        for (UserAction action: this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }
}

