package q8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashMapGenericsTest {

    private HashMapGenerics<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMapGenerics<>();
    }

    @Test
    @DisplayName("get на пустой карте возвращает null")
    void get_emptyMap_returnsNull() {
        assertNull(map.get("key"));
    }

    @Test
    @DisplayName("put и get одного элемента возвращают верное значение")
    void putAndGet_singleElement_returnsValue() {
        map.put("one", 1);
        assertEquals(1, map.get("one"));
    }

    @Test
    @DisplayName("get с несуществующим ключом возвращает null")
    void get_nonExistentKey_returnsNull() {
        map.put("one", 1);
        assertNull(map.get("two"));
    }

    @Test
    @DisplayName("повторный put с тем же ключом обновляет значение, а не создаёт новую запись")
    void put_sameKeyTwice_updatesValueNotDuplicate() {
        map.put("key", 1);
        map.put("key", 2);

        assertEquals(2, map.get("key"));
        assertEquals("key=2", map.toString());
    }

    @Test
    @DisplayName("несколько разных ключей можно добавить и получить обратно")
    void put_multipleKeys_allRetrievable() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));
        assertEquals(3, map.get("c"));
    }

    @Test
    @DisplayName("toString на пустой карте возвращает пустую строку")
    void toString_emptyMap_returnsEmptyString() {
        assertEquals("", map.toString());
    }

    @Test
    @DisplayName("toString для одного элемента возвращает формат key=value")
    void toString_singleElement_returnsKeyValueFormat() {
        map.put("x", 10);
        assertEquals("x=10", map.toString());
    }

    @Test
    @DisplayName("toString для нескольких элементов выводит их через запятую в порядке, обратном добавлению")
    void toString_multipleElements_returnsCommaSeparatedInReverseOrder() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        // Новые узлы вставляются в начало связного списка (buckets[0]),
        // поэтому порядок в toString обратный порядку добавления
        assertEquals("c=3, b=2, a=1", map.toString());
    }

    @Test
    @DisplayName("put с null в качестве значения сохраняет null, и его можно получить обратно")
    void put_nullValue_storesAndReturnsNull() {
        map.put("key", null);
        assertNull(map.get("key"));
        assertEquals("key=null", map.toString());
    }

    @Test
    @DisplayName("большое количество элементов в одном bucket корректно добавляется и читается")
    void put_manyElements_allRetrievable() {
        for (int i = 0; i < 100; i++) {
            map.put("key" + i, i);
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(i, map.get("key" + i));
        }
    }

    @Test
    @DisplayName("get с null ключом на пустой карте возвращает null (исключения нет)")
    void get_nullKeyOnEmptyMap_returnsNull() {
        assertNull(map.get(null));
    }

    @Test
    @DisplayName("get с null ключом после добавления null ключа выбрасывает NullPointerException (баг реализации)")
    void get_nullKeyAfterPuttingNullKey_throwsNullPointerException() {
        map.put(null, 1);
        assertThrows(NullPointerException.class, () -> map.get(null));
    }

    @Test
    @DisplayName("повторный put с null ключом выбрасывает NullPointerException (баг реализации)")
    void put_duplicateNullKey_throwsNullPointerException() {
        map.put(null, 1);
        assertThrows(NullPointerException.class, () -> map.put(null, 2));
    }

    @Test
    @DisplayName("HashMapGenerics корректно работает с другими типами ключей/значений (проверка генеричности)")
    void genericTypes_intKeyStringValue_worksCorrectly() {
        HashMapGenerics<Integer, String> intMap = new HashMapGenerics<>();
        intMap.put(1, "one");
        intMap.put(2, "two");

        assertEquals("one", intMap.get(1));
        assertEquals("two", intMap.get(2));
    }
}