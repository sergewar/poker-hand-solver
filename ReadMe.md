![Java CI with Maven](https://github.com/sergewar/s-bootcamp/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
## Here a program implementing algorithm for comparing the strength of Texas Hold'em Hands.
More info about Texas Hold'em game available at [wiki](https://en.wikipedia.org/wiki/Texas_hold_%27em)

##### Input format of data should be:
<5 board cards> <hand 1> <hand 2> <...> <hand N>

- <5 board cards> is a 10 character string where each 2 characters encode a card
- \<hand X> is a 4 character string where each 2 characters encode a card, with 2 cards per hand
- \<card> is a 2 character string with the first character representing the rank (one of "A", "K", "Q",
"J", "T", "9", "8", "7", "6", "5", "4", "3", "2") and the second character representing the suit (one of
"h", "d", "c", "s").<br>

##### The output will be written to standard output using the format:
\<hand block 1> \<hand block 2> \<...> \<hand block n>

where:
- \<hand block 1> is the hand block with the weakest value
- \<hand block 2> is the hand block with the second weakest value
- ... and so forth.
- \<hand block n> is the hand block with the strongest value

Each hand block consists of one or multiple hands (each represented by 4 character string with 2
characters to encode a card, with 2 cards per hand) with equal strength
In case there are multiple hands with the same value on the same board they should be ordered
alphabetically and separated by "=" signs  
The order of the cards in each hand should remain the same as in the input, e.g., don't reorder `2h3s` into `3s2h`.  
In case the input line is invalid, output a clear & easy to understand error message

##### Example:
Input:  
`4cKs4h8s7s Ad4s Ac4d As9s KhKd 5d6d`  
`2h3h4h5d8d KdKs 9hJh`

Output:  
`Ac4d=Ad4s 5d6d As9s KhKd`  
`KdKs 9hJh`

Program have command line parameter `--omaha` which switching to evaluate hand values
according to [omaha rules](https://en.wikipedia.org/wiki/Omaha_hold_%27em).

### Requirements:
Java 11  
Maven 3.6.3

### How to compile
    mvn package  
at `target` folder will be jar file `s-bootcamp.jar`

### How to run
    java -jar target/s-bootcamp.jar [--omaha]
