import java.util.Arrays;
import java.util.List;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Stack;
import java.util.PriorityQueue;

class ST {
    /**
     * TreeNode class.
     */

    private class TreeNode {
        WordFreq item;
        int number = 1;
        TreeNode l;
        TreeNode r;
        String word;

        //TreeNode constructor.
        private TreeNode(String word, int number) {
            this.word = word;
            this.number = number;
            l = r = null;
        }

        //TreeNode constructor.
        private TreeNode(String word) {
            this.word = word;
        }

        //TreeNode constructor.
        private TreeNode(int number) {
            this.number = number;
        }

        //Item getter.
        private String getItem() {
            return word;
        }

        //Left setter.
        private void setLeft(TreeNode l) {
            this.l = l;
        }

        //Left getter.
        private TreeNode getLeft() {
            return l;
        }

        //Right setter.
        private void setRight(TreeNode r) {
            this.r = r;
        }

        //Right getter.
        private TreeNode getRight() {
            return r;
        }

        //Number getter.
        private int getNumber() {
            return number;
        }

        //Increase method.
        private void setNumber() {
            number++;
        }
    }

    /**
     * Root of the binary search tree.
     */
    private TreeNode head;

    /**
     * List of the stop words.
     */
    private List < String > stopWords = Arrays.asList(new String[] {
        "the"
    });

    /**
     * Finds the node a given element is stored at.
     * @param element The element to search for.
     * @return The node that hosts element, or null if element wasn’t found.
     */
    TreeNode search(String w) {
        TreeNode p = head;
        while (p != null) {
            int result = w.compareTo(p.getItem());
            if (result == 0)
                break;
            p = result < 0 ? p.getLeft() : p.getRight();
        }
        return p;
    }

    /**
     * Inserts an element in the tree.
     */
    void insert(WordFreq word) {
        String trimmed = word.key();
        if (trimmed.equals(""))
            return;
        if (head == null) {
            head = new TreeNode(trimmed);
            return;
        }
        update(trimmed);
    }

