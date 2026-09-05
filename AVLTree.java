
import java.util.ArrayList;
import java.util.List;

public class AVLTree {

    private Node root;
    private int size;
    private String category;
	
	// private Node class for the AVL Tree nodes
    private class Node { 
        private Spell spell;
        private Node left;
        private Node right;
        private int height;

        private Node(Spell spell) {
            this.spell = spell;
            this.left = null;
            this.right = null;
            this.height = 0;
        }
        private Spell getSpell() {
            return spell;
        }
    }

    // Constructor, getters, setters
    public AVLTree(Spell spell) {//Initializes the tree with a root equal to the given Spell and size 1 (currently contains only the root)
        this.root = new Node(spell);
        this.category = spell.getCategory();
        this.size = 1;
    }

    public int getTreeHeight(){//Returns the height of the tree, which is actually the height of the root.
        return this.root.height;
    }

    public int getSize(){//Returns the number of vertices in the tree.
        return size;
    }

    public String getCategory() {//A method that returns the tree category
        return category;
    }

    public Spell search(String spellName, int powerLevel) {
        return help_search_method(this.root,spellName,powerLevel);
    }

    public void insert(Spell spell) {
        //We will place the helper function that is responsible for inserting a new vertex and performing the appropriate balances for the root of our new tree.
        root = find_place_to_insertion(root,spell);
    }

    public List<Spell> getTopK(int k) {
        List<Spell> list_to_return = new ArrayList<>();//Create a new array that will contain our k vertices.
        getTopK_helper(root,k,list_to_return);//We call the helper function with the root from which we will start the transition.
        return list_to_return;//Finally we return the array
    }

    private Spell help_search_method(Node start_node,String spellName, int powerLevel) {
        if (start_node == null) {//If the tree is empty or we reached the end of the tree and didn't find the spell we were looking for we return null
            return null;
        } else if (start_node.getSpell().getName().equals(spellName)&& powerLevel == start_node.getSpell().getPowerLevel() ) {//If we reach a vertex where the name of the spell matches the name and the powerLevel we are looking for, we will return the spell at the vertex.
            return start_node.getSpell();
        } else if (powerLevel<start_node.getSpell().getPowerLevel()) {//If the spell strength is less than the spell strength at the vertex we are currently at, we call the function again but with the vertex to the left of the one we were at.
            return help_search_method(start_node.left,spellName,powerLevel);
        }else {//If the spell strength is greater than the spell strength at the vertex we are currently at, we call the function again but with the vertex to the right of the one we were at.
            return help_search_method(start_node.right,spellName,powerLevel);
        }
    }

    private Node find_place_to_insertion(Node node,Spell spell){
        if (node == null) { //If we reach the point where we want to add the node, we will increase the number of vertices in the tree by 1 and create the new node.
            size += 1;
            return new Node(spell);
        } else if (spell.getPowerLevel() < node.getSpell().getPowerLevel()) {//If the spell strength is higher or lower than the strength of the vertex from which the call is made, we will perform a recursive call on the left vertex.
            node.left = find_place_to_insertion(node.left,spell);
        }else {//If the spell strength is higher than the strength of the vertex from which the call is made, we will perform a recursive call on the right vertex.
            node.right = find_place_to_insertion(node.right,spell);

        }
        //If the subtrees exist, we will save their new height (after adding the vertex) and according to the maximum number, we will update the height of the vertex from which we performed the reading (we will add 1 for itself).
        int left_subtree_height = node.left==null ? -1 : node.left.height;
        int right_subtree_height = node.right == null ? -1 : node.right.height;
        node.height = 1 + (left_subtree_height>right_subtree_height ? left_subtree_height : right_subtree_height);

        //If the difference between the height of the right and left subtrees is greater than 1, we will need to balance using the auxiliary functions detailed below.
        int height_difference = left_subtree_height-right_subtree_height;

        //A situation of left-left imbalance because both the left subtree is higher than the right and we inserted on the left side of the left subtree
        if (height_difference>1 && spell.getPowerLevel()<node.left.getSpell().getPowerLevel()) {
            return rightrotate(node);
        }
        //A situation of right-right imbalance because both the right subtree is higher than the left and we inserted on the right side of the right subtree.
        else if (height_difference<-1 && spell.getPowerLevel()>node.right.getSpell().getPowerLevel()) {
            return lefttrotate(node);
        }
        //A situation of left-right imbalance because both the left subtree is higher than the right and we inserted on the right side of the left subtree
        else if (height_difference>1 && spell.getPowerLevel()>node.left.getSpell().getPowerLevel()) {
            node.left = lefttrotate(node.left);
            return rightrotate(node);
        }
        //A situation of right-left imbalance because both the right subtree is higher than the left and we inserted on the left side of the right subtree.
        else if (height_difference<-1 && spell.getPowerLevel()<node.right.getSpell().getPowerLevel()){
            node.right = rightrotate(node.right);
            return lefttrotate(node);
        }
        return node;
    }

