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
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.vidad.autocomplete.Completer;
import org.vidad.tools.conf.Collection;

public class Dictionary extends Collectionable<Dictionary> implements Completer{
	transient Logger log = Logger.getLogger(Dictionary.class);
	String	name;
	String	lang;
	boolean	open=false;
	List<String> words;
	transient SpellChecker spellChecker;
	transient Trie trie;
	
	public Dictionary(String name, String lang) {
		this.name	=	name;
		this.lang	=	lang;
		words		= new ArrayList<String>();
		spellChecker= null;
		trie		= null;
	}

	@Override
	public Collection getCollection() {
		return Collection.DICTIONARY;
	}
	
	@Override
	public void lightweight() {
		super.lightweight();
		words		= new ArrayList<String>();
		spellChecker= null;
		trie		= null;
	}
	
	@Override
	public void retrived(){
		super.retrived();
		try {
			spellChecker = createSpellChecker();
			trie = new Trie();
			for(String word : words)
				trie.insert(word);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Dictionary fromFile(String name, String lang, Path path) throws IOException{
		String word;
		Dictionary dictionary = new Dictionary(name, lang);
		dictionary.words = new ArrayList<>();
		FileInputStream fis = new FileInputStream(path.toFile());
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		while ((word = reader.readLine()) != null) {
			dictionary.words.add(word);
		}
		reader.close();
		dictionary.mongo.insertCollectionable(dictionary);
		return dictionary;
	}
	
	protected SpellChecker createSpellChecker() throws Exception{
		return createSpellChecker(toInputStreamReader());
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
	
	@Override
	public List<NamedId> autocompEx(String prefix) {
		return null;
	}
	
	@Override
	public List<String> autocomp(String prefix) {
		return trie.search(prefix);
	}

	@Override
	public void update(NamedId newone) {
		if(open)
			words.add(newone.getName());
		//else
		//this dictionary is closed.
	}

	@Override
	public String name() {
		return name;
	}
	
	
	public static void main(String[] args) throws IOException, Exception {
		SpellChecker spellChecker = Dictionary.fromFile("words.english","en", Paths.get("/home/moshe/data/vidad/ftp.cerias.purdue.edu/pub/dict/dictionaries/English/words.english")).
			createSpellChecker();
        String wordForSuggestions = "hello";
        if( !spellChecker.exist(wordForSuggestions) ) {
	        int suggestionsNumber = 5;
	        String[] suggestions = spellChecker.suggestSimilar(wordForSuggestions, suggestionsNumber); // word 'hello' is misspeled like 'hwllo'
	        if (suggestions!=null && suggestions.length>0) {
	            for (String word : suggestions) {
	                System.out.println("Did you mean:" + word);
	            }
	        }
	        else {
	            System.out.println("No suggestions found for word:"+wordForSuggestions);
	        }
        }else{
            System.out.println("word:"+wordForSuggestions + " is spelled ok");

        }
       spellChecker.close();
		
	}
}
