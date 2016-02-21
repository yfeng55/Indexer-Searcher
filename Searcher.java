

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;



public class Searcher {


    private static String directoryname = "";

    public static void main(String[] args) throws IllegalArgumentException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

        if (args.length != 4) {
            throw new IllegalArgumentException("Must provide arguments: " + "-index <index dir> -q <query>");
        }


       String indexDir = "";
       String query = "";


       for(int i=0; i<args.length; i+=2){
           switch(args[i]){
                case "-index":
                    indexDir = args[i+1];
                    break;
                case "-q":
                    query = args[i+1];
                    break;
                default:
                    throw new IllegalArgumentException("Must provide arguments: " + "-index <index dir> -q <query>");
           }
       }



        search(indexDir, query);
    }


    public static void search(String indexDir, String q) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

        Directory dir = FSDirectory.open(Paths.get(new File(indexDir).getPath()));

        IndexReader indexreader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(indexreader);

        //create a new QueryParser
        QueryParser parser = new QueryParser("contents", new StandardAnalyzer());

        Query query = parser.parse(q);
        long start = System.currentTimeMillis();

        TopDocs hits = searcher.search(query, 10);
        long end = System.currentTimeMillis();





        // PRINT OUTPUT (SEARCH RESULTS) //

        System.out.println("\nFound " + hits.totalHits + " document(s) (in " + (end - start) + " milliseconds) that matched query '" + q + "':\n");

        System.out.println("Results for query '" + q + "' in directory '" + directoryname + "' \n");


        String htmlString = "";


        int i = 1;
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);

            System.out.println(Integer.toString(i) + ". " + doc.get("title"));
            System.out.println("\t" + doc.get("fullpath") + "\n");

            
            //set directoryname
            if(directoryname.equals("")){
                String fullpath = doc.get("fullpath");

                int lastindex = fullpath.lastIndexOf("/");

                String shortened_fullpath = fullpath.substring(0, lastindex);

                int secondlastindex = shortened_fullpath.lastIndexOf("/");
                directoryname = fullpath.substring(secondlastindex+1, lastindex);

                // System.out.println("DIRECTORY NAME: " + directoryname);
            }


            htmlString += "<p><b><i>" + Integer.toString(i) + "</i>. " + doc.get("title") + "</b><br/> " +
                    "<span style='margin-left:3em'>" + doc.get("fullpath") + "</span></p>";

            i++;
        }

        htmlString += "</body></html>";


        // PREPEND TITLE
        htmlString = "<title>" + "Results for query '" + q + "' in directory '" + directoryname + "</title></head><body>" + htmlString;
        htmlString = "<h1>Results for query <u>" + q + "</u> in directory <u>" + directoryname + "</u> </h1>" + htmlString;
        htmlString = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + htmlString;
        


        //output to HTML file
        File htmlResultsPage = new File("./results.html");
        FileUtils.writeStringToFile(htmlResultsPage, htmlString);

        //close the indexreader
        indexreader.close();
    }



}