    private Node rightrotate (Node problematic_node) {//A function that performs a rotation to the right (in case of imbalance on the left side)
        //We will perform a pointer swap so that all vertices "move" one vertex to the right.
        Node x = problematic_node.left;
        Node z = x.right;
        x.right = problematic_node;
        problematic_node.left = z;

        //We will update the heights of the vertex where the imbalance occurred and the vertex that replaced it.
        int left_problematic_node_height = problematic_node.left==null ? -1 : problematic_node.left.height;
        int right_problematic_node_height = problematic_node.right == null ? -1 : problematic_node.right.height;
        problematic_node.height = 1 + (left_problematic_node_height>right_problematic_node_height ? left_problematic_node_height : right_problematic_node_height);//To reach the height of the vertex, we select the child with the maximum height and add 1 to it.

        int x_left_height = x.left == null ? -1 : x.left.height;
        int x_right_height = x.right == null ? -1 : x.right.height;
        x.height = 1+(x_left_height>x_right_height ? x_left_height : x_right_height);//To reach the height of the vertex, we select the child with the maximum height and add 1 to it.
        return x;//We will return the root of our new subtree
    }
    private Node lefttrotate (Node problematic_node) {//A function that performs left rotation (in case of imbalance on the right side)
        //We will perform a pointer swap so that all vertices "move" one vertex to the left.
        Node x = problematic_node.right;
        Node z = x.left;
        x.left = problematic_node;
        problematic_node.right = z;

        //We will update the heights of the vertex where the imbalance occurred and the vertex that replaced it.
        int left_problematic_node_height = problematic_node.left==null ? -1 : problematic_node.left.height;
        int right_problematic_node_height = problematic_node.right == null ? -1 : problematic_node.right.height;
        problematic_node.height = 1 + (left_problematic_node_height>right_problematic_node_height ? left_problematic_node_height : right_problematic_node_height);//To reach the height of the vertex, we select the child with the maximum height and add 1 to it.

        int x_left_height = x.left == null ? -1 : x.left.height;
        int x_right_height = x.right == null ? -1 : x.right.height;
        x.height = 1+(x_left_height>x_right_height ? x_left_height : x_right_height);//To reach the height of the vertex, we select the child with the maximum height and add 1 to it.

        return x;//We will return the root of our new subtree
    }

    private void getTopK_helper(Node node, int k, List<Spell> list_to_return) {//A helper function that performs a sort of reverse transition to the inorder walk, so that we first go right (when there is nowhere else to go, we add the vertex) and only then go left.
        if (node == null || list_to_return.size()==k) {//If we have nowhere to go in the tree or we have reached the k required elements in the array, we will stop the recursion.
            return;
        }
        getTopK_helper(node.right,k,list_to_return);
        if (list_to_return.size()<k) {
            list_to_return.add(node.getSpell());
        }
        getTopK_helper(node.left,k,list_to_return);
    }
}


