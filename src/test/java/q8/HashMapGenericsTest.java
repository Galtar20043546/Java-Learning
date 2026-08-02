package q8;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Юнит-тесты для {@link HashMapGenerics}.
 * Проверяют базовую функциональность, обработку граничных случаев
 * и работу с разными типами данных (не только String/Integer/Double).
 */
class HashMapGenericsTypeTest {

    // ---------- базовые комбинации типов ----------

    /**
     * Проверяет базовый случай: K = String, V = Integer.
     * Важно, что переменной Integer value присваивается результат map.get(...)
     * БЕЗ явного каста — если бы дженерики в классе были сломаны и get()
     * реально возвращал Object, этот код не скомпилировался бы. Сам факт
     * компиляции — уже часть проверки.
     */
    @Test
    void stringKeyIntegerValue() {
        HashMapGenerics<String, Integer> map = new HashMapGenerics<>();
        map.put("one", 1);
        map.put("two", 2);

        Integer value = map.get("one");
        assertEquals(1, value);
    }

    /**
     * Проверяет обратную комбинацию: K = Integer, V = String.
     * Убеждаемся, что класс не "заточен" под конкретный порядок типов
     * (например, под String-ключи), а параметризация действительно работает
     * в любую сторону.
     */
    @Test
    void integerKeyStringValue() {
        HashMapGenerics<Integer, String> map = new HashMapGenerics<>();
        map.put(1, "one");
        map.put(2, "two");

        String value = map.get(2);
        assertEquals("two", value);
    }

    /**
     * Проверяет типы-обёртки, отличные от String/Integer: Long и Double.
     * Это исключает вероятность, что реализация где-то в коде неявно
     * рассчитывает именно на String.hashCode() или Integer.hashCode().
     */
    @Test
    void longKeyDoubleValue() {
        HashMapGenerics<Long, Double> map = new HashMapGenerics<>();
        map.put(100L, 3.14);

        Double value = map.get(100L);
        assertEquals(3.14, value);
    }

    /**
     * Проверяет Boolean в качестве ключа — тип с всего двумя возможными
     * значениями. Хороший граничный случай: если в реализации где-то есть
     * логика, завязанная на "количество различных hashCode()", такой тип
     * её быстро выявит.
     */
    @Test
    void booleanKeyStringValue() {
        HashMapGenerics<Boolean, String> map = new HashMapGenerics<>();
        map.put(true, "yes");
        map.put(false, "no");

        assertEquals("yes", map.get(true));
        assertEquals("no", map.get(false));
    }

    /**
     * Проверяет enum в качестве ключа. У enum есть собственная реализация
     * hashCode() (основанная на identity), и тест подтверждает, что
     * HashMapGenerics корректно работает и с такими "непростыми" типами,
     * не только с String/числами.
     */
    @Test
    void enumKeySupported() {
        HashMapGenerics<Day, Integer> map = new HashMapGenerics<>();
        map.put(Day.MONDAY, 1);
        map.put(Day.FRIDAY, 5);

        assertEquals(1, map.get(Day.MONDAY));
        assertEquals(5, map.get(Day.FRIDAY));
    }

