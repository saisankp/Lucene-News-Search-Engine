package ie.tcd.goldenretrievers.irproject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.search.similarities.LMJelinekMercerSimilarity;
import org.apache.lucene.search.similarities.Similarity;

public class Main {
	public static final String INDEX_DIR = "./index";

	// Various types of analyzers specialized for usage - indexing, multi-field etc
	public enum AnalyzerType {
		DEFAULT, SYNONYM
	}

	public static void main(String[] args) throws IOException, ParseException {
		if (args.length == 0) {
			System.out.println("No arguments were passed in. Try again.");
			System.exit(-1);
		}
		Map<AnalyzerType, Analyzer> analyzers;
		Similarity scoring;
		QueryParser parser;

		if (args[0].equals("--operation") && args.length > 1) {
			analyzers = getAnalyzerFromArguments(args);

			switch (args[1].toLowerCase()) {
			case "index":
				File folder = new File(
						INDEX_DIR + "/" + analyzers.get(AnalyzerType.DEFAULT).getClass().getSimpleName());
				if (folder.exists() && folder.isDirectory()) {
					System.out.println("Skipping indexing (indexing for " + args[2] + " was already done previously)");
				} else {
					long startTime = System.currentTimeMillis();
					Indexer indexer = new Indexer();
					indexer.index(analyzers.get(AnalyzerType.DEFAULT));
					long finishTime = System.currentTimeMillis();
					System.out.println("Indexing completed using the " + args[2] + " which took "
							+ (finishTime - startTime) / 1000 + " seconds!");
				}
			case "search":
				if (args.length == 5) {
					scoring = getSimilarityScoringFromArguments(args[3]);
					parser = getQueryParserFromArguments(args[4], analyzers.get(AnalyzerType.DEFAULT));
					Searcher searcher = new Searcher(analyzers, scoring, parser);
					searcher.searchForCommandLine();
				}

			case "evaluate":
				if (args.length == 5) {
					scoring = getSimilarityScoringFromArguments(args[3]);
					parser = getQueryParserFromArguments(args[4], analyzers.get(AnalyzerType.DEFAULT));
					Scorer scorer = new Scorer();
					scorer.score(analyzers, scoring, parser, args);
				}
			}
		} else {
			System.out.println("An invalid argument was passed in. Try again.");
		}
	}

	public static Map<AnalyzerType, Analyzer> getAnalyzerFromArguments(String[] args) {
		Map<AnalyzerType, Analyzer> analyzers = new HashMap<>();
		switch (args[2]) {
		case "StandardAnalyzer":
			analyzers.put(AnalyzerType.DEFAULT, new StandardAnalyzer());
			return analyzers;

		case "CustomAnalyzer":
			analyzers.put(AnalyzerType.DEFAULT, new CustomAnalyzer(false));
			analyzers.put(AnalyzerType.SYNONYM, new CustomAnalyzer(true));
			return analyzers;

		case "EnglishAnalyzer":
			analyzers.put(AnalyzerType.DEFAULT, new EnglishAnalyzer());
			return analyzers;

		default:
			System.out.println("Invalid analyzer requested. Try again.");
			System.exit(-1);
			return null;
		}
	}

	public static Similarity getSimilarityScoringFromArguments(String scoring) {
		switch (scoring) {
		case "BM25":
			return new BM25Similarity();

		case "VSM":
			return new ClassicSimilarity();

		case "LMDirichlet":
			return new LMDirichletSimilarity();

		case "LMJelinekMercer":
			return new LMJelinekMercerSimilarity(0.65f);

		default:
			System.out.println("Invalid similarity requested. Try again.");
			System.exit(-1);
			return null;
		}
	}

	public static QueryParser getQueryParserFromArguments(String parser, Analyzer analyzer) {
		switch (parser) {
		case "QueryParser":
			return new QueryParser("content", analyzer);

		case "MultiFieldQueryParser":
			HashMap<String, Float> weightings = new HashMap<>();
			weightings.put("title", 0.1f);
			weightings.put("content", 1.0f);
			weightings.put("TEXT", 0.4f);

			return new MultiFieldQueryParser(new String[] { "title", "content", "TEXT" }, analyzer, weightings);

		default:
			System.out.println("Invalid parser requested. Try again.");
			System.exit(-1);
			return null;
		}
	}
}
