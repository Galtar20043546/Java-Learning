package q8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayListGenericsTest {

    /**
     * Тест проверяет начальное состояние только что созданного списка.
     * Ожидается, что size() равен 0, а capacity() равна значению,
     * переданному в конструктор, то есть новый список должен быть
     * пустым, но иметь зарезервированную ёмкость.
     */
    @Test
    void testInitialSizeAndCapacity() {
        ArrayListGenerics<Integer> list = new ArrayListGenerics<>(5);

        assertEquals(0, list.size());
        assertEquals(5, list.getCapacity());
    }

    /**
     * Тест проверяет, что метод add() корректно увеличивает size()
     * и сохраняет элементы в правильном порядке (по индексам добавления).
     * Ёмкости для добавляемых элементов должно хватать, поэтому
     * resize здесь не проверяется отдельно.
     */
    @Test
    void testAddIncreasesSizeAndStoresElements() {
        ArrayListGenerics<Integer> list = new ArrayListGenerics<>(5);

        list.add(10);
        list.add(20);

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    /**
     * Тест проверяет автоматическое расширение массива, когда
     * количество элементов достигает текущей capacity. При добавлении
     * элемента сверх ёмкости (capacity == size) capacity должна
     * удвоиться, а все ранее добавленные элементы — сохраниться
     * в исходном порядке.
     */
    @Test
    void testAddResizesCapacityWhenFull() {
        ArrayListGenerics<Integer> list = new ArrayListGenerics<>(2);
        list.add(1);
        list.add(2);

        assertEquals(2, list.getCapacity());

        list.add(3); // должен вызвать увеличение capacity

        assertEquals(4, list.getCapacity());
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    /**
     * Тест проверяет, что класс действительно является обобщённым (generic)
     * и корректно работает с типом, отличным от Integer — в данном случае
     * со String. Это подтверждает, что параметр типа T используется
     * без потери данных и без ClassCastException при add()/get().
     */
    @Test
    void testGenericTypeSupportsNonIntegerType() {
        ArrayListGenerics<String> list = new ArrayListGenerics<>(3);

        list.add("hello");
        list.add("world");

        assertEquals("hello", list.get(0));
        assertEquals("world", list.get(1));
    }

    /**
     * Тест проверяет метод remove(): он должен вернуть удаляемый элемент,
     * уменьшить size() на единицу и сдвинуть все элементы после
     * удалённого индекса на одну позицию влево, сохраняя их порядок.
     */
    @Test
    void testRemoveReturnsElementAndShiftsRemaining() {
        ArrayListGenerics<Integer> list = new ArrayListGenerics<>(5);
        list.add(100);
        list.add(200);
        list.add(300);

        Integer removed = list.remove(0);

        assertEquals(100, removed);
        assertEquals(2, list.size());
        assertEquals(200, list.get(0));
        assertEquals(300, list.get(1));
    }

    /**
     * Тест проверяет автоматическое уменьшение ёмкости при удалении
     * элементов. Согласно реализации remove(), если capacity/4 > size
     * после удаления, capacity должна уменьшиться вдвое. Тест доводит
     * список до capacity=8, затем последовательно удаляет элементы,
     * пока size не станет достаточно малым, чтобы вызвать сжатие
     * capacity до 4.
     */
    @Test
    void testRemoveShrinksCapacityWhenUsageIsLow() {
        ArrayListGenerics<Integer> list = new ArrayListGenerics<>(2);
        list.add(1); // capacity 2, size 1
        list.add(2); // capacity 2, size 2
        list.add(3); // capacity 4, size 3
        list.add(4); // capacity 4, size 4
        list.add(5); // capacity 8, size 5

        assertEquals(8, list.getCapacity());

        list.remove(0); // size 4, 8/4=2 > 4 ? нет
        list.remove(0); // size 3, 2 > 3 ? нет
        list.remove(0); // size 2, 2 > 2 ? нет
        assertEquals(8, list.getCapacity());

        list.remove(0); // size 1, 2 > 1 ? да -> сжатие до 4

        assertEquals(4, list.getCapacity());
        assertEquals(1, list.size());
    }

    /**
     * Тест проверяет метод trimToSize(): после его вызова capacity
     * должна стать равной переданному значению, а все элементы
     * в пределах этого размера — сохраниться без изменений и
     * оставаться доступными через get().
     */
    @Test
    void testTrimToSizeReducesCapacity() {
        ArrayListGenerics<Integer> list = new ArrayListGenerics<>(10);
        list.add(7);
        list.add(8);
        list.add(9);

        list.trimToSize(3);

        assertEquals(3, list.getCapacity());
        assertEquals(7, list.get(0));
        assertEquals(8, list.get(1));
        assertEquals(9, list.get(2));
    }

    /**
     * Тест проверяет граничный случай: удаление единственного элемента
     * из списка. После remove(0) метод должен вернуть удалённый элемент,
     * а size() должен стать равным 0, то есть список должен корректно
     * обрабатывать переход в пустое состояние.
     */
    @Test
    void testRemoveLastRemainingElement() {
        ArrayListGenerics<Integer> list = new ArrayListGenerics<>(4);
        list.add(42);

        Integer removed = list.remove(0);

        assertEquals(42, removed);
        assertEquals(0, list.size());
    }
}