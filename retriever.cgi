#!/bin/bash
echo "Content-type: text/html"
echo ""

echo '<html>'
echo '<head>'
echo '<title>Web Search Engines Programming Assignment 1 : Indexer-Searcher</title>'
echo '</head>'
echo '<body>'

echo "<h1>Index Retriever - Yijie Feng</h1>"

echo "<form method=GET action=\"${SCRIPT}\">"
echo '<table nowrap>'

echo 'Please enter your query:'
    
echo '<tr><td> </td><td><input type="text" name="val_x" size=20></td></tr>'
echo '</tr></table>'
    
echo '<br><input type="submit" value="Search">'
echo '<input type="reset" value="Reset"></form>'

    if [ -z "$QUERY_STRING" ]; then
	#echo "NO QUERY STRING PROVIDED"
	exit 0
    
    else
	#echo "QUERY PROVIDED  "
	
	keyword=`echo "$QUERY_STRING" | sed -n 's/^.*val_x=\([^&]*\).*$/\1/p' | sed "s/%20/ /g\
#{}"`

	#echo $keyword

# Filters out characters <>&*?./ to block malicious users
#      java -cp "jtidy-r938.jar:lucene-core-5.4.1.jar:lucene-queryparser-5.4.1.jar:lucene-ana\                                                                                      
# lyzers-common-5.4.1.jar:lucene-demo-5.4.1.jar:." Retriever -index newIndexes -query $keyword
 
     echo "<h3><a href='results.html'> RESULTS PAGE </a></h3> <br/><br/>"


     java -cp .:Lucene/* Searcher -index ./_index/ -q $keyword
	

  fi

echo '</body>'
echo '</html>'

exit 0
