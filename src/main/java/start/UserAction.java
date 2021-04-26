package ru.job4j.start;

public interface UserAction {
    /**
     * Метод возвращает ключ опции
     *
     * @return ключ
     */
    int key();

    /**
     * Основной метод
     *
     * @param input   объект типа Input
     * @param tracker объект типа Tracker
     */
void execute(Input input, Store tracker);
    /**
     * Метод возвращает информацию о данном пункте меню
     *
     * @ Строка меню
     */
    String info();
}
