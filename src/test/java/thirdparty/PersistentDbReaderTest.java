package thirdparty;

import org.junit.Test;

public class PersistentDbReaderTest {

    private PersistentDbReader persistentDbReader = new PersistentDbReader();

    @Test
    public void test() {
        System.out.println(persistentDbReader.get("x"));

        System.out.println("putting x->abc");
        persistentDbReader.put("x", "abc");

        System.out.println(persistentDbReader.get("x"));
    }

}
