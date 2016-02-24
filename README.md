# Indexer / Retriever

Indexer and Retriever programs for building and searching indices created from text extracted from HTML files

#### HTML Parser Used: 
J​Tidy 

#### JDK Version:​
1.7.0 


### Compile and Run Indexer:

#### 1. run javac and specify external jars

```sh
javac ­cp "./Lucene/*:./HTMLParser/*" Indexer.java ./HTMLParser/JTidyHTMLHandler.java
```

#### 2. run program

```sh
java ­cp .:Lucene/*:./HTMLParser Indexer ­index ./_index ­docs ./_webpagesToIndex/
```

### Compile and Run Searcher:

#### 1. run javac and specify external jars

```sh
javac ­cp "./Lucene/*" Searcher.java
```

#### 2. run program
```
java ­cp .:Lucene/* Searcher ­index ./_index ­q "machine learning"
```

### Reset (delete .class files and all output from indexer/searcher):
```sh
rm *.class; rm ./HTMLParser/JTidyHTMLHandler.class; rm ­rf _index; rm results.html
```