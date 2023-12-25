package ie.tcd.goldenretrievers.irproject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import ie.tcd.goldenretrievers.irproject.Main.AnalyzerType;

public class Searcher {
	static IndexSearcher indexSearcher;
	private final Map<Main.AnalyzerType, Analyzer> analyzers;
	QueryParser queryParser;
	DirectoryReader directoryReader;
	Directory indexDirectory;

	public Searcher(Map<Main.AnalyzerType, Analyzer> analyzers, Similarity similarity, QueryParser parser)
			throws IOException {
		Path pathToIndex = Paths
				.get(Indexer.INDEX_DIR + "/" + analyzers.get(AnalyzerType.DEFAULT).getClass().getSimpleName());
		indexDirectory = FSDirectory.open(pathToIndex);
		directoryReader = DirectoryReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(directoryReader);
		indexSearcher.setSimilarity(similarity);
		queryParser = parser;
		this.analyzers = analyzers;
	}

	public void searchForCommandLine() throws IOException {
		String userQuery;
		Scanner scanner = new Scanner(System.in);

		try {
			while (true) {
				System.out.print("Enter text to search (or control+c to exit): ");
				userQuery = scanner.nextLine().trim();

				if (!userQuery.isEmpty()) {
					System.out.println("Searching for " + userQuery + ":");
					Query query = queryParser.parse(userQuery);
					ScoreDoc[] searchResults = indexSearcher.search(query, 5).scoreDocs;
					System.out.println("Search results:");

					for (int i = 0; i < searchResults.length; i++) {
						Document document = indexSearcher.doc(searchResults[i].doc);
						System.out.println(
								i + 1 + ". \"" + document.get("title") + "\" [Score: " + searchResults[i].score + "]");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exiting search functionality.");
		} finally {
			directoryReader.close();
			indexDirectory.close();
			scanner.close();
		}
	}

	public List<String> generateResultsFromTopic(Topic topic, QueryParser parser, String nameOfScoringFile)
			throws IOException, ParseException {
		// Generate query from the topic (assign different weights to each attribute)

		if (analyzers.get(Main.AnalyzerType.SYNONYM) != null) {
			parser.setAnalyzer(analyzers.get(Main.AnalyzerType.SYNONYM));
		}
		Query title = new BoostQuery(parser.parse(topic.title), 0.9f);
		Query description = new BoostQuery(parser.parse(topic.description), 0.5f);
		// Parsing narrative with synonym expansion drops MAP by about 1%
		parser.setAnalyzer(analyzers.get(Main.AnalyzerType.DEFAULT));
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		builder.add(title, BooleanClause.Occur.SHOULD);
		builder.add(description, BooleanClause.Occur.SHOULD);

		List<String> narrativeSentences = Arrays.asList(topic.narrative.replace("\"", "").split("\\. "));

		List<String> positiveSentences = narrativeSentences.stream()
				.filter(s -> s.length() > 1 && !s.contains("not relevant")).collect(Collectors.toList());

		if (!positiveSentences.isEmpty()) {
			builder.add(new BoostQuery(parser.parse(String.join(" ", positiveSentences).replace("relevant", "")), 0.3f),
					BooleanClause.Occur.SHOULD);
		}

		Query query = builder.build();

		// Search using query
		ScoreDoc[] searchResults = indexSearcher.search(query, 1000).scoreDocs;

		// Iterate through all search results and convert each to an object
		List<String> resultLinesForScoringFile = new ArrayList<>();
		for (int i = 0; i < searchResults.length; i++) {
			Document document = indexSearcher.doc(searchResults[i].doc);
			String documentNo = document.get("docno");
			resultLinesForScoringFile.add(String.format("%d 0 %s %d %f %s", topic.number, documentNo, i + 1,
					searchResults[i].score, nameOfScoringFile));
		}

		return resultLinesForScoringFile;
	}

}
