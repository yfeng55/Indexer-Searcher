# Indexer / Retriever

Indexer and Retriever programs for building and searching indices created from text extracted from HTML files

### Compile and Run Indexer:

###### 1. compile

```sh
javac ­-cp "./Lucene/*:./HTMLParser/*" Indexer.java ./HTMLParser/JTidyHTMLHandler.java
```

###### 2. run indexer

```sh
java ­-cp .:Lucene/*:./HTMLParser Indexer ­-index ./_index ­-docs ./_webpagesToIndex/
```

### Compile and Run Searcher:

###### 1. compile

```sh
javac ­-cp "./Lucene/*" Searcher.java
```

###### 2. run searcher
```
java ­-cp .:Lucene/* Searcher ­-index ./_index ­-q "machine learning"
```

### Reset
```sh
rm *.class; rm ./HTMLParser/JTidyHTMLHandler.class; rm ­-rf _index; rm results.html
```