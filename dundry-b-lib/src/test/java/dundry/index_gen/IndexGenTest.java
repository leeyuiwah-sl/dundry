package dundry.index_gen;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IndexGenTest {

    @Test
    public void gen_should_return_int_in_range() {
        final int SIZE = 10;
        IndexGen gen = new IndexGen(SIZE);
        int index = gen.gen();
        assertTrue(index>=0 && index<SIZE);
    }

}
