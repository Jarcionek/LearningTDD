package thirdparty;

import java.util.List;

/**
 * Instantiate with: new thirdparty.PersistentDbReader()
 */
public interface DbReader {

    /**
     * Adds new entry with the specified key and data. Multiple entries with the same key and data are allowed.
     */
    void put(String key, String... data);

    /**
     * Deletes all entries with the specified key.
     */
    void delete(String key);

    /**
     * Retrieves the data (without the key) of all entries that have the specified key.
     * Retrieved entries are not sorted in any way and multiple calls may return entries in different order.
     * Changes to retrieved data will not be persisted.
     */
    List<String[]> get(String key);

}
