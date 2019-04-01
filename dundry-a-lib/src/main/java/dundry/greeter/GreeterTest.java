package dundry.greeter;

import static org.junit.Assert.*;

import org.junit.Test;

public class GreeterTest {

    @Test
    public void greet_should_return_a_string() {
        Greeter g = new Greeter();
        for (int i=0; i<g.getNumOfLang(); i++) {
            String greeting = g.greet(i);
            assertTrue(greeting!=null && greeting.length()>0);
            
            String msg = String.format("%d: %s", i, greeting);
            System.out.println(msg);
        }
    }

    @Test
    public void getNumOfLang_should_return_2() {
        Greeter g = new Greeter();
        assertTrue(g.getNumOfLang() == 2);
    }

}
