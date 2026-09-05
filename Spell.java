
public class Spell {//A class object contains the following fields: the spell name, the category it belongs to, the spell words, and the spell's power level.
    private String name;
    private String category;
    private int powerLevel;
    private String words;

    public Spell(String name, String category, int powerLevel, String words) {//The class constructor
        this.name = name;
        this.category = category;
        this.powerLevel = powerLevel;
        this.words = words;
    }

    public String getName() {//A method that returns the name of the spell.
        return name;
    }//Returns the name of the spell.

    public String getCategory() {//A method that returns the magic category
        return category;
    }//Returns the spell category

    public int getPowerLevel() {//A method that restores the power of magic melody
        return powerLevel;
    }//Restores the power of the spell

    @Override
    public String toString() {
        return name + " (" + category + ") - Power Level: " + powerLevel + ", to cast say: " + words;
    }
}
