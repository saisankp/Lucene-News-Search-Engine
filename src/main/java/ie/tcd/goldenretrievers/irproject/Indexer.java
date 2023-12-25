package ie.tcd.goldenretrievers.irproject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import me.tongfei.progressbar.ProgressBar;

public class Indexer {
	public static final String INDEX_DIR = "./index";
	private static final String COLLECTION_DIR = "./collection";

	public Indexer() {
	}

	public void index(Analyzer analyzer) throws IOException {
		// Open index directory
		try (Directory directory = FSDirectory.open(Paths.get(INDEX_DIR + "/" + analyzer.getClass().getSimpleName()))) {
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			// Write into index directory
			try (IndexWriter writer = new IndexWriter(directory, config)) {
				// Iterate through each type in the collection and index it
				List<String> typesInCollection = Arrays.asList("fbis", "fr94", "ft", "latimes");
				for (String typeInCollection : typesInCollection) {
					Path pathToCollectionType = Paths.get(COLLECTION_DIR, typeInCollection);
					// Get list of paths in the collection directory that are of the type we are
					// iterating through and are not .txt files
					List<Path> collectionFilesToIndex = Files.walk(pathToCollectionType, Integer.MAX_VALUE)
							.filter(path -> path.getFileName().toString().startsWith(typeInCollection.substring(0, 2)))
							.filter(Files::isRegularFile).collect(Collectors.toList());
					System.out.println(collectionFilesToIndex.size() + " files found to index in collection of type '"
							+ typeInCollection + "', indexing them now!");
					for (Path file : ProgressBar.wrap(collectionFilesToIndex,
							"Indexing collection type '" + typeInCollection + "'")) {
						String contentOfFileAsString = new String(Files.readAllBytes(file));
						List<Element> documentElementsInFile = Jsoup
								.parse(contentOfFileAsString, "", Parser.xmlParser()).getElementsByTag("DOC");
						for (Element documentElementInFile : documentElementsInFile) {
							Document document;

							// Index elements in file according to collection type
							switch (typeInCollection) {
							case "ft":
								document = ParserUtility.ParseFT(documentElementInFile);
								break;
							case "fr94":
								document = ParserUtility.ParseFR94(documentElementInFile);
								break;
							case "fbis":
								document = ParserUtility.ParseFBIS(documentElementInFile);
								break;
							default:
								document = ParserUtility.ParseLATIMES(documentElementInFile);
								break;
							}

							writer.addDocument(document);
						}
					}
				}
			}
		}
	}
}
