package ru.job4j.start;

import org.junit.Test;
import ru.job4j.models.Item;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;


public class TrackerTest {
    @Test
    public void whenAddNewItemThenTreckerHasSameItem() {
        MemTracker tracker = new MemTracker();
        long created = System.currentTimeMillis();
        Item item = new Item("test1", "testdescription", created);
        tracker.add(item);
        Item result = tracker.findById(item.getId());
        assertThat(result.getName(), is(item.getName()));


    }

    @Test
    public void whenReplaceNameThenRetornNewName() {
        MemTracker tracker = new MemTracker();
        Item previous = new Item("test1", "testdescription", 123L);
        //Добавляем заявку в трекер. Теперь в объект проинициализирован id.
        tracker.add(previous);
        //Создаем новую заявку
        Item next = new Item("test2", "testDescription2", 1234L);
        //Проставляем старый id из previous, который был сгенерирован выше
        next.setId(previous.getId());
        //Обновляем заявку в трекере
        tracker.replace(previous.getId(), next);
        //Проверяем, что заявка с таким id имеет новое имя test2
        assertThat(tracker.findById(previous.getId()).getName(), is("test2"));
    }

    @Test
    public void whenDeleteIdThenReturnNewLength() {
        MemTracker tracker = new MemTracker();
        Item one = new Item("test1", "testdescription1", 123L);
        Item two = new Item("test2", "testdescription2", 1234L);
        Item three = new Item("test3", "description3", 12345);
        tracker.add(one);
        tracker.add(two);
        tracker.add(three);
        tracker.delete(two.getId());
        List<Item> result = tracker.findAll();
        List<Item> expect = Arrays.asList(one, three);
        assertThat(result, is(expect));
    }

    @Test
    public void whenIntoArrayHasNullThenReturnNewArrayWithoutNull() {
        MemTracker tracker = new MemTracker();
        Item[] item = new Item[10];
        Item one = new Item("test1", "test1description1", 123L);
        Item two = new Item("test2", "test2description", 1234L);
        tracker.add(one);
        tracker.add(two);
        List<Item> list = tracker.findAll();
        int result = list.size();
        assertThat(result, is(2));



    }

    @Test
    public void whenFindNameThenCopyItemInResultArray() {
        MemTracker tracker = new MemTracker();
        Item one = new Item("test2", "testdescription1", 123L);
        Item two = new Item("test2", "testdescription2", 1234L);
        Item three = new Item("test3", "testdescriotion3", 125L);
        Item four = new Item("test2", "testdescription4", 564L);
        tracker.add(one);
        tracker.add(two);
        tracker.add(three);
        tracker.add(four);
        List<Item> result = tracker.findByName("test2");
        List<Item> expect = Arrays.asList(one, two, four);
        assertThat(result, is(expect));
    }

}