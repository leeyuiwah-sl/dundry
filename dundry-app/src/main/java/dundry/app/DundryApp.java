package dundry.app;

import java.util.List;

import dundry.data_manager.DataManager;
import dundry.greeter.Greeter;
import dundry.index_gen.IndexGen;
import dundry.model.Item;

public class DundryApp {

    public static void main(String[] args) {
        redirectStdErr();
        
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
        
        List<Item> items = DataManager.getItems();
        String msg = String.format("Got %d items from the DB%n", items.size());
        System.out.print(msg);
        for ( Item r :  items) {
            msg = String.format("%10d %10d %10.2f %s%n", r.getTest_table_id(),
                    r.getTest_int(), r.getTest_float(), r.getTest_string());
            System.out.print(msg);
        }
        msg = String.format("Total number of items: %d%n", items.size());
        System.out.print(msg);

    }

    final static boolean debug = true;
    private static void redirectStdErr() {
        if (!debug) {
            System.err.close();
        }
        
    }

}
