package dundry.greeter;

public class Greeter {

    static final String[] greetings = {
            "Hello, World!",
            "Bonjour, Le Monde!"};
    
    public String greet(int lang) {
        if (lang>=greetings.length) {
            final String msg = "No such language: " + lang;
            throw new IllegalArgumentException(msg);
        }
        return greetings[lang];
    }
    
    public int getNumOfLang() {
        return greetings.length;
    }
}
