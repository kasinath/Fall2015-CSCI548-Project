package edu.usc.csci548.project.travelapp.crawler.jsoup;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.usc.csci548.project.travelapp.crawler.htmlunit.JavascriptScraper;

public class CrawlSeedUrl {

	private String url;
	private List<String> visitedUrl;

	public CrawlSeedUrl(String url) {
		this.url = url;
		this.visitedUrl = new ArrayList<String>();
	}

	public CrawlSeedUrl() {

	}

	private Document retrieveDocumentFromUrl(String url) throws IOException {
		Document doc = null;
		doc = Jsoup.connect(url).timeout(10 * 1000).get();
		return doc;
	}

	private Document retrieveDocumentLocal(File url) throws IOException {
		Document doc = null;
		doc = Jsoup.parse(url, null);
		return doc;
	}

	public Map<String, String> crawlThingsToDo(String segment) {
		boolean nextPage = true;
		String page = this.url + segment;
		Map<String, String> members = new HashMap<>();
		while (nextPage ) {
			try {
				Document doc = retrieveDocumentFromUrl(page);
				// get members
				Elements memberList = doc.select("div.property_title");
				for (Element element : memberList) {
					String tt = element.select("a").attr("href");
					if (!members.containsValue(url + tt)
							&& element.select("a").text() != null) {
						members.put(element.select("a").text(), url + tt);

					} else
						continue;
				}
				this.visitedUrl.add(page);
				Elements pageList = doc.select("div.unified.pagination").select("a");
				String val="";
				for (Element element : pageList) {
					if(element.text().equals("Next"))
						val = element.attr("href");
				}
				if (this.visitedUrl.contains(url + val)) {
					nextPage = false;
				}
				if(val == ""){
					nextPage = false;
				}
				page = url + val;
			}

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return members;
	}

	public void clearVisitedUrl() {
		this.visitedUrl.clear();
	}

	public void saveSummary(String location, String key, String sumUrl) {
		try {

			Document doc = retrieveDocumentFromUrl(sumUrl);
			String summaryContents = doc.toString();

			if (summaryContents.length() > 0) {
				PrintWriter writer = new PrintWriter(location + key + ".html",
						"UTF-8");
				writer.println(summaryContents);
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public JSONObject processWebPages(File url, String name) {
		JSONObject result = new JSONObject();
		JSONArray arrayElementOneArray = new JSONArray();
		try {
			Map<String, String> data = new HashMap<String, String>();
			Document doc = retrieveDocumentLocal(url);
			String heading = doc.select("h1[id=HEADING]").text();
			data.put("name", heading);
			JSONObject nameObj = new CreateJsonRead()
			.createJsonObect(data);
			arrayElementOneArray.add(nameObj);
			data.clear();
			String rating = "0";
			rating = doc.select("img[property=ratingValue]").attr("content");
			data.put("rating", rating);
			JSONObject rate = new CreateJsonRead()
			.createJsonObect(data);
			arrayElementOneArray.add(rate);
			data.clear();
			Elements select1 = doc.select("div[id=HOUR_OVERLAY_CONTENTS]");
			if(select1.size() > 0){
			Elements days = select1.get(0).select("div");
			if(days.size() > 0){
			List<JSONObject> time = new ArrayList<JSONObject>();
			days.remove();
			for (Element element : days) {
				Elements day = element.select("span");
				if (day.size() < 1) {
					continue;
				}

				data.put("days", day.select("span[class=days]").text());
				data.put("time", day.select("span[class=hours]").text());
				JSONObject dayshours = new CreateJsonRead()
						.createJsonObect(data);
				time.add(dayshours);
			}
			JSONArray arr = new JSONArray();
			arr.addAll(time);
			JSONObject dayshours = new JSONObject();
			if (arr.size() > 0){
				dayshours.put("operation", arr);
			arrayElementOneArray.add(dayshours);
			}
			}
			}
			data.clear();
			Elements select = doc.select("address");
			for (Element element : select) {
				Elements address = element.select("span.format_address"); // Address
																			// found

				for (Element element2 : address) {
					if (element2.select("span[property=streetAddress").text()
							.length() > 0)
						data.put("streetAddress",
								element2.select("span[property=streetAddress")
										.text());

					String addLoc = element2.select(
							"span[property=addressLocality").text();
					if (addLoc.length() > 0)
						data.put("addressLocality", addLoc);

					String text = element2
							.select("span[property=addressRegion").text();
					if (text.length() > 0)
						data.put("addressRegion", text);
					String text2 = element2.select("span[property=postalCode")
							.text();
					if (text2.length() > 0)
						data.put("postalCode", text2);

				}
				JSONObject addr = new CreateJsonRead().createJsonObect(data);
				JSONObject mainAddress = new JSONObject();
				if (addr.size() > 0) {
					mainAddress.put("address", addr);
					arrayElementOneArray.add(mainAddress);
				}
			}
			Elements contactInfo = doc.select("div.phoneNumber");
			data.clear();
			if (contactInfo != null && contactInfo.size() > 0) {
				String phone[] = contactInfo.get(0).text().split(":");
				if(phone[0].equals("Phone Number")){
				data.put(phone[0], phone[1]);
				arrayElementOneArray.add(new CreateJsonRead()
						.createJsonObect(data));
				}
			}
			Elements details = doc.select("div.details_wrapper");
			if (details != null && details.size() > 0) {
				Elements dd = details.get(0).select("div.detail");
				data.clear();
				for (Element element2 : dd) {
					String timings[] = element2.text().split(":");
					if(timings[0].contains("Recommended length of visit") || timings[0].contains("Fee")){
					data.put(timings[0], timings[1]);
					arrayElementOneArray.add(new CreateJsonRead()
							.createJsonObect(data));
					}
					data.clear();
				}
			}
			
			JavascriptScraper jsScraper = new JavascriptScraper(TripAdvisorCrawler.fileURLDict.get(name));
			ArrayList<JSONObject> rsviews = jsScraper.scrape();
			JSONObject revObj = new JSONObject();
			JSONArray rvsArray = new JSONArray();
			for(JSONObject r : rsviews)
			{
				rvsArray.add(r);
			}
			revObj.put("reviews", rvsArray);
			arrayElementOneArray.add(revObj);
			
			Elements nearByAttractions = doc.select("span.class *");
			JSONArray nearBy = new JSONArray();
			for (Element element2 : nearByAttractions) {
				data.clear();
				if (element2.attr("data-name").length() > 0) {
					nearBy.add(element2.attr("data-name"));
				} else
					continue;
			}
			JSONObject nearByJs = new JSONObject();
			nearByJs.put("nearBy", nearBy);
			if (nearByJs.size() > 0)
				arrayElementOneArray.add(nearByJs);

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (arrayElementOneArray.size() > 0)
			result.put("place", arrayElementOneArray);
		return result;
	}

}