package edu.usc.csci548.project.travelapp.crawler.jsoup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TripAdvisorCrawler {

	public static void crawlCities() {

		String base = "http://www.tripadvisor.com";
		Map<String, String> segments = new HashMap<String, String>();

		segments.put("NewYork",
				"/Attractions-g60763-Activties-New_York_City_New_York.html");
		segments.put("Chicago",
				"/Attractions-g35805-Activities-Chicago_Illinois.html");
		segments.put("London",
				"/Attractions-g186338-Activities-London_England.html");

		segments.put("LosAngeles",
				"/Attractions-g32655-Activities-Los_Angeles_California.html");
		CrawlSeedUrl retrieveSeedUrl = new CrawlSeedUrl(base);
		for (String city : segments.keySet()) {
			Map<String, String> res = retrieveSeedUrl.crawlThingsToDo(segments
					.get(city));
			for (String string : res.keySet()) {
				retrieveSeedUrl.saveSummary(
						"/home/vijay/csci548/" + city + "/",
						string.replace("/", "_").replace(" ", ""),
						res.get(string));
			}
		}

	}

	public static void main(String[] args) {
		// crawlCities();
		String rootFlder = "/home/lk/csci548";
		List<String> cities = new ArrayList<String>();
		cities.add("NewYork");
		cities.add("Chicago");
		cities.add("London");
		cities.add("LosAngeles");
		List<JSONObject> ll;
		;
		for (String string : cities) {
			ll = processCrawledWebPages(string, rootFlder + "/" + string);
			JSONArray arr = new JSONArray();
			arr.addAll(ll);
			JSONObject citi = new JSONObject();
			citi.put(string, arr);
			new CreateJsonRead().writeToFile(citi, rootFlder + "/result/"
					+ string);

		}
	}

	public static List<JSONObject> processCrawledWebPages(String name,
			String location) {
		File dataSource = new File(location);
		List<JSONObject> jsonPlaces = new ArrayList<JSONObject>();
		CrawlSeedUrl wrapper = new CrawlSeedUrl();
		for (File places : dataSource.listFiles()) {

			JSONObject processWebPages = wrapper.processWebPages(places,
					places.getName());
			if (processWebPages.size() > 0) {
				jsonPlaces.add(processWebPages);
			}
		}

		return jsonPlaces;

	}
}
