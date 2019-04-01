package dundry.app;

import dundry.greeter.Greeter;
import dundry.index_gen.IndexGen;

public class DundryApp {

    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        IndexGen gen = new IndexGen(1);
        
        int langMin = 0; // default
        int langMax = 1; // default
        if (args.length==1) {
            String langString = args[0];
            if (langString.equals("*")) {
                /* 
                 * Show all languages
                 */
                langMax = greeter.getNumOfLang();
            } else if (langString.equals("r")) {
                /*
                 * Show one a random language
                 */
                langMin = langMax = gen.gen();
            } else {
                /*
                 * Show only one language as specified
                 */
                try {
                    langMin = Integer.parseInt(langString);
                    langMax = langMin + 1; // exclusive boundary
                } catch (NumberFormatException ex) {
                    String msg = String.format("language can only be one of these: "
                            + "'*', 'r', or %d-%d", 0, greeter.getNumOfLang()-1);
                    throw new IllegalArgumentException(msg);
                }
            }
        }
        
        for (int i=langMin; i<langMax; i++) {            
            System.out.println(greeter.greet(i));
        }

    }

}
