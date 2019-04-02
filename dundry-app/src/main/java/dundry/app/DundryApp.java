package dundry.app;

import dundry.greeter.Greeter;
import dundry.index_gen.IndexGen;
import dundry.model.TestTable;

public class DundryApp {

    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        IndexGen gen = new IndexGen(greeter.getNumOfLang());
        
        int langMin = 0; // default
        int langMax = 1; // default: Note that we use an exclusive semantics
        boolean parseFailed = false;
        if (args.length==1) {
            String langString = args[0];
            if (langString.equals("*")
                    || langString.equals("a")) {
                /* 
                 * Show all languages
                 */
                langMax = greeter.getNumOfLang();
            } else if (langString.equals("r")) {
                /*
                 * Show one a random language
                 */
                langMin = gen.gen();
                langMax = langMin + 1;      // exclusive of this boundary
            } else {
                /*
                 * Show only one language as specified
                 */
                try {
                    langMin = Integer.parseInt(langString);
                    langMax = langMin + 1; // exclusive of this boundary
                } catch (NumberFormatException ex) {
                    parseFailed = true;
                }
            }
            
            if (parseFailed || langMax>greeter.getNumOfLang()) {
                String msg = String.format("Language can only be one of these: "
                        + "'*', 'r', or %d-%d, but you supplied %s", 
                        0, greeter.getNumOfLang()-1, langString);
                System.err.println(msg);
                return;                
            }
        }
        
        for (int i=langMin; i<langMax; i++) {            
            System.out.println(greeter.greet(i));
        }
        
        TestTable t;

    }

}
