# Rake-Java
java implementation of the Rapid Automatic Keyword Extraction


#Description
This version of Rake algorithm combines both the traditional behaviour of the stadnard algorithm 
and the fact that it's Stoplist is generated automatically for each individual document via a part of speech tagger.
With the constructor you can assign the stoplist via a String array(each cell is a stopword).
Before running the algorithm a part of speech tagger should be used in order to decide which will be the stopwords and which words 
will be the candidates ones.
