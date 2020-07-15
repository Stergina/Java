# Reversi


**Objective**:  
Implementing a program that allows users to play the game Reversi against a computer via a GUI. Specifically, the computer moves are performed by using the Minimax algorithm and the user should be able to choose the difficulty of the game and whether to play first.



**Structure of the code**:  
The code includes the following classes:

* State Class: This class is used to represent the state of the board at any given time as well as the various movements of the disks. Specifically, this class includes two constructors, the default State constructor that initializes the board and the State constructor that takes the reversi character table as a parameter (an 8x8 table).
* Game Class: In this class, we build all the methods that are necessary for the computer to perform its movement each time. It includes the default Game constructor who initializes the search depth of the minimax algorithm to one. 
* Main Class: In this class, we build all the methods that are necessary for the computer to perform its movement each time. It includes the default Game constructor who initializes the search depth of the minimax algorithm to one. 





Τέλος, δημιουργούμε την κλάση main στην οποία ουσιαστικά και εκτελούμε τον κώδικα. Στην κλάση αυτή υλοποιούμε ένα Graphical User Interface (GUI) για να αναπαραστήσουμε το Reversi. Συγκεκριμένα, το πρόγραμμα ξεκινά και εμφανίζεται ένα παράθυρο που ζητά από τον χρήστη αν θέλει να παίξει πρώτος. Στην περίπτωση που ο χρήστης επιλέξει να παίξει πρώτος τότε θα εμφανιστεί ένα παράθυρο που θα τον πληροφορεί ότι οι δίσκοι του έχουν χρώμα μαύρο, ενώ αν επιλέξει να παίξει δεύτερος οι δίσκοι του έχουυν χρώμα λευκό. Εν τέλει, αυτός που παίζει πρώτος έχει πάντα τους δίσκους χρώματος μαύρου και εκείνος που παίζει δεύτερος τους δίσκους χρώματος λευκού. Στην συνέχεια, εμφανίζεται ένα παράθυρο που ζητά από τον χρήση να επιλέξει το επίπεδο δυσκολίας του παιχνιδιού. Ανάμεσα στις επιλογές έχουμε: “Easy”, “Medium” και “Hard”. Η επιλογή αυτή του χρήστη καθορίζει και το βάθος της αναζήτησης που θα χρησιμοποιήσει ο Minimax αλγόριθμος. Αφού, λοιπόν, ο παίκτης επιλέξει το επίπεδο δυσκολίας θα εμφανιστεί το παράθυρο του παιχνιδιού με το τωρινό score να καταγράφεται στο πάνω μέρος του. Κάθε φορά που ο χρήστης επιλέγει να βάλει τον δίσκο του σε κουτί που δεν επιτρέπεται από τους κανονισμούς θα εμφανιστεί ένα παράθυρο που τον πληροφορεί ότι η κίνηση είναι μη επιτρεπτή. Επίσης, αν ο παίκτης επιλέξει να τοποθετήσει τον δίσκο του σε ένα κουτί που υπάρχει ήδη άλλος δίσκος, θα εμφανιστεί και πάλι παράθυρο που θα τον πληροφορεί για το ότι η κίνηση του δεν είναι επιτρεπτή. Ακόμα, όταν δεν υπάρχουν άλλες διαθέσιμες κινήσεις θα εμφανιστεί και το ανάλογο παράθυρο. Τέλος, όταν το παιχνίδι τελειώσει θα εμφανείστει ένα παράθυρο που πληροφορεί τον χρήστη αν κέρδισε (You win!) ή αν έχασε (“You lose!”).
