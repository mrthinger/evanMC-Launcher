package mc.evan.maintain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import mc.evan.util.FileUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class VersionsMaintain {
	private static File currentconfig;
	
	@SuppressWarnings("unchecked")
	public static void updateVersion(String JSONFile, String key, String value) {
		currentconfig = new File(JSONFile);
		JSONObject versions = readCurrentJSON();

	

		FileUtil.sexyDelete(currentconfig.getAbsolutePath());
		FileUtil.createFile(currentconfig.getAbsolutePath());

		versions.put(key, value);

		try {
			FileWriter file = new FileWriter(currentconfig);
			file.write(versions.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static JSONObject readCurrentJSON() {
		JSONParser parser = new JSONParser();
		JSONObject CurrentVersions = null;

		try {
			FileReader cc = new FileReader(currentconfig);
			Object obj = parser.parse(cc);

			CurrentVersions = (JSONObject) obj;
			cc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return CurrentVersions;
	}
}
