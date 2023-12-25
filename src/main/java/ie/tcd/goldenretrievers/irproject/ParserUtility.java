package ie.tcd.goldenretrievers.irproject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

public class ParserUtility {

	public static Document ParseFT(Element element) {
		Document document = new Document();

		// Add number of document
		String numberOfDocument = element.getElementsByTag("DOCNO").first().text();
		document.add(new StringField("docno", numberOfDocument, Field.Store.YES));

		// Add entire content of document
		String contentOfDocument = element.text();
		document.add(new TextField("content", contentOfDocument, Field.Store.YES));

		// Add profile in document
		String profileInDocument = element.getElementsByTag("PROFILE").text();
		document.add(new TextField("profile", profileInDocument, Field.Store.YES));

		// Add date in document
		String dateInDocument = element.getElementsByTag("DATE").text();
		document.add(new TextField("date", dateInDocument, Field.Store.YES));

		// Add headline in document
		String headlineInDocument = element.getElementsByTag("HEADLINE").text();
		document.add(new TextField("title", headlineInDocument, Field.Store.YES));

		// Add text inside document
		String textOfDocument = element.getElementsByTag("TEXT").text();
		document.add(new TextField("TEXT", textOfDocument, Field.Store.YES));

		// Add publication in document
		String publicationInDocument = element.getElementsByTag("PUB").text();
		document.add(new TextField("publication", publicationInDocument, Field.Store.YES));

		// Add page in document
		String page = element.getElementsByTag("PAGE").text();
		document.add(new TextField("page", page, Field.Store.YES));

		return document;
	}

	public static Document ParseFR94(Element element) {
		Document document = new Document();

		// Add number of document
		String numberOfDocument = element.getElementsByTag("DOCNO").first().text();
		document.add(new StringField("docno", numberOfDocument, Field.Store.YES));

		// Add entire content of document
		String contentOfDocument = element.text();
		document.add(new TextField("content", contentOfDocument, Field.Store.YES));

		// Add title of document
		String titleOfDocument = element.getElementsByTag("DOCTITLE").text();
		document.add(new TextField("title", titleOfDocument, Field.Store.YES));

		// Add text inside document (including nested elements)
		String textOfDocumentWithNesting = element.getElementsByTag("TEXT").first().text().replaceAll("\n+", "\n");
		document.add(new TextField("TEXT", textOfDocumentWithNesting, Field.Store.YES));

		// Add text inside document (without nested elements)
		String textOfDocumentWithoutNesting = element.getElementsByTag("TEXT").first().ownText().replaceAll("\n+",
				"\n");
		document.add(new TextField("OUTER_TEXT", textOfDocumentWithoutNesting, Field.Store.YES));

		List<String> remainingElements = Arrays.asList("ACTION", "AGENCY", "FURTHER", "SUMMARY", "SUPPLEM");
        for (String s : remainingElements) {
            Element remainingElement = element.getElementsByTag(s).first();

            if (Objects.nonNull(remainingElement)) {
                document.add(new TextField(s, remainingElement.text().replaceAll("\n+", "\n"),
                        Field.Store.YES));
            }
        }

		return document;
	}

	public static Document ParseFBIS(Element element) {
		Document document = new Document();

		// Add number of document
		String numberOfDocument = element.getElementsByTag("DOCNO").text();
		document.add(new StringField("docno", numberOfDocument, Field.Store.YES));

		// Add entire content of document
		String contentOfDocument = element.text();
		document.add(new TextField("content", contentOfDocument, Field.Store.YES));

		// Add title of document
		String titleOfDocument = element.getElementsByTag("TI").text();
		document.add(new TextField("title", titleOfDocument, Field.Store.YES));

		// Add text inside document
		String textOfDocument = element.getElementsByTag("TEXT").text();
		document.add(new TextField("TEXT", textOfDocument, Field.Store.YES));

		// Add date of document
		String date = element.getElementsByTag("DATE1").text();
		document.add(new TextField("DATE", date, Field.Store.YES));

		// Sometimes there is also an <F P=*NumberHere*> tag with a date
		if (element.getElementsByTag("F") != null) {
			for (Element currentF : element.getElementsByTag("F")) {
				// Add region, location, network, language, and reporter fields
				if (currentF.attr("P").equals("100")) {
					String region = currentF.text();
					document.add(new TextField("REGION", region, Field.Store.YES));
				} else if (currentF.attr("P").equals("101")) {
					String location = currentF.text();
					document.add(new TextField("location", location, Field.Store.YES));
				} else if (currentF.attr("P").equals("104")) {
					String network = currentF.text();
					document.add(new TextField("NETWORK", network, Field.Store.YES));
				} else if (currentF.attr("P").equals("105")) {
					String language = currentF.text();
					document.add(new TextField("LANGUAGE", language, Field.Store.YES));
				} else if (currentF.attr("P").equals("106")) {
					String reporter = currentF.text();
					document.add(new TextField("REPORTER", reporter, Field.Store.YES));
				}
			}
		}

		return document;
	}

	public static Document ParseLATIMES(Element element) {
		Document document = new Document();

		// Add number of document
		String numberOfDocument = element.getElementsByTag("DOCNO").first().text();
		document.add(new StringField("docno", numberOfDocument, Field.Store.YES));

		// Add entire content of document
		String contentOfDocument = element.text();
		document.add(new TextField("content", contentOfDocument, Field.Store.YES));

		// Add headline in document
		String headlineInDocument = element.getElementsByTag("HEADLINE").text();
		document.add(new TextField("title", headlineInDocument, Field.Store.YES));

		// Add text inside document (not taking null values)
		if (element.getElementsByTag("TEXT").first() == null)
			document.add(new TextField("TEXT", "", Field.Store.YES));
		else {
			String text = element.getElementsByTag("TEXT").first().text();
			document.add(new TextField("TEXT", text, Field.Store.YES));
		}

		// Add remaining elements
		List<String> remainingElements = Arrays.asList("DATE", "DOCID", "GRAPHIC", "LENGTH", "SECTION", "subject",
				"TYPE");
        for (String s : remainingElements) {
            Element remainingElement = element.getElementsByTag(s).first();

            if (Objects.nonNull(remainingElement)) {
                document.add(new TextField(s, remainingElement.text().replaceAll("\n+", "\n"),
                        Field.Store.YES));
            }
        }

		return document;
	}

}
