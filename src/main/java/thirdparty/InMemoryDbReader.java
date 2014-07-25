package thirdparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InMemoryDbReader implements DbReader {

    private static final Map<String, List<String[]>> DATA = new HashMap<String, List<String[]>>();

    @Override
    public void put(String key, String... data) {
        if (!DATA.containsKey(key)) {
            DATA.put(key, new ArrayList<String[]>());
        }
        DATA.get(key).add(data);
    }

    @Override
    public void delete(String key) {
        DATA.remove(key);
    }

    @Override
    public List<String[]> get(String key) {
        List<String[]> result = new ArrayList<String[]>();
        if (DATA.containsKey(key)) {
            for (String[] data : DATA.get(key)) {
                insertAtRandomPosition(result, copyOf(data));
            }
        }
        return result;
    }

    private static void insertAtRandomPosition(List<String[]> list, String[] object) {
        int position = new Random().nextInt(list.size() + 1);
        list.add(position, object);
    }

    private static String[] copyOf(String[] array) {
        String[] copy = new String[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }

}
