/**
 * 
 */
package org.vidad.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * @author moshe
 *
 */
public class SpellCheckService {
	transient Logger log = Logger.getLogger(SpellCheckService.class);

	/**
	 * 
	 */
	public SpellCheckService() {
	}
	
	public SpellChecker createSpellChecker(InputStreamReader isr)throws Exception{
        RAMDirectory spellCheckerDir = new RAMDirectory();
        SpellChecker spellChecker = new SpellChecker(spellCheckerDir);
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, analyzer);
        PlainTextDictionary plainTextDictionary = new PlainTextDictionary(isr);
        spellChecker.indexDictionary(plainTextDictionary, config, true);
		return spellChecker;	
	}
	
	/**
	 * @param args
	 */
    public static void main(String[] args) throws Exception {
        RAMDirectory spellCheckerDir = new RAMDirectory();
        SpellChecker spellChecker = new SpellChecker(spellCheckerDir);
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, analyzer);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("/home/moshe/data/vidad/ftp.cerias.purdue.edu/pub/dict/dictionaries/English/words.english")), "UTF-8");
        PlainTextDictionary dictionary = new PlainTextDictionary(isr);
        spellChecker.indexDictionary(dictionary, config, true);
//        isr = new InputStreamReader(new FileInputStream(new File("/home/moshe/data/vidad/ftp.cerias.purdue.edu/pub/dict/dictionaries/Swedish/words.swedish")), "UTF-8");
//        PlainTextDictionary swdictionary = new PlainTextDictionary(isr);
//        spellChecker.indexDictionary(swdictionary, config, true);
        String wordForSuggestions = "hwllo";
        int suggestionsNumber = 5;

        String[] suggestions = spellChecker.suggestSimilar("hwllo", suggestionsNumber); // word 'hello' is misspeled like 'hwllo'
        if (suggestions!=null && suggestions.length>0) {
            for (String word : suggestions) {
                System.out.println("Did you mean:" + word);
            }
        }
        else {
            System.out.println("No suggestions found for word:"+wordForSuggestions);
        }
       spellChecker.close();
    }
}
