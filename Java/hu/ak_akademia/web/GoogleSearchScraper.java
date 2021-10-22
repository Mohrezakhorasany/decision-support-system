package hu.ak_akademia.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GoogleSearchScraper {

	private static final String GOOGLE_SEARCH_LINK_PREFIX = "https://www.google.com/search?q=";
	private static final List<String> CLASSES_NAMES = List.of("hgKElc", "di3YZe", "LGOjhe", "Crs1tb", "ILfuVd NA6bn", "co8aDb XcVN5d", "ILfuVd", "wDYxhc", "wDYxhc NFQFxe oHglmf xzPb7d viOShc", "RqBzHd");

	public String getSearchedResult(String searchFor) {
		String result = null;
		try {
			Document document = Jsoup.connect(GOOGLE_SEARCH_LINK_PREFIX + searchFor)
					.timeout(12000)
					.get();
			result = getTextDescriptionsFromClasses(document).stream()
					.filter(Predicate.not(String::isEmpty))
					.findFirst()
					.orElse(getLink(document));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getLink(Document document) {
		Optional<String> isLinkPresent = document.select("a[href]")
				.stream()
				.map(link -> link.attr("abs:href"))
				.filter(url -> !url.contains("google"))
				.findFirst();
		if (isLinkPresent.isPresent()) {
			return "For more information please visit: " + isLinkPresent.get();
		}
		return "No result have been found!";
	}

	private String getTextDescription(Document document, String className) {
		return document.getElementsByClass(className)
				.eachText()
				.stream()
				.filter(Predicate.not(String::isEmpty))
				.findFirst()
				.orElse("");
	}

	private List<String> getTextDescriptionsFromClasses(Document document) {
		return CLASSES_NAMES.stream()
				.map(name -> getTextDescription(document, name))
				.collect(Collectors.toList());
	}
}
