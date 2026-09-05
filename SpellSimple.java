
public class SpellSimple {//An object of the class (magic) contains two fields: magic name and words
    private String name;
    private String words;

    SpellSimple(String name, String words) {
        this.name = name;
        this.words = words;
    }

    public String getName(){//Method that returns the name of the magic
        return name;
    }//Returns the name of the spell.

    public String getWords(){//A method that returns the words of a magic
        return words;
    }//Returns the spell words
}