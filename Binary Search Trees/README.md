# Binary Search Trees in Java


**Objective**:  
Implementing a program that uses Binary Search Trees in order to count the number of occurrences of each word in a given text file.

**Structure of the code**:  
The code implements the following classes:

* **State Class**: This class is used to represent the state of the board at any given time as well as the various movements of the disks. Specifically, this class includes two constructors, the default State constructor that initializes the board and the State constructor that takes the reversi character table as a parameter (an 8x8 table).
* **Game Class**: In this class, we build all the methods that are necessary for the computer to perform its movement each time. It includes the default Game constructor who initializes the search depth of the minimax algorithm to one. 
* **Main Class**: In the main class we implement a Graphical User Interface (GUI) to represent Reversi. The program starts and a window appears asking the user if he wants to play first and the desired level of difficulty (Easy, Medium or Hard). If the user chooses to put the disk in a box that is not allowed by the regulations or that is already occupied by another disc, a window will appear informing him that the movement is not allowed. Finally, when the game is over a window will appear informing the user if he has won or lost.



Αφού, λοιπόν, δημιουργησαμε την κλάση WordFreq, όπου για κάθε διαφορετική λέξη που διαβάζουμε από το text αρχείο δημιουργούμε και ένα τέτοιο αντικείμενο προχωρούμε στο Β μέρος όπου γίνεται και η υλοποίηση του όλου προγράμματος.
Η κλάση ST που δημιουργήσαμε στο δεύτερο μέρος περιλαμβάνει τις παρακάτω μεθόδους (όπου αναγράφονται και οι αντίστοιχες πολυπλοκότητες τους):
