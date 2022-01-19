package ru.job4j.start;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ValidateInputTest {
    @Test
    public void whenInputInvalid() {
        Output output = new StubOutput();
        ValidateInput input = new ValidateInput(new StubInput(new String[] {"invalid", "1"}), output);
        List<Integer> range = new ArrayList<>();
        range.add(1);
        input.ask("Enter", range);
        assertThat(
                output.toString(),
                is(
                        String.format("Пожалуйста, введите корректные данные снова%n")
                )
        );
    }
    @Test
    public void whenInputNotHasPointMenu() {
        Output output = new StubOutput();
        ValidateInput input = new ValidateInput(new StubInput(new String[] {"90", "1"}), output);
        List<Integer> range = new ArrayList<>();
        range.add(1);
        input.ask("Enter", range);
        assertThat(
                output.toString(),
                is(
                        String.format("Пожалуйста введите ключ из меню%n")
                )
        );
    }
}