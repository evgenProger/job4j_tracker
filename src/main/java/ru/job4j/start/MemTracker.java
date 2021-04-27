package ru.job4j.start;
import ru.job4j.models.*;
import java.lang.*;
import java.util.*;

public class MemTracker implements Store {
    private List<Item> items = new ArrayList<>();
    private int position = 0;
    private static final Random RN = new Random();

    @Override
    public void init() {

    }

    /**
     * Метод добавляет заявку, переданную в аргументах в массив заявок items. В методе генерируется уникадьный
     * ключ (id) на основании времени и произвольного числа, это делается вызовом Item.setID(this.generatedID). Где
     * generatedID и есть метод генерирующий наш id. После чего сгенерированная новая заявка добавляется в массив items
     * и позиция(position) ячейки сдвигается на одну.
     * @param item - наша заявка, содержащая поля класса Item (имя, описание, дата создания, уникальный ключ id).
     * @return возвращает добавленную заявку
     */

    public Item add(Item item) {
        item.setId(this.generatedId());
        items.add(position++, item);
        return item;
    }
    /**
     * Метод замены заявки. Метод находит заявку по уникальному ключу id в массиве items и заменяет ее на новую.
     * @param id   уникальный ключ заявки, генерируемый в методе generatedID
     * @param item входящая заявка которую нужно вставить вместо предыдущей
     * @return метод возвращает true если удалось заменить заявку
     */

    public boolean replace(String id, Item item) {
        boolean result = false;
        for (int i = 0; i < position; i++) {
            if (items.get(i).getId().equals(id)) {
                item.setId(id);
                items.set(i, item);
                result = true;
                break;
            }
        }
        return result;
    }
    /**
     * Метод удаления заявки. Метод находит ячейку в массиве по id. Далее смещаем все значения справа от удаляемого
     * элемента - на одну ячейку влево с помощью System.arrayCopy(), удаляя таким образом найденную ячейку.
     * @param id ячейки (заявки) которую нужно удалить.
     * @return метод возвращет true если, оперция по удалению завершилась успешно.
     */

    public boolean delete(String id) {
        boolean result = false;
        for (int i = 0; i < position; i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                this.position--;
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод находит все заявки в массиве, после чего обрезает ячейки со значением null, оставляя таким образом
     * в массиве только заявки/
     *
     * @return возвращет массив без значений null.
     */

    public List<Item> findAll() {
        return items;
    }


    /**
     * Метод сравнивает входящий аргумент key и имя (name) заявки в массиве items и если они равны, то копирует,
     * совпадающую заявку во вновь созданный массив.
     *
     * @param key имя заявки которую нужно найти в массиве items и скопировать в результирующий массив, найденную
     *            заявку.
     * @return метод возвращает список с найденными по имени key заявками
     */

    public List<Item> findByName(String key) {
        List<Item> resultitems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().contains(key)) {
                resultitems.add(item);
            }
        }
        return resultitems;
    }

    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item != null && item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }

    String generatedId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    public List<Item> getAll() {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            result.add(item);
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}