    /**
     * Updates the tree with an element.
     */
    void update(String w) {
        TreeNode current = head;
        TreeNode newNode = new TreeNode(w);
        while (current != null) {
            if (w.equals(current.getItem())) {
                current.setNumber();
                return;
            } else if (newNode.getItem().compareTo(current.getItem()) < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(newNode);
                    return;
                } else {
                    current = current.getLeft();
                }
            } else {
                if (current.getRight() == null) {
                    current.setRight(newNode);
                    return;
                } else {
                    current = current.getRight();

                }
            }
        }
    }

    /**
     * Removes a word from the tree.
     */
    void remove(String w) {
        removeR(head, w);
    }

    /**
     * Removes a given node from the tree.
     * @throws NullPointerException if p is null.
     * @param p The node to remove.
     */
    TreeNode removeR(TreeNode h, String w) {
        if (h == null)
            return null;
        String word = h.item.key();
        if (w.compareTo(word) < 0)
            h.l = removeR(h.l, w);
        if (word.compareTo(w) < 0)
            h.r = removeR(h.r, w);
        if (word.compareTo(w) == 0)
            h = joinLR(h.l, h.r);
        return h;
    }

    /**
     * Join nodes.
     */
    TreeNode joinLR(TreeNode a, TreeNode b) {
        if (b == null) return a;
        b = partR(b, 0);
        b.l = a;
        return b;
    }

    /**
     * Parts nodes.
     */
    TreeNode partR(TreeNode h, int k) {
        int t = (h.l == null) ? 0 : h.l.number;
        if (t > k) {
            h.l = partR(h.l, k);
            h = rotR(h);
        }
        if (t < k) {
            h.r = partR(h.r, k - t - 1);
            h = rotL(h);
        }
        return h;
    }

    /**
     * Right rotation.
     */
    TreeNode rotR(TreeNode h) {
        TreeNode x = h.l;
        h.l = x.r;
        x.r = h;
        return x;
    }

    /**
     * Left rotation.
     */
    TreeNode rotL(TreeNode h) {
        TreeNode x = h.r;
        h.r = x.l;
        x.l = h;
        return x;
    }

    /**
     * Reads the text file containing some English text.
     * Creates the binary search tree after removing punctuation
     * and ignoring stop words.
     * @throws FileNotFoundException 
     */
    void load(String filename) {
        //Creates a new ST object.
        ST tree = new ST();

        File file = new File(filename);
        BufferedReader in = null;

        //Reading the text file line by line and checking the syntax and if the lines have been read correctly. 
        try { in = new BufferedReader(new FileReader(file));

            //Inserts each word in the tree.
            String str = in .readLine();
            while (str != null) {
                String[] line = str.split(" ");
                for (int i = 0; i < line.length; i++) {
                    String word = line[i];
                    word = word.toLowerCase();
                    word = word.replaceFirst("^[^a-z]*", "");
                    word = word.replaceFirst("[^a-z]*$", "");
                    WordFreq element = new WordFreq(word);

                    //Search if the word is contained in the stop words list.
                    if (stopWords != null)
                        if (!stopWords.contains(word))
                            tree.insert(element);
                }
                str = in .readLine();
            } in .close(); //Closes the file reading process.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the number of distinct words.
     */
    int getDistinctWords() {
        int count = 0;
        if (head == null)
            return 0;
        Stack < TreeNode > s = new Stack < TreeNode > ();
        TreeNode curr = head;
        while (curr != null || s.size() > 0) {
            while (curr != null) {
                s.push(curr);
                curr = curr.getLeft();
                count++;
            }
            curr = s.pop();
            curr = curr.getRight();
        }
        return count;
    }

    /**
     * Returns the total number words.
     */
    int getTotalWords() {
        int sum = 0;
        if (head == null)
            return 0;
        Stack < TreeNode > s = new Stack < TreeNode > ();
        TreeNode curr = head;
        while (curr != null || s.size() > 0) {
            while (curr != null) {
                s.push(curr);
                curr = curr.getLeft();
            }
            curr = s.pop();
            sum += curr.getNumber();
            curr = curr.getRight();
        }
        return sum;
    }

    /**
     * Returns the frequency of a given word.
     */
    int getFrequency(String w) {
        TreeNode current = head;
        int freq = 0;
        while (current != null) {
            if (w.equals(current.getItem())) {
                freq = current.getNumber();
            } else if (w.compareTo(current.getItem()) < 0) {
                current = current.getLeft();
                if (w.equals(current.getItem()))
                    freq = current.getNumber();
                else
                    break;
            } else {
                current = current.getRight();
                if (w.equals(current.getItem()))
                    freq = current.getNumber();
                else
                    break;
            }
        }
        return freq;
    }

    /**
     * Returns the maximum frequency of all words.
     */
    int getMaximumFrequency() {
        PriorityQueue < Integer > freq = new PriorityQueue < Integer > ();
        TreeNode curr = head;

        while (!freq.isEmpty() || curr != null) {
            if (curr != null) {
                freq.add(curr.getNumber());
                curr = curr.getLeft();
            } else {
                TreeNode node = new TreeNode(freq.poll());
                curr = node.getRight();
            }
        }
        return freq.peek();
    }

    /**
     * Returns the mean frequency of all words.
     */
    double getMeanFrequency() {
        double distinct = (double) getDistinctWords();
        double total = (double) getTotalWords();
        return distinct / total;
    }

    /**
     * Add a stop word to the stop words list.
     */
    void addStopWord(String w) {
        w = w.toLowerCase();
        w = w.replaceFirst("^[^a-z]*", "");
        w = w.replaceFirst("[^a-z]*$", "");
        stopWords.add(w);
    }

    /**
     * Remove a stop word from the stop words list.
     */
    void removeStopWord(String w) {
        w = w.toLowerCase();
        w = w.replaceFirst("^[^a-z]*", "");
        w = w.replaceFirst("[^a-z]*$", "");
        stopWords.remove(w);
    }

    /**
     * Prints the binary tree by alphabetical order.
     */
    String toStringR(TreeNode node) {
        if (node == null)
            return "";
        String amountFormat = String.format("%5d", node.getNumber());
        String s = toStringR(node.getLeft());
        System.out.println(amountFormat + "      " + node.getItem());
        s += toStringR(node.getRight());
        return s;
    }

    /**
     * Prints the binary tree by alphabetical order.
     */
    void printTreeAlphabetically(PrintStream stream) {
        System.out.println("Tree in alphabetical order:");
        System.out.println();
        System.out.println("Frequency  Word");
        System.out.println("---------  -----");
        stream.print(toStringR(head));
    }
}
