package tripadisor.crawlers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;

public class CreateJsonRead {

	public JSONObject createJsonObect(Map<String, String> input) {
		JSONObject obj = new JSONObject();
		for (String key : input.keySet()) {
			obj.put(key, input.get(key));
		}
		return obj;
	}

	public void writeToFile(JSONObject results, String location) {
		try {

			FileWriter file = new FileWriter(location+".json");
				file.write(results.toJSONString());
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
