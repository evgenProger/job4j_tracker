package ru.job4j.start;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Item;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StartUITest {
    private final MemTracker tracker = new MemTracker();
    private Output stdout = new StubOutput();     
    private final String ln = System.lineSeparator();
    private final String menu = this.showMenu();

    private final String showMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("0 Добавить новую заявку").append(ln);
        menu.append("1 Редактировать заявку").append(ln);
        menu.append("2 Показать все заявки").append(ln);
        menu.append("3 Удалить заявку").append(ln);
        menu.append("4 Найти заявку по id").append(ln);
        menu.append("5 Найти заявку по имени").append(ln);
        menu.append("6 Выйти из программы").append(ln);
        return menu.toString();
    }

    @Before
    public void loadOutput() {
       stdout = new StubOutput();
    }

    @Test
    public void whenuserEnterShowAllItemsThenOutAllItems() {
        Item one = this.tracker.add(new Item("testname1", "testdesc1"));
        Item two = this.tracker.add(new Item("testname2", "testdesc2"));
        Input input = new StubInput(new String[]{"2", "6"});
        new StartUI(stdout, input, tracker).init();
        StringBuilder showalls = new StringBuilder(menu);
        showalls.append("------Отображение всех заявок------").append(ln);
        showalls.append(one).append(ln);
        showalls.append(two).append(ln);
        showalls.append(menu).append(ln);
        assertThat(stdout.toString(), is(showalls.toString()));
    }

    @Test
    public void whenUserAddItemThenTrackerHasNewItemWithSomeName() {
        Input input = new StubInput(new String[]{"0", "testname", "testdesc", "6"});
        new StartUI(stdout, input, this.tracker).init();
        assertThat(tracker.getAll().get(0).getName(), is("testname"));
    }

    @Test
    public void whenUpdateYhenTrackerHasUpdateValue() {
        Item item = tracker.add(new Item("testname", "testdescription"));
        Input input = new StubInput(new String[]{"1", item.getId(), "testreplace", "replaceitem", "6"});
        new StartUI(stdout, input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("testreplace"));
    }

    @Test
    public void whenDeleteItemThenTrackerHasUpdateValue() {
        Item one = tracker.add(new Item("testname1", "testdescription1"));
        Item two = tracker.add(new Item("testname2", "testdescription2"));
        Input input = new StubInput(new String[]{"3", one.getId(), "6"});
        new StartUI(stdout, input, tracker).init();
        List<Item> result = tracker.findAll();
        assertThat(result.get(0).getName(), is("testname2"));
    }

    @Test
    public void whenFindItemByIdThenWeHaveFoundItem() {
        Item one = tracker.add(new Item("testname1", "testdescription1"));
        Item two = tracker.add(new Item("testname2", "testdescription2"));
        Input input = new StubInput(new String[]{"4", two.getId(), "6"});
        new StartUI(stdout, input, tracker).init();
        StringBuilder showitembyid = new StringBuilder(menu);
        showitembyid.append("-----Поиск заявки по id-----").append(ln);
        showitembyid.append("Найденная заявка");
        showitembyid.append(two).append(ln);
        showitembyid.append(menu).append(ln);
        assertThat(stdout.toString(), is(showitembyid.toString()));
    }

    @Test
    public void whenFindItemByNameThenWeHaveFoundItem() {
        Item one = tracker.add(new Item("testname1", "testdescription1"));
        Item two = tracker.add(new Item("testname2", "testdescription2"));
        Input input = new StubInput(new String[]{"5", two.getName(), "6"});
        new StartUI(stdout, input, tracker).init();
        StringBuilder showitemsbynames = new StringBuilder(menu);
        showitemsbynames.append("----Поиск заявки по имени----").append(ln);
        showitemsbynames.append("Найденная заявка:");
        showitemsbynames.append(Arrays.toString(new Item[]{two})).append(ln);
        showitemsbynames.append(menu).append(ln);
        assertThat(stdout.toString(), is(showitemsbynames.toString()));
    }


    @Test
    public void whenUpdateThenTrackerHasUpdateValueUseMockito() {
        Item item = tracker.add(new Item("testname", "testdescription"));
        String replacedName = "replacedName";
        String replacedDesc = "replacedDesc";
        Input input = mock(Input.class);
        UpdateItem updateItem = new UpdateItem(stdout);
        when(input.ask(any(String.class))).thenReturn(item.getId()).thenReturn(replacedName).thenReturn(replacedDesc);
        updateItem.execute(input, tracker);
        assertThat(tracker.findById(item.getId()).getName(), is(replacedName));
        assertThat(tracker.findById(item.getId()).getDescription(), is(replacedDesc));
    }

    @Test
    public void whenDeleteItemThenTrackerHasUpdateValueUseMockito() {
        Item one = tracker.add(new Item("testname1", "testdescription1"));
        Item two = tracker.add(new Item("testname2", "testdescription2"));
        Input input = mock(Input.class);
        DeleteItem delete = new DeleteItem(one.getName(), stdout);
        when(input.ask(any(String.class))).thenReturn(one.getId());
        delete.execute(input, tracker);
        List<Item> result = tracker.findAll();
        assertThat(result.get(0).getName(), is("testname2"));
        assertThat(result.size(), is(1));
    }

    @Test
    public void whenFindItemByNameThenWeHaveFoundItemUseMockito() {
        Item one = tracker.add(new Item("testname1", "testdescription1"));
        Item two = tracker.add(new Item("testname2", "testdescription2"));
        Input input = mock(Input.class);
        FindByName find = new FindByName(two.getName(), stdout);
        when(input.ask(any(String.class))).thenReturn(two.getName());
        find.execute(input, tracker);
        StringBuilder showitemsbynames = new StringBuilder();
        showitemsbynames.append("----Поиск заявки по имени----").append(ln);
        showitemsbynames.append("Найденная заявка:");
        showitemsbynames.append(Arrays.toString(new Item[]{two})).append(ln);
        assertThat(stdout.toString(), is(showitemsbynames.toString()));
    }

    @Test
    public void whenFindItemByIdThenWeHaveFoundItemUseMockito() {
        Item one = tracker.add(new Item("testname1", "testdescription1"));
        Item two = tracker.add(new Item("testname2", "testdescription2"));
        Input input = mock(Input.class);
        FindById find = new FindById(two.getName(), stdout);
        when(input.ask(any(String.class))).thenReturn(two.getId());
        find.execute(input, tracker);
        StringBuilder showitembyid = new StringBuilder();
        showitembyid.append("-----Поиск заявки по id-----").append(ln);
        showitembyid.append("Найденная заявка");
        showitembyid.append(two).append(ln);
        assertThat(stdout.toString(), is(showitembyid.toString()));
    }
}