# Hybrid Data Structures: Double Hashing & AVL-Chained Hash Tables (Java)

An advanced Java algorithmic data structures project implementing composite associative structures: open addressing with Double Hashing probing, and an AVL-tree chained hash table supporting sorted Top-K queries without runtime sorting.

## Architecture & Modules

### 1. Open Addressing with Double Hashing (`DoubleHashTable`)
- Primary and secondary modular polynomial hash functions ($h_1$, $h_2$) using ASCII character weighting.
- Collision resolution sequence: $(h_1(k) + i \cdot h_2(k)) \pmod m$.
- Tracks precise collision probe distances via `getLastSteps()`.
- Constant average-time spell lookup and insertion.

### 2. Balanced AVL Tree (`AVLTree`)
- Height-balanced Binary Search Tree (BST) sorted by spell `powerLevel`.
- Automatic tree rebalancing upon insertion via Single and Double Rotations (LL, RR, LR, RL).
- Strict $O(k)$ Top-K retrieval (`getTopK`) utilizing reverse in-order traversal (Right-Root-Left) without external array sorting.

### 3. AVL-Chained Hash Table (`HashAVLSpellTable`)
- Multi-tier composite data structure combining hash-indexed category routing with tree-structured priority lookup.
- Separate chaining resolution where each bucket holds a `LinkedList<AVLTree>` grouping distinct spell categories.
- Provides fast hierarchical querying: category bucket hash -> linked list search -> balanced BST point search.

## Complexity Highlights
- Double Hashing Lookup: $O(1)$ average, $O(n)$ worst-case.
- AVL Insertion & Search: Guaranteed $O(\log n)$ worst-case due to strict height balancing.
- Reverse In-Order Top-K: $O(k + \log n)$ time complexity with zero array sorting overhead.

## File Structure
- `SpellSimple.java`: Lightweight entity storing spell name and incantation words.
- `DoubleHashTable.java`: Open addressing hash table with double hashing collision handling.
- `Spell.java`: Domain entity storing spell name, category, power level, and cast words.
- `AVLTree.java`: Self-balancing AVL tree maintaining spell records sorted by power level.
- `HashAVLSpellTable.java`: Multi-level hash table chained with balanced AVL trees per category.
- `Tester.java`: Validation suite covering edge cases, balance factors, collisions, and Top-K extraction.

## Requirements
- Java Development Kit (JDK 8 or higher).

## Build & Run
Compile all Java source files:
javac *.java

Run test suite:
java Tester
