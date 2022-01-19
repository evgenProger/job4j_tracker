package ru.job4j.start;

public class ConsolOutput implements Output {
    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }
}
