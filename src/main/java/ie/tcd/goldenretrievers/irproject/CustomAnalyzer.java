package ie.tcd.goldenretrievers.irproject;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
//change adding new filters
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;

public class CustomAnalyzer extends Analyzer {
	/*
	 * Creating new custom analyzer with additional filters and stopwords dictionary
	 */
	private static final String STOP_WORD_FILE = "./collection/stopwords.txt";

	private static CharArraySet getStopWords() {
		CharArraySet stopwords = null;

		try {
			byte[] encoded = Files.readAllBytes(Paths.get(STOP_WORD_FILE));
			String[] words = new String(encoded, StandardCharsets.UTF_8).split("\n");
			stopwords = new CharArraySet(Arrays.asList(words), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stopwords;
	}

	boolean applySynonyms = false;

	CustomAnalyzer(boolean applySynonyms) {
		this.applySynonyms = applySynonyms;
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		final Tokenizer tokenizer = new StandardTokenizer();

		TokenStream tokenstream = new ClassicFilter(tokenizer);
		tokenstream = new EnglishPossessiveFilter(tokenstream);
		tokenstream = new ASCIIFoldingFilter(tokenstream);
		tokenstream = new TrimFilter(tokenstream);
		tokenstream = new StopFilter(tokenstream, getStopWords());
		if (applySynonyms) {
			tokenstream = new SynonymFilter(tokenstream, createSynonymMap(), true);
		}
		tokenstream = new LowerCaseFilter(tokenstream);
		tokenstream = new PorterStemFilter(tokenstream);

		return new TokenStreamComponents(tokenizer, tokenstream);
	}

	private SynonymMap createSynonymMap() {
		try {
			// Using StandardAnalyzer as synonymParse can't handle stopword removal
			WordnetSynonymParser synonymParser = new WordnetSynonymParser(true, false, new StandardAnalyzer());
			synonymParser.parse(new FileReader("./synonyms/wn_s.pl"));
			return synonymParser.build();
		} catch (Exception e) {
			System.out.println("Error opening or parsing synonym map file!:" + e.toString());
		}
		return null;
	}
}
