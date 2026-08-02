package q8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Тесты, ориентированные именно на проверку корректной работы generics
 * в LinkedListGenerics<T>: разные типы параметризации, отсутствие
 * необходимости явного приведения типов, независимость разных
 * типизированных экземпляров, поддержка null и вложенных generics.
 */
class LinkedListGenericsTest {

    // ===================== Тесты с типом Integer =====================

    @Test
    void testAddAndGetWithInteger() {
        LinkedListGenerics<Integer> list = new LinkedListGenerics<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(3, list.size());

        // get() возвращает Integer без явного приведения типа —
        // если бы генерики не работали, эта строка не скомпилировалась бы
        int first = list.get(0);
        int second = list.get(1);
        int third = list.get(2);

        assertEquals(1, first);
        assertEquals(2, second);
        assertEquals(3, third);
    }

    @Test
    void testRemoveWithInteger() {
        LinkedListGenerics<Integer> list = new LinkedListGenerics<>();
        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1); // без каста (Integer)
        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, (int) list.get(0));
        assertEquals(30, (int) list.get(1));
    }

    // ===================== Тесты с типом String =====================

    @Test
    void testAddAndGetWithString() {
        LinkedListGenerics<String> list = new LinkedListGenerics<>();
        list.add("Hello");
        list.add("World");

        assertEquals(2, list.size());

        String first = list.get(0);
        String second = list.get(1);

        assertEquals("Hello", first);
        assertEquals("World", second);
    }

    @Test
    void testStringMethodsAvailableWithoutCast() {
        LinkedListGenerics<String> list = new LinkedListGenerics<>();
        list.add("Java");

        // Если бы get() возвращал Object, вызов .length() без каста
        // привёл бы к ошибке компиляции
        String value = list.get(0);
        assertEquals(4, value.length());
        assertTrue(value.startsWith("Ja"));
    }

    // ============== Тесты с пользовательским (кастомным) классом ==============

    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    void testAddAndGetWithCustomObject() {
        LinkedListGenerics<Person> list = new LinkedListGenerics<>();
        Person p1 = new Person("Alice", 25);
        Person p2 = new Person("Bob", 30);

        list.add(p1);
        list.add(p2);

        // Доступ к полю .name напрямую, без приведения к Person
        Person result = list.get(0);
        assertEquals("Alice", result.name);
        assertEquals(25, result.age);
        assertSame(p1, result); // тот же самый объект, не копия
    }

    @Test
    void testRemoveWithCustomObject() {
        LinkedListGenerics<Person> list = new LinkedListGenerics<>();
        Person p1 = new Person("Alice", 25);
        Person p2 = new Person("Bob", 30);
        list.add(p1);
        list.add(p2);

        Person removed = list.remove(0);
        assertSame(p1, removed);
        assertEquals(1, list.size());
        assertSame(p2, list.get(0));
    }

    // ============ Тест независимости разных типизированных экземпляров ============

    @Test
    void testIndependenceOfDifferentGenericInstances() {
        LinkedListGenerics<String> stringList = new LinkedListGenerics<>();
        LinkedListGenerics<Integer> intList = new LinkedListGenerics<>();

        stringList.add("test");
        intList.add(100);

        assertEquals(1, stringList.size());
        assertEquals(1, intList.size());
        assertEquals("test", stringList.get(0));
        assertEquals(100, (int) intList.get(0));
    }

    // ===================== Тесты работы с null-элементами =====================

    @Test
    void testAddNullElement() {
        LinkedListGenerics<String> list = new LinkedListGenerics<>();
        list.add(null);
        list.add("notNull");

        assertEquals(2, list.size());
        assertNull(list.get(0));
        assertEquals("notNull", list.get(1));
    }

    @Test
    void testRemoveNullElement() {
        LinkedListGenerics<String> list = new LinkedListGenerics<>();
        list.add(null);
        list.add("a");

        String removed = list.remove(0);
        assertNull(removed);
        assertEquals(1, list.size());
        assertEquals("a", list.get(0));
    }

    // ============ Тест целостности структуры при разных типах данных ============

    @Test
    void testMultipleRemovalsMaintainCorrectOrderWithDouble() {
        LinkedListGenerics<Double> list = new LinkedListGenerics<>();
        list.add(1.1);
        list.add(2.2);
        list.add(3.3);
        list.add(4.4);

        list.remove(1); // удаляем 2.2

        assertEquals(3, list.size());
        assertEquals(1.1, list.get(0));
        assertEquals(3.3, list.get(1));
        assertEquals(4.4, list.get(2));
    }

    @Test
    void testGenericListWorksWithBooleanType() {
        LinkedListGenerics<Boolean> list = new LinkedListGenerics<>();
        list.add(true);
        list.add(false);

        assertTrue(list.get(0));
        assertFalse(list.get(1));
    }
}