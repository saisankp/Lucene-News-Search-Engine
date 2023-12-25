package ie.tcd.goldenretrievers.irproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.similarities.Similarity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import me.tongfei.progressbar.ProgressBar;

public class Scorer {
	private static final String EVALUATION_DIR = "./evaluation";
	private static final String TOPICS_FILE = "./topics/topics";

	public Scorer() {
	}

	public void score(Map<Main.AnalyzerType, Analyzer> analyzers, Similarity similarity, QueryParser parser, String[] args)
			throws IOException, ParseException {
		// Create evaluation directory to store results if it doesn't exist already
		Path path = Paths.get(EVALUATION_DIR);

		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}

		String nameOfScoringFile = args[2] + args[3] + args[4];
		// Gather topics for the collection
		List<Topic> topics = parseCollectionTopics();
		System.out.println(topics.size() + " topics found to search with, using them as queries now!");

		// Instantiate a searcher
		Searcher searcher = new Searcher(analyzers, similarity, parser);

		// Iterate through each topic and search the topic using the searcher
		FileWriter scoringFile = new FileWriter(EVALUATION_DIR + "/" + nameOfScoringFile, false);
		BufferedWriter scoringFileBuffer = new BufferedWriter(scoringFile);

		for (Topic topic : ProgressBar.wrap(topics, "")) {
			List<String> results = searcher.generateResultsFromTopic(topic, parser, nameOfScoringFile);

			for (String result : results) {
				scoringFileBuffer.write(result);
				scoringFileBuffer.newLine();
			}
		}

		scoringFileBuffer.flush();
		scoringFileBuffer.close();
		scoringFile.close();
	}

	private List<Topic> parseCollectionTopics() throws IOException {
		// Convert topics file to bytes and eventually to a string
		String topicsFileAsString = new String(Files.readAllBytes(Paths.get(TOPICS_FILE)));

		// Parse the string as an XML file to get a list of <top>
		List<Element> topicsElements = Jsoup.parse(topicsFileAsString, "", Parser.xmlParser()).getElementsByTag("top");

		// Create a list of topics objects using the <top> elements
		List<Topic> topics = new ArrayList<>();
		for (Element topicElement : topicsElements) {
			topics.add(new Topic(topicElement));
		}

		return topics;
	}

}
