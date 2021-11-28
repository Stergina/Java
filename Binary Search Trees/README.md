# Word Counter

**Objective**:  
Implementing a program that uses Binary Search Trees in order to count the number of occurrences of each word in a given text file.

**Implementation**:  
The Word Counter program "loads" a text file and counts how many times each word is displayed. For example, if the file contains the following text

\- Hello, how are you?
\- Very well, thank you. How about you?
\- Fine, thank you.

the word "you" appears 4 times, the word "thank" 2 times, the word how also 2 times while words "hello", "are", "very", "well", "what", "about" and "fine" appear 1 time. The Word Counter program should remove punctuation marks (e.g. . ? ; ! -: “”) from words, as well as parentheses, brackets, action symbols or any other character that is not a letter of the English alphabet. From the text above, when reading e.g. the string “well,” comma should be removed so as to leave only the word "well". Only the single apostrophe is allowed. Also all the strings consisting of numbers should be ignored, e.g. 17:25 or 1980’s. Moreover, the user is able to define special words (stop words) that will be completely ignored, e.g. articles "a", "an" and "the". Finally, the program will be case in-sensitive, for example words "Hello" and "hello" are the same.
