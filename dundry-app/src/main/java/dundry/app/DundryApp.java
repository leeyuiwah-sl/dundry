package dundry.app;

import dundry.index_gen.IndexGen;

public class DundryApp {

    public static void main(String[] args) {
        IndexGen gen = new IndexGen(1);

        System.out.println("gen: " + gen.gen());
    }

}
