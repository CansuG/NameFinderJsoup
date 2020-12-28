import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main  {

    public static void main (String[] args) throws Exception {
        Document doc = Jsoup.connect(args[0]).get();
        String str = doc.text();

        InputStream modelIn = new FileInputStream("src/main/resources/en-token.bin");
        TokenizerModel model = new TokenizerModel(modelIn);
        Tokenizer tokenizer = new TokenizerME(model);
        String [] tokens = tokenizer.tokenize(str);

        InputStream modelIn2 = new FileInputStream("src/main/resources/en-ner-person.bin");
        TokenNameFinderModel model2 = new TokenNameFinderModel(modelIn2);
        NameFinderME nameFinder = new NameFinderME(model2);

        Span nameSpans[] = nameFinder.find(tokens);
        String[] personNames = Span.spansToStrings(nameSpans, tokens);

        for(int i =0; i<personNames.length;i++){
            System.out.println(personNames[i]);
        }




    }
}