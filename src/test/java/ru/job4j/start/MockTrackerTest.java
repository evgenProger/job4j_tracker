package ru.job4j.start;

import org.junit.Test;
import ru.job4j.models.Item;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockTrackerTest {
    @Test
    public  void whenDeleteItemWithMokko() {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        Item item = new Item("TestName");
        String id =  tracker.add(item).getId();
        DeleteItem delete = new DeleteItem(item.getName(), out);
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn(id);
        delete.execute(input, tracker);
        assertEquals(tracker.getAll().size(), 0);
    }

    @Test
    public  void whenFindByIdWithMokko() {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        Item item = new Item("TestName");
        String id =  tracker.add(item).getId();
        FindById findById = new FindById(item.getName(), out);
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn(id);
        findById.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), "-----Поиск заявки по id-----" + ln + "Найденная заявка" + item + ln);
    }

    @Test
    public  void whenFindByNameWithMokko() {
        Output out = new StubOutput();
        MemTracker tracker = new MemTracker();
        Item item = new Item("TestName");
        String name =  tracker.add(item).getName();
        FindByName findByName = new FindByName(name, out);
        Input input = mock(Input.class);
        when(input.ask(any(String.class))).thenReturn(name);
        findByName.execute(input, tracker);
        String ln = System.lineSeparator();
        assertEquals(out.toString(), "----Поиск заявки по имени----"
                + ln + "Найденная заявка:" + List.of(item) + ln);
    }
}