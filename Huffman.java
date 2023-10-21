//Mansi Srinivas

//B01035912
//Huffman Coding
import java.io.*;

import java.util.*;

//This class stores the frequencies of the letters and also has the method to compare the nodes
class LetterFrequency implements Comparable<LetterFrequency> {
     private int frequency;
     LetterFrequency leftNode;
     LetterFrequency rightNode;
    //constructor to initialize the frequency
    public LetterFrequency(int frequency) {
        this.frequency = frequency;
        
    }
    //gets the frequency
    public int getFrequency() {
        return frequency;
    }
    
    @Override
    public int compareTo(LetterFrequency other) {
        return Integer.compare(frequency, other.getFrequency());
    }
}
//this class contains the main method
public class Huffman {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Huffman <file_path>");
            return;
        }

        String file = args[0];
        //to read the contents of the file
        try (BufferedReader content = new BufferedReader(new FileReader(file))) {
            int totalLetters = Integer.parseInt(content.readLine().trim());
            PriorityQueue<LetterFrequency> letterQueue = new PriorityQueue<>();

            String line;
            while ((line = content.readLine()) != null) {
                if (!line.isEmpty()) {
                    try {
                        int frequency = Integer.parseInt(line.trim());
                        letterQueue.add(new LetterFrequency(frequency));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid frequency: " + line);
                    }
                }
            }

            System.out.println("The number of letters are " + totalLetters);

            int totalBits = constructtree(letterQueue);
            System.out.println("the total number of bits needed to encode are" + totalBits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //this is to add the frequencies of two numbers and from a new node 
    public static int constructtree(PriorityQueue<LetterFrequency> letterQueue) {
        while (letterQueue.size() > 1) {
            LetterFrequency left = letterQueue.poll();
            LetterFrequency right = letterQueue.poll();
            //creating a new node by adding two nodes
            LetterFrequency parent = new LetterFrequency(left.getFrequency() + right.getFrequency());
            parent.leftNode = left;
            parent.rightNode = right;
            //adding the newly formed node into the queue
            letterQueue.add(parent);
        }

        return calculateTotalBits(letterQueue.peek(), 0);
    }
  //this function is to calculate the total number of bits needed to encode
    private static int calculateTotalBits(LetterFrequency root, int depth) {
        if (root == null) {
            return 0;
        }
        //depth represents the depth at which the letter is located in the tree
        if (root.leftNode == null && root.rightNode == null) {
            return root.getFrequency() * depth;
        }
        //this is to finally tell the number of bits taken
        return calculateTotalBits(root.leftNode, depth + 1) + calculateTotalBits(root.rightNode, depth + 1);
    }
}
