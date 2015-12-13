package esQueries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import facebook4j.internal.org.json.JSONException;

public class QueryES {

	public static JSONArray queryCities(List<String> cats) throws JSONException, IOException, ParseException {

		JSONObject obj = new JSONObject();
		obj.put("size", 100);

		JSONArray should = new JSONArray();

		for (String cat : cats) {
			JSONObject c = new JSONObject();
			c.put("categories", cat);
			JSONObject m = new JSONObject();
			m.put("match", c);
			should.add(m);
		}
		JSONObject bool = new JSONObject();
		bool.put("should", should);
		JSONObject q = new JSONObject();
		q.put("bool", bool);
		obj.put("query", q);

		FileWriter fw = new FileWriter("query.json");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(obj.toString());
		bw.close();

		Process p = Runtime.getRuntime().exec(
				new String[] { "curl", "-XGET", "http://localhost:9200/cities/things/_search", "-d", "@query.json" });
		InputStream stdout = p.getInputStream();
		String line = "";
		BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
		while ((line = brCleanUp.readLine()) != null) {
			System.out.println("[Stdout] " + line);
			break;
		}
		brCleanUp.close();
		JSONParser parser = new JSONParser();
		JSONObject cities = (JSONObject) parser.parse(line);
		JSONArray hits = (JSONArray) ((JSONObject) cities.get("hits")).get("hits");

		for (int i = 0; i < hits.size(); i++) {
			JSONObject o = (JSONObject) ((JSONObject) hits.get(i)).get("_source");
			if (o.get("desc") != null) {
			}
			System.out.println(o.get("name") +":" + o.get("rating"));
		}
		return hits;

	}

	public static void main(String[] args) throws JSONException, IOException, ParseException {
		QueryES q = new QueryES();
		List<String> cats = new ArrayList<>();
		cats.add("Museum");
		cats.add("Art");
		cats.add("Sports");
		q.queryCities(cats);
	}

}
