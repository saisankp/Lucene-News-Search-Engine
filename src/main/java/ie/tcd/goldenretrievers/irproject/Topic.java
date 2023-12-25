package ie.tcd.goldenretrievers.irproject;

import org.jsoup.nodes.Element;

public class Topic {
	public String description;
	public String narrative;
	public int number;
	public String title;

	public Topic(Element element) {
		number = Integer.parseInt(getTopicSubHeading(element, "num"));
		title = getTopicSubHeading(element, "title");
		description = getTopicSubHeading(element, "desc");
		narrative = getTopicSubHeading(element, "narr");
	}

	private String getTopicSubHeading(Element element, String ElementAttribute) {
		// Remove extra label on attributes such as "Description: "
		// The only exception is for title, where this doesn't happen
		String getSubheadingText = (ElementAttribute.equals("title"))
				? element.getElementsByTag(ElementAttribute).first().ownText()
				: element.getElementsByTag(ElementAttribute).first().ownText()
						.substring(element.getElementsByTag(ElementAttribute).first().ownText().indexOf(':') + 2);

		// Backslash causes a ParseException, so let's remove it
		getSubheadingText = getSubheadingText.replaceAll("/", " ");
		return getSubheadingText;
	}

	public int getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getNarrative() {
		return narrative;
	}
}
