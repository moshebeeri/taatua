package org.vidad.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.search.spell.SpellChecker;
import org.vidad.lucene.SpellCheckService;
import org.vidad.tools.conf.Collection;

public class Dictionary extends Collectionable<Dictionary> {
	transient Logger log = Logger.getLogger(Dictionary.class);
	List<String> words;
	
	public Dictionary() {
	}

	@Override
	public Collection getCollection() {
		return Collection.DICTIONARY;
	}
	
	public static Dictionary fromFile(Path path) throws IOException{
		String word;
		Dictionary dictionary = new Dictionary();
		dictionary.words = new ArrayList<>();
		FileInputStream fis = new FileInputStream(path.toFile());
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		while ((word = reader.readLine()) != null) {
			dictionary.words.add(word);
		}
		reader.close();
		return dictionary;
	}
	
	public SpellChecker createSpellChecker() throws Exception{
		return new SpellCheckService().createSpellChecker(toInputStreamReader());
	}
	
	public Trie createTrie(){
		return null;
	}

	public InputStreamReader toInputStreamReader() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
		for(String word : words)
			writer.write(word+"\n");
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		return new InputStreamReader(bais);
	} 
	public static void main(String[] args) throws IOException, Exception {
		SpellChecker spellChecker = Dictionary.fromFile(Paths.get("/home/moshe/data/vidad/ftp.cerias.purdue.edu/pub/dict/dictionaries/English/words.english")).
			createSpellChecker();
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
