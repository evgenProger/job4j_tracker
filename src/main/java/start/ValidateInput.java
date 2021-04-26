package ru.job4j.start;

import java.util.List;

public class ValidateInput extends ConsoleInput {

    private final Input input;

    public ValidateInput(Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {


            try {
                value = super.ask(question, range);

                invalid = false;

            } catch (MenuOutException moe) {
                System.out.println("Пожалуйста введите ключ из меню");
            } catch (NumberFormatException nfe) {
                System.out.println("Пожалуйста, введите корректные данные снова");

            }
        } while (invalid);
        return value;


    }

}
