package dundry.index_gen;

import java.util.Random;

public class IndexGen {
    private final int size;
    private final Random random = new Random();

    public IndexGen(int size) {
        super();
        if (size<=0) {
            String msg = "size must be a positive integer";
            throw new IllegalArgumentException(msg);
        }
        this.size = size;
    }
    
    public int gen() {
        return random.nextInt(size);
    }

}