    private enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY }

    // ---------- несколько независимых экземпляров с разными типами ----------

    /**
     * Ключевой тест на "чистоту" дженериков во время выполнения.
     * Из-за type erasure оба экземпляра (HashMapGenerics<String,Integer>
     * и HashMapGenerics<Integer,String>) во время выполнения — это один
     * и тот же raw-класс. Тест проверяет, что состояние (массив buckets,
     * счётчик size) у каждого экземпляра своё, и операции над одним
     * объектом никак не затрагивают другой.
     */
    @Test
    void differentInstancesWithDifferentTypesDoNotInterfere() {
        HashMapGenerics<String, Integer> stringToInt = new HashMapGenerics<>();
        HashMapGenerics<Integer, String> intToString = new HashMapGenerics<>();

        stringToInt.put("key", 123);
        intToString.put(123, "key");

        assertEquals(123, stringToInt.get("key"));
        assertEquals("key", intToString.get(123));

        stringToInt.remove("key");
        assertNull(stringToInt.get("key"));
        assertEquals("key", intToString.get(123));
    }

    // ---------- generic-значения (вложенные параметризованные типы) ----------

    /**
     * Проверяет параметризованный тип в качестве значения: V = List<Integer>.
     * Убеждаемся, что из map.get(...) возвращается именно тот же объект
     * списка (изменения в нём видны потом через повторный get), а не
     * какая-то некорректно приведённая копия.
     */
    @Test
    void listAsValueType() {
        HashMapGenerics<String, List<Integer>> map = new HashMapGenerics<>();
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3));
        map.put("numbers", numbers);

        List<Integer> retrieved = map.get("numbers");
        assertEquals(List.of(1, 2, 3), retrieved);

        retrieved.add(4);
        assertEquals(4, map.get("numbers").size());
    }

    /**
     * Проверяет вложенность самого HashMapGenerics: V = HashMapGenerics<String,Integer>.
     * Это стресс-тест на параметризацию — если бы где-то в реализации
     * использовался "сырой" (raw) тип вместо параметризованного, вложенная
     * структура могла бы сломаться именно на таком сценарии.
     */
    @Test
    void mapAsValueType_nestedGenericStructure() {
        HashMapGenerics<String, HashMapGenerics<String, Integer>> outer = new HashMapGenerics<>();
        HashMapGenerics<String, Integer> inner = new HashMapGenerics<>();
        inner.put("age", 30);

        outer.put("person", inner);

        Integer age = outer.get("person").get("age");
        assertEquals(30, age);
    }

    // ---------- корректность работы с ключами-объектами (equals/hashCode) ----------

    /**
     * Вспомогательный класс с ПРАВИЛЬНО переопределёнными equals()/hashCode().
     * Два разных объекта с одинаковыми полями (x, y) должны считаться
     * одним и тем же ключом — так обязан вести себя любой generic-ключ
     * в хэш-таблице, работающей по контракту equals()/hashCode().
     */
    private static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    /**
     * Проверяет, что два РАЗНЫХ объекта Point с одинаковыми x и y считаются
     * одним ключом при поиске через get(). Это главный тест на то, что
     * put()/get() внутри используют .equals(), а не сравнение ссылок (==).
     */
    @Test
    void customKeyWithProperEqualsAndHashCode_treatedAsSameKey() {
        HashMapGenerics<Point, String> map = new HashMapGenerics<>();
        map.put(new Point(1, 2), "first");

        String value = map.get(new Point(1, 2));

        assertEquals("first", value);
        assertEquals(1, map.size());
    }

    /**
     * Проверяет, что put() с "равным" (но другим по ссылке) ключом
     * перезаписывает существующее значение, а не создаёт новую отдельную
     * запись. Дополняет предыдущий тест: там проверялся get(), здесь — put().
     */
    @Test
    void customKeyWithProperEquals_overwritesOnEqualKey() {
        HashMapGenerics<Point, String> map = new HashMapGenerics<>();
        map.put(new Point(1, 2), "first");
        map.put(new Point(1, 2), "second");

        assertEquals("second", map.get(new Point(1, 2)));
        assertEquals(1, map.size());
    }

    /**
     * Вспомогательный класс БЕЗ переопределённых equals()/hashCode()
     * (используется дефолтная реализация из Object, основанная на ссылке).
     * Два объекта с одинаковыми полями, но без переопределения, ДОЛЖНЫ
     * считаться разными ключами — это ожидаемое поведение Java, а не баг.
     */
    private static class RawKey {
        final int id;

        RawKey(int id) {
            this.id = id;
        }
    }

    /**
     * Проверяет, что при отсутствии переопределённых equals()/hashCode()
     * два разных объекта с одинаковыми данными (id) действительно
     * трактуются как два разных ключа: оба сохраняются, size() = 2,
     * и по каждому объекту возвращается именно его собственное значение.
     */
    @Test
    void customKeyWithoutEquals_differentInstancesAreDifferentKeys() {
        HashMapGenerics<RawKey, String> map = new HashMapGenerics<>();
        RawKey key1 = new RawKey(1);
        RawKey key2 = new RawKey(1);

        map.put(key1, "value1");
        map.put(key2, "value2");

        assertEquals(2, map.size());
        assertEquals("value1", map.get(key1));
        assertEquals("value2", map.get(key2));
    }

    /**
     * Контрольный тест к предыдущему: если использовать ОДНУ И ТУ ЖЕ
     * ссылку на объект дважды, значение должно перезаписаться (size = 1).
     * Вместе с предыдущим тестом это подтверждает, что поведение зависит
     * именно от identity объекта, когда equals()/hashCode() не переопределены.
     */
    @Test
    void customKeyWithoutEquals_sameReferenceIsSameKey() {
        HashMapGenerics<RawKey, String> map = new HashMapGenerics<>();
        RawKey key = new RawKey(1);

        map.put(key, "value1");
        map.put(key, "value2");

        assertEquals(1, map.size());
        assertEquals("value2", map.get(key));
    }

    // ---------- согласованность null для разных generic-параметров ----------

    /**
     * Проверяет, что null допустим в качестве значения (V) независимо
     * от того, какой конкретно тип подставлен вместо V — простой Integer
     * или параметризованный List<Integer>.
     */
    @Test
    void nullValueAllowedForAnyValueType() {
        HashMapGenerics<String, Integer> intMap = new HashMapGenerics<>();
        intMap.put("key", null);
        assertNull(intMap.get("key"));

        HashMapGenerics<String, List<Integer>> listMap = new HashMapGenerics<>();
        listMap.put("key", null);
        assertNull(listMap.get("key"));
    }

    /**
     * Проверяет, что null-ключ выбрасывает IllegalArgumentException
     * одинаково, независимо от того, какой тип подставлен вместо K
     * (String или Integer). Показывает, что проверка на null в put()
     * не завязана случайно на конкретный тип ключа.
     */
    @Test
    void nullKeyThrowsRegardlessOfKeyType() {
        HashMapGenerics<String, Integer> stringKeyMap = new HashMapGenerics<>();
        assertThrows(IllegalArgumentException.class, () -> stringKeyMap.put(null, 1));

        HashMapGenerics<Integer, String> intKeyMap = new HashMapGenerics<>();
        assertThrows(IllegalArgumentException.class, () -> intKeyMap.put(null, "value"));
    }

    // ---------- отсутствие ClassCastException при типовой работе ----------

    /**
     * Проверяет "жизненный" сценарий с многократными put()/get() и
     * неявной распаковкой Integer -> int (auto-unboxing). Если бы где-то
     * в реализации типы были перепутаны (например, хэш считался не от
     * того объекта), здесь либо упал бы assertEquals, либо возникло бы
     * ClassCastException при распаковке.
     */
    @Test
    void noCastExceptionOnNormalUsageAcrossTypes() {
        HashMapGenerics<String, Integer> map = new HashMapGenerics<>();
        for (int i = 0; i < 20; i++) {
            map.put("k" + i, i * i);
        }

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 20; i++) {
                int value = map.get("k" + i);
                assertEquals(i * i, value);
            }
        });
    }
}