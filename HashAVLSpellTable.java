
import java.util.LinkedList;
import java.util.List;

public class HashAVLSpellTable {
    private LinkedList<AVLTree> buckets[];
    private int tableSize;
    private int numSpells;


    public HashAVLSpellTable(int size) {
        this.buckets = new LinkedList[size];//Create an array of size "size" where each element is a linked list
        this.tableSize = size;
        this.numSpells = 0;// Initially all my linked lists are empty so I have no spells at all

        for (int i=0 ; i<size ; i++) {
            this.buckets[i] = new LinkedList<AVLTree>();//In each bucket in the array we will create a linked list that will contain the AVL trees.
        }
    }

    private int hash(String category) {//A function that is responsible for calculating the appropriate position in the array for each category so that when we look for a spell from a particular category we will know which cell to access. The position is calculated based on the numerical value of the spell name
        int hashnum = category.hashCode();
        if (hashnum<0) {
            hashnum = hashnum*(-1);
        }
        return  hashnum % tableSize;
    }

    public void addSpell(Spell s) {
        int index_to_insert = hash(s.getCategory());
        LinkedList<AVLTree> current_list = buckets[index_to_insert];
        if  (!current_list.isEmpty()) {
            for (AVLTree tree : current_list) {//We will go through all the existing trees in the linked list located at the index to which the hash function led us.
                if (tree.getCategory().equals(s.getCategory())) {//This condition is important in case our hash function matched the same location to two different categories.
                    tree.insert(s);//We will insert the new spell into the appropriate tree.
                    numSpells += 1;//After adding, we will increase the number of spells by 1.
                    return;//Once we find it, we can stop searching.
                }
            }
        }
        current_list.add(new AVLTree(s));//If we do not find a tree with a suitable category that already exists, we will create a tree for the category and insert s into it.
        numSpells += 1;
    }

    public Spell searchSpell(String category, String spellName, int powerLevel) {
        int index = hash(category);//Using the hash function, we find the index where the spell should be.
        LinkedList<AVLTree> current_list = buckets[index];//We will get to the linked list where the spell is supposed to be (in the index we mentioned earlier)
        if (!current_list.isEmpty()){//As long as the linked list is empty, we can know for sure that the spell is not found and not enter the loop.
            for (AVLTree tree : current_list) {//We will go through all the trees in the linked list and when we find the tree that matches the category we are looking for, we will use the search function to find the specific spell in the tree.
                if (tree.getCategory().equals(category)){
                    return tree.search(spellName,powerLevel);
                }
            }
        }
        return null;//If the spell is not found, we will return null.
    }

    public int getNumberSpells(){
        return numSpells;
    }//We will return the total number of spells in the data structure

    public int getNumberSpells(String category) {//Similar to what we did in the search function, we will locate the list that is in the appropriate place for the category according to the hash function and if we find it, we will return the size of the category tree.
        int index = hash(category);
        LinkedList<AVLTree> current_list = buckets[index];
        if (!current_list.isEmpty()) {
            for (AVLTree tree : current_list) {
                if (tree.getCategory().equals(category)) {
                    return tree.getSize();
                }
            }
        }
        return 0;//If we did not find the category, it means that the number of spells in this category is equal to 0.
    }
    public List<Spell> getTopK(String category, int k) {//Here too, we will find the location where the tree belonging to the relevant category should be. If the linked list is not empty, we will search for the category's tree, and return the top-k spells using the getTopK function.top-k spells
        int index = hash(category);
        LinkedList<AVLTree> current_list = buckets[index];
        if (!current_list.isEmpty()) {
            for (AVLTree tree : current_list) {
                if (tree.getCategory().equals(category)){
                    return tree.getTopK(k);
                }
            }
        }
        return null;// If the category is not found we will return null.
    }
}
