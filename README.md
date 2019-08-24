# EC-Assignment-1

#-----------Group Members------------#\
Brett Shelley a1645904 brett.shelley@student.adelaide.edu.au \
David Harley         a1688375  david.harley@student.adelaide.edu.au\
Jayden Boskell       a1705111  a1705111@student.adelaide.edu.au \
Raymond Habis        a1631834  raymond.habis@student.adelaide.edu.au \
Matthew Durflinger   a1669837  matthew.durflinger@student.adelaide.edu.au \
Patrick Ellway       a1717790  patrick.ellway@student.adelaide.edu.au \

#--------Running Instructions--------#\

#compile the program with javac Main.java\

#to test local Search\

1. Jump\
    java Main <filePath> 1 10\

2. Exchange\
    java Main <filePath> 2 10\

3. TwoOpt\
    java Main <filePath> 3 10\

ex. java Main ./Problems/kroB100.tsp 3 10\
This would run twoOpt on the kroC100 dataset.\

#to test the Evolutionary Algorithms\

1. Algorithm1\
    java Main <filePath> 4 <populationSize>\
2. Algorithm2\
    java Main <filePath> 5 <populationSize>\
3. Algorithm3\
    java Main <filePath> 6 <populationSize>\

ex. java Main ./Problems/kroC100.tsp 6 50\
This would run algorithm 3 with a population of 50 on the kroC100 dataset.\

#to test inverOver
    java Main <filePath> 7 <populationSize>\


