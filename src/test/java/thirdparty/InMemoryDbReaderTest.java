package thirdparty;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InMemoryDbReaderTest {

    @Test
    public void test() {
        InMemoryDbReader reader = new InMemoryDbReader();

        reader.put("one", "a", "b", "c");
        reader.put("one", "x", "y", "z");
        reader.put("one", "1", "2", "3");

        for (String[] array : reader.get("one")) {
            System.out.println(Arrays.toString(array));
        }

        List<String[]> strings = reader.get("one");
        strings.remove(0);
        strings.get(0)[1] = "bah!";

        for (String[] array : reader.get("one")) {
            System.out.println(Arrays.toString(array));
        }
    }

}