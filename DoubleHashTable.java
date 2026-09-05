
public class DoubleHashTable {//A data structure that stores objects of type SpellSimple. The class contains the data structure, the size of the filled cells, capacity, and the number of steps taken to reach the desired cell/until we found an empty cell.
    private SpellSimple[] table;
    private int capacity;
    private int size;
    private int steps=0;

    public DoubleHashTable(int capacity) {
        this.table = new SpellSimple[capacity];//We will initialize the data structure as an empty array, with 0 filled cells.
        this.size = 0;
        this.capacity = capacity;
    }

    public boolean put(SpellSimple spell) {//A method that aims to allow adding a new magic to the data structure
        steps = 0;//Initially the number of steps taken is 0.
        for (int i=0; i<capacity; i++) {//We will loop from index 0 to the size of the table. Each time we will define an index variable that is equal to the index obtained from running the hash functions with the current i.
            int index = (hash1(spell.getName())+i*hash2(spell.getName()))%capacity;
            if (table[index] == null) {//If we find a free space (the value found there is equal to null), we will update the table at the current index to be equal to the object we want to add, increase the number of filled cells by 1 and update the number of steps according to the current i.
                table[index] = new SpellSimple(spell.getName(),spell.getWords());
                steps = i;
                size += 1;
                return true;//If we found a free space we will return true
            }
        }return false; //If we have finished going through the entire table and have not found any free space, we will return false.
    }

    public String getCastWords(String name) {//A method that aims to search for the desired magic, and if it exists in the table, return the words.
        steps = 0;
        for (int i=0 ; i<capacity; i++) {//A loop that runs from 0 to the size of the table, each time checking whether the index obtained from running the hash functions contains the magic we are looking for.
            int index = (hash1(name)+i*hash2(name))%capacity;
            if (table[index] == null) {//If we reach an empty position, this means that the magic will no longer appear in the table (because during the insertion operation, the magic will enter the first cell that the hash function suggests and is empty, so there cannot be a suitable cell that remains empty).
                steps = i;//We will update the number of steps taken until we realized that the requested magic does not exist in the data structure (it is actually i)
                return null;//We will return null because the magic was not found.
            }
            if (table[index].getName().equals(name)) {//If we found the magic, we will update the number of steps to be the current i and return the words of the magic.
                steps = i;
                return table[index].getWords();
            }
        }
        steps = capacity; //If we have finished going through the entire table and have not encountered null or the magic we are looking for, it means that the magic is not in the table (everywhere it could have been, there was another magic). We will update the steps to be the size of the table and return null
        return null;
    }

    public int getSize() {//Returns the number of full cells
        return size;
    }

    public int getLastSteps() {//Returns the number of steps we took in the last action (getCastWords or put)
        return steps;
    }

    private int hash1(String name) {//The first hash function
        int hash = 0;
        for (int index = 0; index<name.length();index ++) {
            hash += name.charAt(index) * 31;
        }
        return hash%capacity;
    }

    private int hash2(String name) {//The second hash function
        int hash2 = 0;
        for (int index = 0; index<name.length();index ++) {
            hash2 += name.charAt(index) * 13;
        }
        return (1+ hash2%(capacity-2));
    }
}